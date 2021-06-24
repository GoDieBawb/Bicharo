/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.player;

import bicharo.Main;
import bicharo.util.InteractionManager;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;


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
        player   = new Player();
        player.cameraNode.setLocalTranslation(10, 2, 10);
        movePlayer(0);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    private void movePlayer(float tpf) {
        float moveSpeed = 5f;
        player.cameraNode.setLocalTranslation(player.cameraNode.getLocalTranslation().multLocal(1,0,1).add(0, 1.1f, 0));
        player.cameraNode.lookAt(camera.getDirection().mult(999999), new Vector3f(0,1,0)); //Makes the gun point
        if (im.getIsPressed("Up")) {
            if (moveCheck(camera.getDirection(), rootNode))
                player.cameraNode.move(camera.getDirection().mult(tpf).mult(moveSpeed));
        }
        else if (im.getIsPressed("Down")) {
            if (moveCheck(camera.getDirection().negate(), rootNode))
                player.cameraNode.move(camera.getDirection().negate().mult(tpf).mult(moveSpeed));
        }
        if (im.getIsPressed("Right")) {
            if (moveCheck(camera.getLeft().negate(), rootNode))
                player.cameraNode.move(camera.getLeft().negate().mult(tpf).mult(moveSpeed));
        }
        else if (im.getIsPressed("Left")) {
            if (moveCheck(camera.getLeft(), rootNode))
                player.cameraNode.move(camera.getLeft().mult(tpf).mult(moveSpeed));
        }
        camera.setLocation(player.getLocalTranslation().multLocal(1,0,1).add(0, 1.1f, 0));
    }
    
    public boolean moveCheck(Vector3f moveDir, Node collisionNode) {

        Ray              ray     = new Ray(player.cameraNode.getLocalTranslation().multLocal(1,0,1).add(0,1,0), moveDir);
        CollisionResults results = new CollisionResults();
        collisionNode.collideWith(ray, results);

        for (int i = 0; i < results.size(); i++) {

            float dist = results.getCollision(i).getContactPoint().distance(player.cameraNode.getLocalTranslation());
            return dist >= 2.0f;

        }

        return true;

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
