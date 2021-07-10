/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script.handler;

import bicharo.util.script.Scriptable;
import bicharo.util.script.parser.TagParser;
import com.jme3.scene.Spatial;

/**
 *
 * @author Bob
 */
public class ScriptableHandler extends AbstractCommandHandler {

    public ScriptableHandler(TagParser parser) {
        super(parser);
    }    
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled = true;
        String[] args    = rawCommand.split(" ");
        String   command = args[0];

        switch (command) {
            case "hide":
                scriptable.getScript().deactivate();
                scriptable.getModel().setCullHint(Spatial.CullHint.Always);
                break;
            case "show":
                scriptable.getScript().activate();
                scriptable.getModel().setCullHint(Spatial.CullHint.Inherit);
                break;
            default:
                handled = false;
                break;        
        }
    
        return handled;
        
    }
    
}
