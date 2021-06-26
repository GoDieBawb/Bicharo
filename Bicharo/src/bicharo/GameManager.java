/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo;

import bicharo.scene.SceneManager;
import bicharo.entity.EntityManager;
import bicharo.gui.GuiManager;
import bicharo.player.PlayerHandler;
import bicharo.player.PlayerManager;
import bicharo.player.PlayerTagParser;
import bicharo.util.UtilityManager;
import bicharo.util.script.parser.TagParser;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class GameManager {
    
    UtilityManager utilityManager;
    PlayerManager  playerManager;
    SceneManager   sceneManager;
    EntityManager  entityManager;
    GuiManager     guiManager;
    
    public GameManager(SimpleApplication app) {
        utilityManager = new UtilityManager(app);
        playerManager  = new PlayerManager(app, utilityManager.getInteractionManager());
        sceneManager   = new SceneManager(app.getAssetManager(), app.getRootNode());
        entityManager  = new EntityManager();
        guiManager     = new GuiManager(app, utilityManager, playerManager.getPlayer());
        initScriptTools();
    }
    
    private void initScriptTools() {
        TagParser tp = utilityManager.getScriptManager().getScriptParser().getTagParser();
        tp.registerParser(new PlayerTagParser());
        utilityManager.getScriptManager().getScriptParser().registerHandler(new PlayerHandler(tp, playerManager.getPlayer()));
    }
    
    public void startGame() {
        sceneManager.initScene("TestScene");
        entityManager.initEntities((Node)sceneManager.getScene().getChild("Entity Node"));
    }
    
    public UtilityManager getUtilityManager() {
        return utilityManager;
    }
    
    public PlayerManager getPlayerManager() {
        return playerManager;
    }
    
    public SceneManager getSceneManager() {
        return sceneManager;
    }
    
    public void update(float tpf) {
        entityManager.update(tpf);
        playerManager.update(tpf);
    }
    
}
