/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.gui;

import bawbgui.components.AlertBox;
import bawbgui.components.ChoiceBox;
import bawbgui.components.GuiComponent;
import bawbgui.components.TextBoard;
import bicharo.player.Player;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author Bob
 */
public class Gui {
    
    private final AlertBox  alertBox;
    private final TextBoard textBoard;
    private final ChoiceBox choiceBox;
    private final Player    player;

    private final ArrayList<String> choices = new ArrayList<>();    
    
    public Gui(Node guiNode, Player player) {
    
        this.player = player;
        
        alertBox = new AlertBox(guiNode) {
        
            @Override
            public void onClickableClicked(GuiComponent.Clickable clickable) {
                super.onClickableClicked(clickable);
                if (getButtonText().equals("Talk") || getButtonText().equals("Read")) {
                    check();
                }

                hide();
            }
            
        };
        alertBox.scale(.75f);
        
        textBoard = new TextBoard(guiNode) {
        
            @Override
            public void onClickableClicked(GuiComponent.Clickable clickable) {
                super.onClickableClicked(clickable);
                endInteract();
            }
            
            @Override
            public void show() {
                super.show();
                interact();
            }
            
        };
        textBoard.scale(.65f);
        
        choiceBox = new ChoiceBox(guiNode, choices, textBoard) {
            
            @Override
            public void show() {
                super.show();
                textBoard.show();
            }
            
            @Override
            public void hide() {
                super.hide();
                choose();
            }
            
            @Override
            public void onClickableClicked(Clickable clickable) {
                act(clickable.getName());
            }
        };
        choiceBox.scale(.65f);
        
    }
    
    protected void check() {
        player.setHasChecked(true);
    }
    
    protected void interact() {
        player.setInteracting(true);
    }
    
    protected void endInteract() {
        player.setInteracting(false);
    }    
    
    protected void choose() {
        player.setChoice("");
    }
    
    protected void act(String name) {
    
        player.setChoice(name);
        
    }
    
    public AlertBox getAlertBox() {
        return alertBox;
    }
    
    public TextBoard getTextBoard() {
        return textBoard;
    }
    
    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }
    
    public ArrayList<String> getChoices() {
        return choices;
    }
    
}
