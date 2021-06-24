/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.player;

import bicharo.util.InteractionManager;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


/**
 *
 * @author Bob
 */
public class PlayerManager {
   
    Player             player;
    Camera             camera;
    InteractionManager im;
    FlyByCamera        flyCam;
    Node               rootNode;
    
    public PlayerManager(SimpleApplication app, InteractionManager im) {
        camera   = app.getCamera();
        this.im  = im;
        flyCam   = app.getFlyByCamera();
        rootNode = app.getRootNode();
        player   = new Player(app.getAssetManager(), app.getRootNode());
        player.cameraNode.setLocalTranslation(10, 2, 10);
        movePlayer(0);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    private void movePlayer(float tpf) {
        float moveSpeed = 5f;
        player.cameraNode.setLocalTranslation(player.cameraNode.getLocalTranslation().multLocal(1,0,1).add(0, 1.1f, 0));
        player.collider.setLocalTranslation(0,1.1f,0); //Default collider location
        
        if (im.getIsPressed("Up")) {
            player.collider.getLocalTranslation().addLocal(0,0,2);
            if (moveCheck(camera.getDirection(), rootNode.getChild("Scene")))
                player.cameraNode.move(camera.getDirection().mult(tpf).mult(moveSpeed));
            
        }
        
        else if (im.getIsPressed("Down")) {
            player.collider.getLocalTranslation().addLocal(0,0,-2);
            if (moveCheck(camera.getDirection().negate(), rootNode.getChild("Scene")))
                player.cameraNode.move(camera.getDirection().negate().mult(tpf).mult(moveSpeed));
        }
        
        if (im.getIsPressed("Right")) {
            player.collider.getLocalTranslation().addLocal(-1,0,0);
            if (moveCheck(camera.getLeft().negate(), rootNode.getChild("Scene")))
                player.cameraNode.move(camera.getLeft().negate().mult(tpf).mult(moveSpeed));
        }
        
        else if (im.getIsPressed("Left")) {
            player.collider.getLocalTranslation().addLocal(1,0,0);
            if (moveCheck(camera.getLeft(), rootNode.getChild("Scene")))
                player.cameraNode.move(camera.getLeft().mult(tpf).mult(moveSpeed));
        }
        
        player.cameraNode.lookAt(camera.getDirection().mult(999999).setY(0), new Vector3f(0,1,0)); //Makes the gun point
        camera.setLocation(player.getLocalTranslation().multLocal(1,0,1).add(0, 1.1f, 0));
        
    }
    
    public boolean moveCheck(Vector3f moveDir, Spatial collisionNode) {

        CollisionResults results = new CollisionResults();
        collisionNode.collideWith(player.collider.getWorldBound(), results);
        return results.size() <= 0;

    }      
    
    public void handleCamera() {
        boolean cam    = flyCam.isEnabled();
        boolean cursor = im.getIsPressed("Cursor");
        if (cam == cursor) {
            flyCam.setEnabled(!cursor);
            im.getInputManager().setCursorVisible(cursor);
        }
    }
    
    public void update(float tpf) {
        if (player.isInteracting) {
            if (flyCam.isEnabled()) {
                flyCam.setEnabled(false);
                im.getInputManager().setCursorVisible(true);
            }
        }
        else {
            movePlayer(tpf);
            handleCamera();
        }
    }
    
}
