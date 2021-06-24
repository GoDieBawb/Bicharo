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
 * @author Bob
 */
public class DebugHandler extends AbstractCommandHandler {

    public DebugHandler(TagParser parser) {
        super(parser);
    }
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled = true;
        String[] args    = rawCommand.split(" ");
        String   command = args[0];        
        
        switch (command) {
            
            case "debug":
                {
                    String[] a     = rawCommand.split(" ", 2);
                    String debugMessage = a[1];
                    System.out.println(debugMessage);
                    break;
                }
                
            case "debugtag":
                {
                    String[] a = rawCommand.split(" ", 2);
                    System.out.println(parser.parseTag(a[1], scriptable));
                    break;
                }
                
            default:
                handled = false;
                break;                
                        
        }
        
        return handled;
        
    }
    
}
