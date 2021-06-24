package bicharo.util.script;

import bicharo.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Bawb
 */

public class Script {
    
    public static HashMap<Object, Object> GLOBAL_SYM_TAB;
    private final HashMap<String, Object> symTab;
            
    private final Scriptable       scriptable;
    private final ScriptManager    scriptManager;
    private float                  proximity;
    private boolean                inProx;
    private boolean                enteredProx;
    private final ProximityTrigger trigger;
    private final LinkedHashMap    map;
    
    //Contstructs the Script
    public Script(Scriptable scriptable, LinkedHashMap map, ProximityTrigger trigger) {
        System.out.println("Constructing Script For: " + scriptable.getName());
        this.scriptable = scriptable;
        this.map        = map;     
        this.trigger    = trigger;
        scriptManager   = Main.GAME_MANAGER.getUtilityManager().getScriptManager();
        symTab          = new HashMap();
    }
    
    //Initializes the Proximity and Start Actions
    public void initialize() {
        setFields();
        setProximity();
        startAction();
    }
    
    private void setFields() {
    
        if (map.get("Fields") == null)
            return;
        
        ArrayList fm = (ArrayList) map.get("Fields");
        scriptManager.getScriptParser().parse(fm, scriptable);
        
    }
    
    public Map<String, Object> getSymTab() {
        return symTab;
    }
    
    //If there is a proximity map in the script get the Distance
    private void setProximity() {
        
        if (map.get("Proximity") == null)
            return;        
        
        try {
            Map<Object, Object> pm = (Map<Object, Object>) map.get("Proximity");
            proximity              = (Integer) pm.get("Distance");
        }
        
        catch (Exception e){
            System.out.println("Proximity Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }
        
    }
    
    //If there is a start action do it on initialize
    public void startAction() {
        
        ArrayList startScript;
        if (map.get("Start") == null)
            return;
            
        try {
            Map<Object, Object> sm = (Map<Object, Object>)  map.get("Start");
            startScript            = (ArrayList)  sm.get("Script");
            scriptManager.getScriptParser().parse(startScript, scriptable);
        }
        
        catch (Exception e) {
            System.out.println("Start Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }

    }
  
    //This Action is Run When Hit is Called. Currently Only With Gun
    public void hitAction() {
        
        if (map.get("Hit") == null)
            return;

        try {
            Map<Object, Object> hm  = (Map<Object, Object>)  map.get("Hit");
            ArrayList hitScript     = (ArrayList) hm.get("Script");
            scriptManager.getScriptParser().parse(hitScript, scriptable);
        }

        catch (Exception e) {
            System.out.println("Hit Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }
        
    }
  
    //Run if the players distance is within the proximity or leaves it
    private void proximityAction() {

        if (map.get("Proximity") == null)
            return;
        
        try {
        
            float distance  = trigger.getLocalTranslation().distance(scriptable.getLocalTranslation());
            Map<Object, Object> pm = (Map<Object, Object>)  map.get("Proximity");

            if (proximity > distance) {
                inProx = true;
            }
            
            if (proximity < distance) {
                inProx = false;
            }

            //If in prox but has not yet run. Run Proximity Action
            if (inProx && !enteredProx) {

                enteredProx          = true;
                
                if (pm.get("Enter") == null)
                    return;
                
                ArrayList enterScript = (ArrayList) pm.get("Enter");
                scriptManager.getScriptParser().parse(enterScript, scriptable);

            }
            
            //If left prox but has not run. Run leave proximity action
            if (!inProx && enteredProx) {

                //player.getHud().getInfoText().hide();
                enteredProx          = false;
                
                if (pm.get("Exit") == null)
                    return;
                
                ArrayList exitScript = (ArrayList) pm.get("Exit");
                scriptManager.getScriptParser().parse(exitScript, scriptable);

            }

        }
        
        catch (Exception e) {
            System.out.println("Proximity Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }
            
    }
  
    //Returns whether the player is within the entities proximity
    public boolean InProx() {
        return inProx;
    }
    
    //Run when the player interacts within the entities proximity
    private void checkAction() {

        if (map.get("Interact") == null) {
            trigger.setHasChecked(false);
            return;
        }
        
        try {
            
            if (inProx) {

                Map<Object, Object> im   = (Map<Object, Object>)  map.get("Interact");
                ArrayList interactScript = (ArrayList) im.get("Script");
                scriptManager.getScriptParser().parse(interactScript, scriptable);
                trigger.setHasChecked(false);

            }
            
        }
                
        catch(Exception e) {
            System.out.println("Interact Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }
        
    }
  
    //This script is contanstly run on a loop.
    private void loopAction() {
        
        if (map.get("While") == null)
            return;
        
        try {
            Map<Object, Object> wm          = (Map<Object, Object>)  map.get("While");
            ArrayList whileScript           = (ArrayList) wm.get("Script");
            scriptManager.getScriptParser().parse(whileScript, scriptable);
        }
        
        catch (Exception e) {
            System.out.println("Loop Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }
        
    }    
    
    public void pressAction() {
        
        ArrayList pressScript;
        
        if (map.get("Press") == null)
            return;
            
        try {
            Map<Object, Object> pm = (Map<Object, Object>)  map.get("Press");
            pressScript            = (ArrayList)  pm.get("Script");
            scriptManager.getScriptParser().parse(pressScript, scriptable);
        }
        
        catch (Exception e) {
            System.out.println("Hold Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }

    }            
    
    public void holdAction() {
        
        ArrayList holdScript;
        
        if (map.get("Hold") == null)
            return;
            
        try {
            Map<Object, Object> hm = (Map<Object, Object>)  map.get("Hold");
            holdScript              = (ArrayList)  hm.get("Script");
            scriptManager.getScriptParser().parse(holdScript, scriptable);
        }
        
        catch (Exception e) {
            System.out.println("Hold Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }

    }        
    
    //If there is a start action do it on initialize
    public void releaseAction() {
        
        ArrayList useScript;
        
        if (map.get("Release") == null)
            return;
            
        try {
            Map<Object, Object> um = (Map<Object, Object>)  map.get("Release");
            useScript              = (ArrayList)  um.get("Script");
            scriptManager.getScriptParser().parse(useScript, scriptable);
        }
        
        catch (Exception e) {
            System.out.println("Release Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }

    }    
    
    public void equipAction(boolean isLeft) {
    
        ArrayList equipScript;
        String side = "Right";
        
        if (isLeft)
            side = "Left";
        
        if (map.get("Equip") == null)
            return;
            
        try {
            Map<Object, Object> em = (Map<Object, Object>)  map.get("Equip");
            equipScript            = (ArrayList)  em.get(side);
            scriptManager.getScriptParser().parse(equipScript, scriptable);
        }
        
        catch (Exception e) {
            System.out.println("Equip " + side  + " Error For Entity: " + scriptable.getName());
            e.printStackTrace();
        }        
        
    }
    
    //Run on loop and checks for player interaction and distance from entity
    public void checkForTriggers() {
        
        if (trigger.hasChecked()) {
            checkAction();
        }
        
        proximityAction();
        loopAction();
        
    }    
    
}
