/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script.parser;

/**
 *
 * @author Bob
 */
public abstract class AbstractTagParser {
    
    protected TagParser parser;
    
    public abstract Object parseTag(String tag, Object scriptable);
    
    public void setParser(TagParser parser) {
        this.parser = parser;
    }
    
}
