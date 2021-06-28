package bicharo.util.script.handler;

import bicharo.util.UtilityHandler;
import bicharo.util.script.Scriptable;
import bicharo.util.script.parser.TagParser;
import bicharo.util.script.handler.*;
import com.jme3.app.state.AppStateManager;
import java.util.ArrayList;


/**
 *
 * @author Bob
 */
public class ScriptHandler {

    public static Scriptable SCRIPTABLE;
    
    private final TagParser        parser;
    private final SymbolHandler    symbolHandler;
    private final LogicHandler     logicHandler;
    private final UtilityHandler   utilityHandler;
    private final DebugHandler     debugHandler;
    
    private final ArrayList<AbstractCommandHandler> handlers;
    
    public ScriptHandler(AppStateManager stateManager) {
        parser            = new TagParser(stateManager);
        symbolHandler     = new SymbolHandler(parser);
        utilityHandler    = new UtilityHandler(parser);
        logicHandler      = new LogicHandler(parser);
        debugHandler      = new DebugHandler(parser);
        handlers          = new ArrayList<>();
        handlers.add(new SpatialHandler(parser));
    }
    
    public TagParser getTagParser() {
        return parser;
    }
    
    public void registerHandler(AbstractCommandHandler handler) {
        handlers.add(handler);
    }
    
    //Takes the current line from the script and executes the proper command
    public void parse(ArrayList commands, Scriptable scriptable) {
        
        SCRIPTABLE = scriptable;
        
        //Start with assuming there is no condition to run the script
        logicHandler.setGo(true);
        logicHandler.setMet(false);
        
        for (int i = 0; i < commands.size(); i++) {
                
            String[] args     = ((String) commands.get(i)).split(" ");
            String   command  = args[0];
            
            //Logic Handler Determines Go Check
            if (logicHandler.handle((String) commands.get(i), scriptable))
                continue;
            
            //If not able to go continue onto the next command
            if(!logicHandler.isGo()) {
                continue;
            }
            
            //On Go if Command is Return end Parse
            if (command.equals("return"))
                return;
            
            
            if (symbolHandler.handle((String) commands.get(i), scriptable))
                continue;

            if (debugHandler.handle((String) commands.get(i), scriptable))
                continue;
            
            if (utilityHandler.handle((String) commands.get(i), scriptable))
                continue;
            
            boolean handled = false;
            for (int j = 0; j < handlers.size(); j++) {
                if (handlers.get(j).handle((String) commands.get(i), scriptable)) {
                    handled = true;
                    break;
                }
            }
            if (handled) continue;
            
            //If Commands Go Unhandled They are Unknown
            System.out.println("Unknown command: " + command);
            
        }
        
    }
    
}