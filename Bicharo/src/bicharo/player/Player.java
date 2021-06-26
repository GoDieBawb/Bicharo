/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.player;

import bicharo.util.script.ProximityTrigger;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import java.util.HashMap;

/**
 *
 * @author Bob
 */
public class Player implements ProximityTrigger {
    
    boolean  hasChecked;
    boolean  isInteracting;
    boolean  isFalling;
    String   choice;
    Node     cameraNode;
    Geometry collider;
    HashMap<String, Object> flags;
    
    
    public Player(AssetManager am, Node rootNode) {
        cameraNode = new Node("Player");
        flags      = new HashMap<>();
        Cylinder s = new Cylinder(16, 16, .2f, .5f, true);
        collider   = new Geometry("Collider", s);
        collider.rotate(FastMath.HALF_PI, 0, 0);
        Material mat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md"); 
        mat.setColor("Color", ColorRGBA.White);
        collider.setMaterial(mat);
        rootNode.attachChild(cameraNode);
        cameraNode.attachChild(collider);
        collider.setLocalTranslation(0,1f,0);
        //collider.setCullHint(Spatial.CullHint.Always);
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
