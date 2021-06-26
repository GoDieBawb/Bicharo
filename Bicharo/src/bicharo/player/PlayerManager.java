/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.player;

import bicharo.util.InteractionManager;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.FlyByCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;


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
        initMark(app.getAssetManager());
        movePlayer(0);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    private void movePlayer(float tpf) {
        float ymult = 0; //If not falling this will zero out the camera location
        float yadd  = 1.1f; //Camera add offset for falling
        if (player.isFalling) {
            ymult = 1;
            yadd  = 0;
        }
        float moveSpeed = 5f;
        float phydist   = 1f;
        player.cameraNode.setLocalTranslation(player.cameraNode.getLocalTranslation().multLocal(1,1*ymult,1).add(0, yadd, 0));
        player.collider.setLocalTranslation(0,.5f,phydist); //Default collider location
        
        if (im.getIsPressed("Up")) {
            player.collider.getLocalTranslation().addLocal(0,0,phydist);
            if (moveCheck(player.collider, rootNode.getChild("Scene Node")))
                player.cameraNode.move(camera.getDirection().mult(tpf).mult(moveSpeed));
            
        }
        
        else if (im.getIsPressed("Down")) {
            player.collider.getLocalTranslation().addLocal(0,0,-phydist);
            if (moveCheck(player.collider, rootNode.getChild("Scene Node")))
                player.cameraNode.move(camera.getDirection().negate().mult(tpf).mult(moveSpeed));
        }
        
        if (im.getIsPressed("Right")) {
            player.collider.getLocalTranslation().addLocal(-phydist,0,0);
            if (moveCheck(player.collider, rootNode.getChild("Scene Node")))
                player.cameraNode.move(camera.getLeft().negate().mult(tpf).mult(moveSpeed));
        }
        
        else if (im.getIsPressed("Left")) {
            player.collider.getLocalTranslation().addLocal(phydist,0,0);
            if (moveCheck(player.collider, rootNode.getChild("Scene Node")))
                player.cameraNode.move(camera.getLeft().mult(tpf).mult(moveSpeed));
        }
        
        player.cameraNode.lookAt(camera.getDirection().mult(999999).setY(0), new Vector3f(0,1,0)); //Makes the gun point
        camera.setLocation(player.getLocalTranslation().multLocal(1,1*ymult,1).add(0, yadd, 0));
        
    }
    
    private Geometry mark;
    private void initMark(AssetManager assetManager) {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }    

    private void pick() {
        Node shootables = (Node) rootNode.getChild("Scene Node");
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(camera.getLocation(), camera.getDirection());
        shootables.collideWith(ray, results);
        System.out.println("----- Collisions? " + results.size() + "-----");
        for (int i = 0; i < results.size(); i++) {
          float dist = results.getCollision(i).getDistance();
          Vector3f pt = results.getCollision(i).getContactPoint();
          String hit = results.getCollision(i).getGeometry().getName();
          System.out.println("* Collision #" + i);
          System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
        }
        if (results.size() > 0) {
          CollisionResult closest = results.getClosestCollision();
          mark.setLocalTranslation(closest.getContactPoint());
          rootNode.attachChild(mark);
        } else {
          rootNode.detachChild(mark);
        }
    }
    
    private boolean moveCheck(Spatial collisionNode) {
        CollisionResults results = new CollisionResults();
        player.collider.getMesh().updateBound();
        collisionNode.collideWith(player.collider.getWorldBound(), results);
        for (CollisionResult r : results) {
            if (r.getGeometry() != player.collider && r.getGeometry() != null) {
                System.out.println(r);
                return false;
            }
        }
        return true;
    }
    
    private boolean moveCheck(Spatial s, Spatial collide) {
        ((Geometry) s).getMesh().updateBound();
        s.getLocalTranslation().addLocal(0,-1.35f,0);
        CollisionResults results = new CollisionResults();
        int x = collide.collideWith(s.getWorldBound(), results);
        s.getLocalTranslation().addLocal(0,1.35f,0);
        return x == 0;
    }    
    
    private void handleCamera() {
        boolean cam    = flyCam.isEnabled();
        boolean cursor = im.getIsPressed("Cursor");
        if (cam == cursor) {
            flyCam.setEnabled(!cursor);
            im.getInputManager().setCursorVisible(cursor);
        }
    }
    
    private void fallCheck(float tpf) {
        if (player.isFalling) {
            player.cameraNode.move(0, -5*tpf, 0);
            return;
        }
        CollisionResults results = new CollisionResults();
        Ray              ray     = new Ray(camera.getLocation(), new Vector3f(0,-1,0));
        int              x       = rootNode.getChild("Terrain Node").collideWith(ray, results);
        if (x == 0) {
            player.isFalling = true;
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
        fallCheck(tpf);
        
    }
    
}
