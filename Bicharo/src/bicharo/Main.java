package bicharo;

import bawbgui.components.ChoiceBox;
import bawbgui.components.GuiAppState;
import bawbgui.components.TextBoard;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import java.util.ArrayList;
import java.util.Arrays;

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
    }
    
    private void testChoiceBox() {
        
        TextBoard textBoard = new TextBoard(guiNode);
        textBoard.showMessage("Test", "A Test Message");
        textBoard.scale(.5f);
        
        ArrayList<String> x = new ArrayList<>(Arrays.asList("Agree", "Disagree", "Walk", "Run", "Eat", "Drink"));        
        ChoiceBox choiceBox = new ChoiceBox(guiNode, x, textBoard);
        choiceBox.show();
        choiceBox.scale(.5f);
        
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
