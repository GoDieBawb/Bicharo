/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script;

import bicharo.util.script.handler.ScriptHandler;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Bawb
 */
public class ScriptManager {
    
    private final ScriptHandler scriptParser;
    
    //This Object Pretty Much Holds the Command Parser
    public ScriptManager(AppStateManager stateManager) {
        scriptParser = new ScriptHandler(stateManager);
    }
    
    //Gets the Script Parser
    public ScriptHandler getScriptParser() {
        return scriptParser;
    }
    
}
