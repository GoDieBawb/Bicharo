/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.gui;

import bicharo.player.Player;
import bicharo.util.UtilityManager;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author Bob
 */
public class GuiManager {
    
    Player player;
    Gui    gui;
    
    public GuiManager(SimpleApplication app, UtilityManager um, Player player) {
        gui = new Gui(app.getGuiNode(), player);
        GuiHandler gh = new GuiHandler(um.getScriptManager().getScriptParser().getTagParser(), gui);
        um.getScriptManager().getScriptParser().registerHandler(gh);
    }
    
    public void update(float tpf) {
    }
    
}
