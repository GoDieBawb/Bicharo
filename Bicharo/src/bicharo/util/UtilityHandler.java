/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util;

import bicharo.Main;
import bicharo.player.Player;
import bicharo.util.script.Scriptable;
import bicharo.util.script.handler.AbstractCommandHandler;
import bicharo.util.script.parser.TagParser;
import com.jme3.math.Vector3f;

/**
 *
 * @author root
 */
public class UtilityHandler extends AbstractCommandHandler {
    
    public UtilityHandler(TagParser parser) {
        super(parser);
    }
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        Player   player  = Main.GAME_MANAGER.getPlayerManager().getPlayer();
        boolean  handled = true;
        String[] args    = rawCommand.split(" ");
        String   command = args[0];
        
        //Debug command prints a message to the console
        switch (command) {
            
            case "playsound": {
                Main.GAME_MANAGER.getUtilityManager().getAudioManager().getSound(args[1]).playInstance();
                break;
            }
            
            case "changescene":
                
                //File Path for scene and boolean for teleporting
                String  scenePath;
                boolean teleport = false;
                
                //If args are 4 both Directory and Teleport Location
                switch (args.length) {
                    
                    case 4:
                        scenePath = "Scenes/" + args[1] + "/" + args[2] + ".j3o";
                        teleport = true;
                        break;
                    case 3:
                        //If last arg contains .location it cannot be a location tag
                        if (!args[args.length -1 ].contains(".location")) {
                            scenePath = "Scenes/" + args[1] + "/" + args[2] + ".j3o";
                        }
                        
                        //The final args must be location
                        else {
                            scenePath = "Scenes/" + args[1] + ".j3o";
                            teleport = true;
                        }   break;
                    default:
                        scenePath = "Scenes/" + args[1] + ".j3o";
                        break;
                        
                }   
                
                Main.GAME_MANAGER.getSceneManager().initScene(scenePath);
                
                //If teleport is true the player will warp to the final tag
                if (teleport)
                    player.getCameraNode().setLocalTranslation((Vector3f)parser.parseTag(args[args.length-1], player));
                
                break;
                
            default:
                handled = false;
                break;
                
        }
        
        return handled;
        
    }
    
}
