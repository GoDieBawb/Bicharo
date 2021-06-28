/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script;

import bicharo.util.script.handler.ScriptHandler;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.ArrayList;

/**
 *
 * @author Bawb
 */
public class ScriptManager extends AbstractAppState {
    
    public static float TPF;
    
    private final ScriptHandler     scriptParser;
    private final ArrayList<Script> scripts;
    
    //This Object Pretty Much Holds the Command Parser
    public ScriptManager(AppStateManager stateManager) {
        scriptParser = new ScriptHandler(stateManager);
        scripts = new ArrayList<>();
    }
    
    //Gets the Script Parser
    public ScriptHandler getScriptParser() {
        return scriptParser;
    }
    
    public ArrayList<Script> getScripts() {
        return scripts;
    }
    
    public void clearScripts() {
        scripts.clear();
    }
    
    @Override
    public void update(float tpf) {
        TPF = tpf;
        for (Script s : scripts) {
            s.checkForTriggers();
        }
    }
    
}
