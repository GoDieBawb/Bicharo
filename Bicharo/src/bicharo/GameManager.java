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
import bicharo.util.AndroidManager;
import bicharo.util.UtilityManager;
import bicharo.util.script.parser.TagParser;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashMap;

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
    String         filePath;
    
    public GameManager(SimpleApplication app) {
        utilityManager = new UtilityManager(app);
        playerManager  = new PlayerManager(app, utilityManager.getInteractionManager());
        sceneManager   = new SceneManager(app.getAssetManager(), app.getRootNode());
        entityManager  = new EntityManager();
        guiManager     = new GuiManager(app, utilityManager, playerManager.getPlayer());
        initScriptTools();
        setFilePath(app.getStateManager());
        loadGame();
    }
    
    private void initScriptTools() {
        TagParser tp = utilityManager.getScriptManager().getScriptParser().getTagParser();
        tp.registerParser(new PlayerTagParser());
        utilityManager.getScriptManager().getScriptParser().registerHandler(new PlayerHandler(tp, playerManager.getPlayer()));
    }
    
    public void startGame() {
        sceneManager.initScene("Portal");
        Spatial  spawn    = sceneManager.getScene().getChild("Spawn");
        if (spawn != null)
            playerManager.getPlayer().getCameraNode().setLocalTranslation(spawn.getLocalTranslation());
        entityManager.initEntities((Node)sceneManager.getScene().getParent().getChild("Entity Node"));
    }
    
    public void loadPortal() {
    
    }
    
    private void setFilePath(AppStateManager stateManager) {
        
        filePath = System.getProperty("user.home") + "\\Documents\\Games\\Saves\\";
        
        if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
            filePath = stateManager.getState(AndroidManager.class).filePath;
        }
        
    }    
    
    public void saveGame() {
        HashMap<String, Integer> save = new HashMap<>();
        save.put("Level", playerManager.getPlayer().getMaxLevel());
        utilityManager.getYamlManager().saveYaml(filePath + "Bicharo.yml", save);
    }    
    
    private void loadGame() {
        HashMap<String, Integer> save = utilityManager.getYamlManager().loadYaml(filePath + "Bicharo.yml");
        if (save == null) {
            saveGame();
            save = utilityManager.getYamlManager().loadYaml(filePath + "Bicharo.yml");
        }
        playerManager.getPlayer().setMaxLevel(save.get("Level"));
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
        entityManager.update();
        playerManager.update(tpf);
    }
    
}
