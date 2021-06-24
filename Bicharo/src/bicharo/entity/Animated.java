/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.entity;

import com.jme3.anim.AnimComposer;

/**
 *
 * @author Bawb
 * Interface for Animated Entity
 */
public interface Animated {
    
    //Returns the animated entity's Animation Control
    public AnimComposer getAnimComposer();
    
    public void setAnimation(String animName);
    
    public void setAnimation(String animName, boolean loop);
    
}
