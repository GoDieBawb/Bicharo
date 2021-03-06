/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.entity;

import bicharo.Main;
import bicharo.util.script.Script;
import bicharo.util.script.Scriptable;
import bicharo.util.script.parser.TagParser;
import com.jme3.anim.AnimComposer;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Bob
 */
public class Entity implements Scriptable, Animated {
    
    Spatial      model;
    boolean      isHid;
    Script       script;
    String       name;
    AnimComposer animComposer;
    AnimChannel  animChannel;
    
    public Entity() {
        TagParser        tp = Main.GAME_MANAGER.getUtilityManager().getScriptManager().getScriptParser().getTagParser();
        AnimationHandler ah = new AnimationHandler(tp);
        Main.GAME_MANAGER.getUtilityManager().getScriptManager().getScriptParser().registerHandler(ah);
    }
    
    @Override
    public void setModel(Spatial model) {
        this.model = model;
    }
    
    @Override
    public Spatial getModel() {
        return model;
    }
    
    public void hide() {
        isHid =  true;
        model.setLocalTranslation(0, -15, 0);
    }
    
    public void setIsHid(boolean newVal) {
        isHid = newVal;
    }
    
    public void show() {
        isHid =  false;
        model.setLocalTranslation(0, 0, 0);
    }
    
    public boolean isHid() {
        return isHid;
    }
    
    @Override
    public void setScript(Script script) {
        this.script = script;
    }
    
    @Override
    public Script getScript() {
        return script;
    }
    
    @Override
    public void setProximity() {
        if (script == null)
            return;
    }
    
    @Override
    public boolean inProx() {
        return script.InProx();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Vector3f getLocalTranslation() {
        return model.getLocalTranslation();
    }
    
    String currentAnimation = "";
    @Override
    public void setAnimation(String animName) {
        
        if (currentAnimation.equals(animName)) return;
        currentAnimation = animName;   
        
        if (animComposer == null)
            animComposer = findAnimComposer(model);
        
        if (animComposer == null) {
            setControlAnimation(animName);
            return;
        }
        
        if (!animComposer.getAnimClipsNames().contains(animName)) {
            System.out.println("No Animation: " + animName + " For Model: " + model.getName());
            return;
        }        
        
        animComposer.setCurrentAction(animName);
        
    }    
    
    
    @Override
    public void setAnimation(String animName, boolean loop) {   
        
        if (currentAnimation.equals(animName)) return;
        currentAnimation = animName;        
        
        if (animComposer == null)
            animComposer = findAnimComposer(model);
        
        if (animComposer == null) {
            setControlAnimation(animName, loop);
            return;
        }        
        
        if (!animComposer.getAnimClipsNames().contains(animName)) {
            System.out.println("No Animation: " + animName + " For Model: " + model.getName());
            return;
        }             
        
        animComposer.setCurrentAction(animName);
        
    }    
    
    private void setControlAnimation(String animName) {
        
        if (animChannel == null)
            animChannel = findAnimControl(model).createChannel();
        
        animChannel.setAnim( animName);
        
    }
    
    private void setControlAnimation(String animName, boolean loop) {
        
        if (animChannel == null)
            animChannel = findAnimControl(model).createChannel();  
        
        animChannel.setAnim(animName);
        
        if (loop)
            animChannel.setLoopMode(LoopMode.Loop);
        else
            animChannel.setLoopMode(LoopMode.DontLoop);
        
    }    
    
    @Override
    public AnimComposer getAnimComposer() {
        return animComposer;
    }
    
    private AnimComposer findAnimComposer(final Spatial parent) {
        
        final AnimComposer animControl = parent.getControl(AnimComposer.class);
        if (animControl != null) {
            return animControl;
        }

        if (parent instanceof Node) {
            for (final Spatial s : ((Node) parent).getChildren()) {
                final AnimComposer animControl2 = findAnimComposer(s);
                if (animControl2 != null) {
                    return animControl2;
                }
                
            }
            
        }

        return null;
    
    }
    
    private AnimControl findAnimControl(final Spatial parent) {
        
        final AnimControl animControl = parent.getControl(AnimControl.class);
        if (animControl != null) {
            return animControl;
        }

        if (parent instanceof Node) {
            for (final Spatial s : ((Node) parent).getChildren()) {
                final AnimControl animControl2 = findAnimControl(s);
                if (animControl2 != null) {
                    return animControl2;
                }
                
            }
            
        }

        return null;
    
    }         
    
}
