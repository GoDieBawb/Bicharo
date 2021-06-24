/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script.handler;

import bicharo.util.script.Scriptable;
import bicharo.util.script.parser.TagParser;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Bob
 */
public class SpatialHandler extends AbstractCommandHandler {

    public SpatialHandler(TagParser parser) {
        super(parser);
    }    
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled = true;
        String[] args    = rawCommand.split(" ");
        String   command = args[0];

        switch (command) {
        
            case "look":
            {
                Spatial s = scriptable.getModel();
                Spatial p = (Spatial) parser.parseTag(args[1], scriptable);
                Vector3f direction = p.getLocalTranslation().subtract(s.getLocalTranslation()).setY(0);
                direction.normalizeLocal();
                Quaternion rotation = new Quaternion().lookAt(direction, Vector3f.UNIT_Y);
                s.setLocalRotation(rotation);
                break;
            }
            case "move":
                break;
            
            case "rotate":
                break;
                
            case "scale":
                if (args.length == 2) {
                    Spatial s = scriptable.getModel();
                    float f = Float.valueOf(args[1]);
                    s.scale(f);
                }
                else {
                    Spatial s = (Spatial) parser.parseTag(args[1], scriptable);
                    float   f = Float.valueOf(args[2]);
                    s.scale(f);
                }
                break;
            default:
                handled = false;
                break;
            
        }
        
        return handled;
        
    }
    
}
