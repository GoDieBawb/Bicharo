/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util;

import bicharo.util.script.ScriptManager;
import bicharo.util.yaml.YamlManager;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author Bob
 */
public class UtilityManager {
    
    YamlManager        yamlManager;
    ScriptManager      scriptManager;
    AudioManager       audioManager;
    FileWalker         fileWalker;
    InteractionManager interactionManager;
    
    public UtilityManager(SimpleApplication app) {
        yamlManager        = new YamlManager(app.getStateManager());
        scriptManager      = new ScriptManager(app.getStateManager());
        audioManager       = new AudioManager(app.getStateManager());
        fileWalker         = new FileWalker();
        interactionManager = new InteractionManager(app.getInputManager());
        app.getStateManager().attach(scriptManager);
    }
    
    public ScriptManager getScriptManager() {
        return scriptManager;
    }
    
    public AudioManager getAudioManager() {
        return audioManager;
    }
    
    public YamlManager getYamlManager() {
        return yamlManager;
    }
    
    public FileWalker getFileWalker() {
        return fileWalker;
    }
    
    public InteractionManager getInteractionManager() {
        return interactionManager;
    }
    
}
