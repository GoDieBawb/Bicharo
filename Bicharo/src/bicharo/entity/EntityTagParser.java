/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.entity;

import bicharo.Main;
import bicharo.util.script.parser.AbstractTagParser;

/**
 *
 * @author Bob
 */
public class EntityTagParser extends AbstractTagParser {
   
    @Override
    public Object parseTag(String fullTag, Object object) {
        
        Object obj = object;
        String strippedTag = fullTag.split("#")[0];   
        
        switch (strippedTag) {
            
            case "ishid":
                obj = (Boolean) ((Entity) obj).isHid();
                break;   
                
        }
        return obj;
    }    
    
}
