/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.player;

import bicharo.util.script.Scriptable;
import bicharo.util.script.handler.AbstractCommandHandler;
import bicharo.util.script.parser.TagParser;

/**
 *
 * @author Bob
 */
public class PlayerHandler extends AbstractCommandHandler {
    
    Player player;
    
    public PlayerHandler(TagParser parser, Player player) {
        super(parser);
        this.player = player;
    }
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled = true;
        String[] args    =  rawCommand.split(" ");
        String   command = args[0];
            
        switch (command) {
        
            case "flag":
                if (args.length == 2) {
                    String flagName = args[1];
                    player.getFlagList().put(flagName, true);
                }
                else {
                    String flagName = args[1];
                    String flagArg  = rawCommand.split(" ", 3)[2];
                    Object value    = parser.parseTag(flagArg, scriptable);
                    player.getFlagList().put(flagName, value);
                }
                break;
            
            case "unflag":
                String flagName = args[1];
                player.getFlagList().remove(flagName);
                break;
            
            case "clearflags":
                player.getFlagList().clear();
                break;

            default:
                handled = false;
                break;
        }
        
        return handled;
    }

}
