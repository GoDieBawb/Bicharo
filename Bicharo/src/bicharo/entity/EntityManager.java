/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.entity;

import bicharo.Main;
import bicharo.util.script.Script;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import bicharo.util.FileWalker;

/**
 *
 * @author Bob
 */
public class EntityManager {
    
    private final ArrayList<Entity> entities;
    
    public EntityManager() {
        entities = new ArrayList<>();
    }
    
    public void initEntities(Node entityNode) {
        
        entities.clear();
        for (int i = 0; i < entityNode.getQuantity(); i++) {
            
            Spatial s = entityNode.getChild(i);
            Entity  e = new Entity();
            e.setModel(s);
            e.setName(s.getName());
            entities.add(e);
            
            LinkedHashMap map;
            
            String fileName = s.getUserData("Script")+".yml";
            
            FileWalker fw = new FileWalker();
            String filePath = fw.walk("assets/Scripts", fileName);
            filePath = filePath.replaceAll("\\\\", "/");
            filePath = filePath.split("assets/")[1];
            
            map   = (LinkedHashMap) Main.GAME_MANAGER.getUtilityManager().getYamlManager()
                                        .loadYamlAsset(filePath);

            Script script = new Script(e, map, Main.GAME_MANAGER.getPlayerManager().getPlayer());
                    
            e.setScript(script);
            script.initialize();
            
        }        
        
    }
    
    public void update() {
        
    }
    
}
