package bicharo;

import bawbgui.components.GuiAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static GameManager GAME_MANAGER;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new GuiAppState(inputManager, assetManager));
        GAME_MANAGER = new GameManager(this);
        GAME_MANAGER.startGame();
        flyCam.setMoveSpeed(0);
        inputManager.setCursorVisible(false);
        initCamera();
    }    
    
    private void initCamera() {
        float scale = .5f;
        cam.setFrustumNear(cam.getFrustumNear()*scale);
        cam.setFrustumLeft(cam.getFrustumLeft()*scale);
        cam.setFrustumRight(cam.getFrustumRight()*scale);
        cam.setFrustumTop(cam.getFrustumTop()*scale);
        cam.setFrustumBottom(cam.getFrustumBottom()*scale);    
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        GAME_MANAGER.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
