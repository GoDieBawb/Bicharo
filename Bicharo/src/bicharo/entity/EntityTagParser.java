/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.entity;

import bicharo.Main;
import bicharo.util.script.parser.AbstractTagParser;

/**
 *
 * @author Bob
 */
public class EntityTagParser extends AbstractTagParser {
   
    @Override
    public Object parseTag(String fullTag, Object object) {
        
        Object obj = object;
        String strippedTag = fullTag.split("#")[0];   
        
        switch (strippedTag) {
            
            case "entity":
            case "entities":
                
                //If it contains a # the next string is the name of the entity
                if (fullTag.contains("#")) {

                    String[] strAr = fullTag.split("#");

                    if (strAr[1].equals("model"))
                        obj = ((Entity) object).getModel();

                    else
                        obj = Main.GAME_MANAGER.getSceneManager().getScene().getChild(strAr[1]);

                }

                //If no # the tag is referring to the entity itself
                else
                    obj = object; //This will cause issues because it will be rejected as unparsed.
                break;
                
            case "ishid":
                obj = (Boolean) ((Entity) obj).isHid();
                break;   
                
        }
        return obj;
    }    
    
}
