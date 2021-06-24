/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script;

import com.jme3.math.Vector3f;

/**
 *
 * @author Bob
 */
public interface ProximityTrigger {
    
    public Vector3f getLocalTranslation();
    public boolean  hasChecked();
    public void     setHasChecked(boolean newVal);
    
}
