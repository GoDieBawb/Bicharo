/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.player;

import bicharo.Main;
import bicharo.util.script.parser.AbstractTagParser;

/**
 *
 * @author Bob
 */
public class PlayerTagParser extends AbstractTagParser {
    
    @Override
    public Object parseTag(String fullTag, Object object) {
    
        Object obj = object;
        
        String strippedTag = fullTag.split("#")[0];        
        
        switch (strippedTag) {
            
            case "player":
                obj = Main.GAME_MANAGER.getPlayerManager().getPlayer();
                break;      

            case "camera":
                obj = Main.GAME_MANAGER.getPlayerManager().getPlayer().cameraNode;
                break;
                
            case "dead":
                obj = (Boolean) ((Player) obj).isDead();
                break;
                
            case "hasflag": 
            {
                String[] args = fullTag.split("#");
                obj = ((Player) obj).flags.containsKey(args[1]);
                break;
            }
            case "flag":
                String[] args = fullTag.split("#");
                if (!((Player) obj).getFlagList().containsKey(args[1])) {
                    obj = -1;
                }
                else {
                    obj = ((Player) obj).getFlagList().get(args[1]);
                }
                break;

            case "choice":
                if (fullTag.contains("#")) {
                    String arg = fullTag.split("#")[1];
                    return ((Player) obj).getChoice().equals(arg);
                }
                else
                    obj = ((Player) obj).getChoice();
                break;
                
            case "inventory":
                obj = ((Player) obj).getInventory();
                break;
                
            case "interacting":
                obj = ((Player) obj).isInteracting;
                break;                
                
        }
        
        return obj;
        
    }
    
}
