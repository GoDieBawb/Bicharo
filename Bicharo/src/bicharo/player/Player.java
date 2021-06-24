/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.player;

import bicharo.util.script.ProximityTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.HashMap;

/**
 *
 * @author Bob
 */
public class Player implements ProximityTrigger {
    
    boolean hasChecked;
    boolean isInteracting;
    String  choice;
    Node    cameraNode;
    HashMap<String, Object> flags;
    
    
    public Player() {
        cameraNode = new Node("Player");
        flags      = new HashMap<>();
    }
    
    public Node getCameraNode() {
        return cameraNode;
    }
    
    public void setInteracting(boolean val) {
        isInteracting = val;
    }
    
    public boolean isInteracting() {
        return isInteracting;
    }
    
    public String getInventory(){return null;}; //Place Holder
    
    public HashMap<String, Object> getFlagList() {
        return flags;
    }
    
    public boolean isDead() {return false;} //Place Holder

    public void setChoice(String choice) {
        this.choice = choice;
    }
    
    public String getChoice() {
        return choice;
    }
    
    @Override
    public Vector3f getLocalTranslation() {
        return cameraNode.getLocalTranslation();
    }

    @Override
    public boolean hasChecked() {
        return hasChecked;
    }

    @Override
    public void setHasChecked(boolean hasChecked) {
        this.hasChecked = hasChecked;
    }
    
}
