/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.entity;

import bicharo.util.script.Scriptable;
import bicharo.util.script.handler.AbstractCommandHandler;
import bicharo.util.script.parser.TagParser;
import com.jme3.anim.AnimComposer;
/**
 *
 * @author root
 */
public class AnimationHandler extends AbstractCommandHandler {
    
    public AnimationHandler(TagParser parser) {
        super(parser);
    }
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled = true;
        String[] args    =  rawCommand.split(" ");
        String   command = args[0];
            
        switch (command) {
            
            //Runs the entities idle animation
            case "animate":
                if (args[1] == null || args[1].equals("")) {
                    System.out.println("ERROR: Argument Required for 'Animate' Command");
                    handled = false;
                    break;
                }
                ((Animated) scriptable).setAnimation(args[1]);
                handled = true;
                break;
            
            //Clears the entities animations
            case "noanim":
            
                ((Animated) scriptable).getAnimComposer().removeCurrentAction(AnimComposer.DEFAULT_LAYER);    
                break;
            
            default:
                handled = false;
                break;
            
        }

        return handled;
        
    }    
    
}
