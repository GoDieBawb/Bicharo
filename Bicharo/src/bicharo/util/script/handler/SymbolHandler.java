package bicharo.util.script.handler;

import bicharo.util.script.Scriptable;
import bicharo.util.script.parser.TagParser;

/**
 *
 * @author root
 */
public class SymbolHandler extends AbstractCommandHandler {
    
    public SymbolHandler(TagParser parser) {
        super(parser);
    }
    
    @Override
    public boolean handle(String rawCommand, Scriptable scriptable) {
        
        boolean  handled = true;
        String[] args    = rawCommand.split(" ");
        String   command = args[0];
            
        switch (command) {

            case "boolean":
                
                boolean boolSym = false;
                
                if (args[2].contains("<") && args[2].contains(">")) {
                    boolSym = (Boolean) parser.parseTag(args[2], scriptable);
                }
                else if (args[2].contains("true")) {
                    boolSym = true;
                }
                      
                scriptable.getScript().getSymTab().put(args[1], boolSym);
                
                break;

            case "int":
                
                int intSym;
                
                if (args[2].contains("<") && args[2].contains(">")) {
                    intSym = (Integer) parser.parseTag(args[2], scriptable);
                }
                else {
                    intSym = Integer.valueOf(args[2].split("#")[1]);
                }
                
                scriptable.getScript().getSymTab().put(args[1], intSym);
                break;

            case "float":
                
                float floatSym;
                
                if (args[2].contains("<") && args[2].contains(">")) {
                    floatSym = (Float) parser.parseTag(args[2], scriptable);
                }                
                else {
                    floatSym = Float.valueOf(args[2].split("#")[1]);
                }
                scriptable.getScript().getSymTab().put(args[1], floatSym);
                break;

            case "String":
                
                String stringSym;
                
                if (args[2].contains("<") && args[2].contains(">")) {
                    stringSym = (String) parser.parseTag(args[2], scriptable);
                }
                else {
                    stringSym = args[2];
                }
                
                scriptable.getScript().getSymTab().put(args[1], stringSym);
                break;

            case "Object":
                Object obj = parser.parseTag(args[2], scriptable);
                scriptable.getScript().getSymTab().put(args[1], obj);
                break;
                
            default:
                handled = false;
                break;

        }
        
        return handled;
        
    }
    
}
