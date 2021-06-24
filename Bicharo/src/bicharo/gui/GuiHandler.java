/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.gui;

import bicharo.util.script.Scriptable;
import bicharo.util.script.handler.AbstractCommandHandler;
import bicharo.util.script.parser.TagParser;

/**
 *
 * @author Bob
 */
public class GuiHandler extends AbstractCommandHandler {

    Gui gui;
    
    public GuiHandler(TagParser parser, Gui gui) {
        super(parser);
        this.gui = gui;
    }
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled = true;
        String[] args    = rawCommand.split(" ");
        String   command = args[0];
        
        switch (command) {
        
            case "chat":
                //args[1] is title, the message is between quotes
                gui.getTextBoard().showMessage(args[1], rawCommand.split("\"")[1]);
                break;
            case "closechat":
                gui.getTextBoard().hide();
                break;                
            case "alert":
                //args[1] is title, the message is between quotes and args[2] is the button name
                gui.getAlertBox().showMessage(args[1], rawCommand.split("\"")[1], args[2]);
                break;
            case "closealert":
                gui.getAlertBox().hide();
                break;
            case "yesno":
                gui.getChoices().clear();
                gui.getChoices().add("Yes");
                gui.getChoices().add("No");         
                gui.getChoiceBox().show();
                break;
            case "prompt":
                gui.getChoices().clear();
                String[] choices = args[1].split(",");
                for (int i = 0; i < choices.length; i++) {
                    
                    String tag = choices[i];
                    
                    if (choices[i].contains("<")) {
                        while (!choices[i].contains(">")) {
                            i++;
                            tag+= choices[i];
                        }
                    }
                    
                    String choice = parser.parseTag(tag, scriptable).toString();
                    choice = choice.replace("\"", "");
                    gui.getChoices().add(choice);
                }
                gui.getChoiceBox().show();
                break;
            case "closechoice":
                gui.getChoiceBox().hide();
                break;
            default:
                handled = false;
                break;
            
        }
        
        return handled;
        
    }
    
}
