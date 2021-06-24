/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script.parser;


import bicharo.util.script.Script;
import bicharo.util.script.Scriptable;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bob
 */
public class TagParser extends AbstractTagParser {
    
    private final ArrayList<AbstractTagParser> parsers = new ArrayList<>();
    
    public TagParser(AppStateManager stateManager) {
        SpatialParser sp = new SpatialParser(((SimpleApplication)stateManager.getApplication()).getRootNode());
        sp.setParser(this);
        parsers.add(sp);
    }
    
    public void registerParser(AbstractTagParser parser) {
        parser.setParser(this);
        parsers.add(parser);
    }
    
    //This method returns on object based on the tag string
    @Override
    public Object parseTag(String tag, Object object) {
        
        //Split the tag on . character and remove <>
        tag            = tag.replace("<", "");
        tag            = tag.replace(">", "");
        String[] args  = tag.split("\\.");
        Object obj     = object;
        
        //Iterate over the list of arguments
        for(int i = 0; i < args.length; i++) {
            
            String fullTag     = args[i];
            String strippedTag = fullTag.split("#")[0];
            
            //Handle Symbol Table
            if (fullTag.startsWith("&"))
            {
                String[] strAr = fullTag.split("&");
                obj            = ((Scriptable)object).getScript().getSymTab().get(strAr[1]);
                continue;
            }
            //Handle Constants
            else if (fullTag.startsWith("$"))
            {
                String[] strAr = fullTag.split("\\$");
                obj            = checkForConstants(strAr[1]);
                
                //Check for decimal
                if (args.length >= i+2) {
                    if (args[i+1].matches("[0-9]*")) {
                        String decimal = args[i].replace("$", "")+"."+args[i+1];
                        obj = checkForConstants(decimal);
                        i++; //Beacuse decimal contains a .
                    }
                }
                //For Strings containing a period go onto next
                if (strAr[1].contains(".")) {
                    i++;
                }
                
                continue;
            }            
            
            switch (strippedTag) {
                    
                case "true":
                    obj = true;
                    break;
                    
                case "false":
                    obj = false;
                    break;
                    
                case "time":
                    obj = System.currentTimeMillis();
                    break;
                    
                case "add":
                case "sub":
                {
                    int mult = 1;
                    if (strippedTag.equals("sub"))
                        mult = -1;
                    String[] strAr  = fullTag.split("#", 2);
                    if (obj instanceof Number) {

                        Number num = (Number) parseTag(strAr[1], object);

                        if (obj instanceof Long) {

                            Long a = (Long) obj;

                            if (num instanceof Long) {
                                Long b = (long) num*mult;
                                obj = a+b;
                            }

                            else if (num instanceof Float) {
                                float b = (float) num*mult;
                                obj = a+b;
                            }

                            else if (num instanceof Integer) {
                                int b = (int) num*mult;
                                obj = a+b;
                            }

                        }

                        else if (obj instanceof Float) {

                            //If the added argument is a decimal
                            if (args.length >= i+2) {
                                if (args[i+1].matches("[0-9]*")) {
                                    String decimal = num+"."+args[i+1];
                                    num = (Float) checkForConstants(decimal);
                                    i++; //Beacuse decimal contains a .
                                }
                            }

                            Float a = (Float) obj;

                            if (num instanceof Long) {
                                Long b = (long) num*mult;
                                obj = a+b;
                            }

                            else if (num instanceof Float) {
                                float b = (float) num*mult;
                                obj = a+b;
                            }

                            else if (num instanceof Integer) {
                                int b = (int) num*mult;
                                obj = a+b;
                            }

                        }

                        else if (obj instanceof Integer) {

                            int a = (int) obj;

                            if (num instanceof Long) {
                                Long b = (long) num*mult;
                                obj = a+b;
                            }

                            else if (num instanceof Float) {
                                float b = (float) num*mult;
                                obj = a+b;
                            }

                            else if (num instanceof Integer) {
                                int b = (int) num*mult;
                                obj = a+b;
                            }

                        }
                        
                        else {
                            System.out.println("ERROR: " + obj + " is not a number");
                        }

                    }

                    else if (obj instanceof Vector3f) {
                        String[] floats = tag.split(strippedTag)[1].split("#")[1].split(",");
                        float x         = Float.valueOf(floats[0])*mult;
                        float y         = Float.valueOf(floats[1])*mult;
                        float z         = Float.valueOf(floats[2])*mult;
                        obj             = ((Vector3f) obj).add(x,y,z);
                    }       
                    break;
                }
                    
                case "north":
                    obj = (Boolean) (((Spatial) obj).getLocalTranslation().x < 0);
                    break;    
                    
                case "south":
                    obj = (Boolean) (((Spatial) obj).getLocalTranslation().x > 0);
                    break;
                    
                case "east":
                    obj = (Boolean) (((Spatial) obj).getLocalTranslation().z < 0);
                    break;
                    
                case "west":
                    obj = (Boolean) (((Spatial) obj).getLocalTranslation().z > 0);
                    break;
                    
                case "!":
                    if (((Boolean) obj)) {
                        
                        obj = (Boolean) false;
                        
                    }
                    
                    else {
                        
                        obj = (Boolean) true;
                        
                    }   
                    break;
                    
                case "inprox":
                    obj = ((Scriptable) obj).inProx();
                    break;
                    
                case "contains":
                {
                    String[] strAr = fullTag.split("#");
                    obj = ((HashMap<Object, Integer>)obj).get(strAr[1]);
                    if (obj == null)
                        obj = false;

                    else
                        obj = true;
                    break;
                }
                    
                case "global":
                {
                    String[] strAr = fullTag.split("#");
                    obj = Script.GLOBAL_SYM_TAB.get(strAr[1]);
                    break;
                }
                
                //Check other registered parsers for the tag
                default:
                    Object o = null;
                    for (int j = 0; j < parsers.size(); j++) {
                        AbstractTagParser p = parsers.get(j);
                        o = p.parseTag(fullTag, obj);
                        if (o != null && obj != o) {
                            obj = o;
                            break;
                        }
                    }
                    
                    //If o remains null no registered parser knows the tag
                    if (o == null) {
                        System.out.println("Unkown Tag Argument: " + strippedTag);
                    }
                    break;
                    
            }
            
            //If at the final tag then return the final casted object
            if (i == args.length-1) {
                return obj;
            }
        
        }
        return obj;
        
    }
    
    private Object checkForConstants(String objString) {
        
        if (objString.matches("[0-9]*")) {
            return Integer.valueOf(objString);
        }
        
        else if (objString.matches("[0-9]*\\.[0-9]*")) {
            return Float.valueOf(objString);
        }

        return objString;
        
    }
    
}
