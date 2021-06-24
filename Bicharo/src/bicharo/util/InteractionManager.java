/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 *
 * @author Bob
 */
public class InteractionManager implements ActionListener {

    boolean up=false, left=false, right=false,down=false,cursor=false,click=false;

    InputManager inputManager;
    
    public InteractionManager(InputManager inputManager) {
        this.inputManager = inputManager;
        setUpKeys();
    }

    //Sets up the keys
    private void setUpKeys() {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Cursor", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Cursor");
        inputManager.addListener(this, "Click");
    }

    //Runs when an action is performed
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {

        switch (binding) {
            case "Up":
                up = isPressed;
                break;
            case "Down":
                down = isPressed;
                break;
            case "Left":
                left = isPressed;
                break;
            case "Right":
                right = isPressed;
                break;
            case "Cursor":
                cursor = isPressed;
                break;                
            case "Click":
                click = isPressed;
                break;
            default:
                break;
        }

    }
    
    public InputManager getInputManager() {
        return inputManager;
    }
    
    public boolean getIsPressed(String binding) {
        boolean isPressed = false;
        switch (binding) {
            case "Up":
                isPressed = up;
                break;
            case "Down":
                isPressed = down;
                break;
            case "Left":
                isPressed = left;
                break;
            case "Right":
                isPressed = right;
                break;
            case "Cursor":
                isPressed = cursor;
                break;                
            case "Click":
                isPressed = click;
                break;
            default:
                break;
        }  
        return isPressed;
    }

}
