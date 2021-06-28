/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script.handler;

import bicharo.util.script.Scriptable;
import bicharo.util.script.parser.TagParser;

/**
 *
 * @author root
 */
public class LogicHandler extends AbstractCommandHandler {
    
    private String  rawCommand;
    private boolean go;
    private boolean met;
    
    public LogicHandler(TagParser parser) {
        super(parser);
    }
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled     = true; 
        String[] args        = rawCommand.split(" ");
        String   command     = args[0];     
        this.rawCommand      = rawCommand;
        
        //Starts an if logic statement
        switch (command) {
            
            case "if":
                //If the condition returns true then go
                if (ifCheck(scriptable, args)) {
                    go  = true;
                    met = true;
                }
                
                //If not the condition is not met and the commands should not go
                else {
                    go  = false;
                    met = false;
                }   
                
                break;
                
            case "elseif":
                //If previous condition was not met else if comes into action
                if (!met) {
                    
                    //Check the condiition if true the script can go
                    if (ifCheck(scriptable, args)) {
                        
                        go  = true;
                        met = true;
                        
                    }
                    
                }
                
                //If the previous condition was met else if does not come into play
                else {
                    
                    go  = false;
                    
                }
                break;
                
            case "else":
                if (!met && !go)
                    go = true;
                
                if (met)
                    go = false;
                
                break;
                
            case "end":
                go = true
                        ;
                break;
                
            default:
                handled = false;
                break;
                
        }
        
        return handled;
        
    }

    public void setMet(boolean newVal) {
        met = newVal;
    }
    
    public void setGo(boolean newVal) {
        go = newVal;
    }
    
    public boolean isGo() {
        return go;
    }
  
    //Determines the truth value of a tag
    private boolean ifCheck(Scriptable entity, String[] args) {
      
        
        //If more than 4 arguments more than one boolean statement present
        if (rawCommand.contains("&&")) {
        
            String[] splitCom  = rawCommand.split("&&", 2);
            String[] realCheck = splitCom[0].split(" ");
            rawCommand         = splitCom[1];
            
            if (ifCheck(entity, realCheck)) {
                String[] newCheck = rawCommand.split(" ");
                return ifCheck(entity, newCheck);
            }
            
            else {
                return false;
            }
            
        }
        
        Object comp1 = null;
        Object comp2 = null;
        String comp  = null;
      
        try {
            comp1      = parser.parseTag(args[1], entity);
            comp2      = parser.parseTag(args[3], entity);
            comp       = args[2];
        }
        
        catch (Exception e) {
        }
      
        boolean truthVal  = false;
      
        if (comp == null) {
            
            if (comp1 instanceof Boolean) {
              truthVal = (Boolean) comp1;
            }
            
            else {
              truthVal = false;
            }
            
            return truthVal;
            
        }
        
        switch (comp) {
            
            case "&&":
                
                if (comp1 instanceof Boolean && comp2 instanceof Boolean) {
                    
                    boolean a = (Boolean) comp1;
                    boolean b = (Boolean) comp2;
                    
                    if (a&&b) {
                        truthVal = true;
                    }
                    
                    else{
                        truthVal = false;
                    }
                    
                }
                
                else {
                    
                    truthVal = false;
                    
                }
                
                return truthVal;
                
            case "!!":
                if (comp1 instanceof Boolean && comp2 instanceof Boolean) {
                    
                    Boolean a = (Boolean) comp1;
                    Boolean b = (Boolean) comp2;
                    
                    if (!a && !b) {
                        truthVal = true;
                    }
                    else{
                        truthVal = false;
                    }
                    
                }
                
                else {
                    
                    truthVal = false;
                    
                }
                
                return truthVal;
                
            case "!&":
                if (comp1 instanceof Boolean && comp2 instanceof Boolean) {
                    
                    Boolean a = (Boolean) comp1;
                    Boolean b = (Boolean) comp2;
                    
                    if (a && !b) {
                        truthVal = false;
                    }
                    else{
                        truthVal = true;
                    }
                    
                    return truthVal;
                    
                }
                
                else {  
                    truthVal = true;
                }
                
                return truthVal;
                
            case "==":
                if (comp1.equals(comp2)) {
                    truthVal = true;
                }
                else {
                    truthVal = false;
                }     
                break;
                     
            case ">": {
                
                Double a = ((Number) comp1).doubleValue();
                Double b = ((Number) comp2).doubleValue();

                if (a > b) {
                    truthVal = true;
                }
                
                break;                
            }
            
            case "<": {
                
                Double a = ((Number) comp1).doubleValue();
                Double b = ((Number) comp2).doubleValue();

                if (a < b) {
                    truthVal = true;
                }
                
                break;                     
            }
            
            case ">=": {
                
                Double a = ((Number) comp1).doubleValue();
                Double b = ((Number) comp2).doubleValue();

                if (a >= b) {
                    truthVal = true;
                }
                
                break;                
            }
            
            case "<=": {
                
                Double a = ((Number) comp1).doubleValue();
                Double b = ((Number) comp2).doubleValue();

                if (a <= b) {
                    truthVal = true;
                }
                
                break;                     
            }            
            
            default:
                System.out.println("Unknown Operator: " + comp);
                break;
                
        }
      
    return truthVal;
    
    }    
    
}
