/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script.handler;

import bicharo.util.script.Scriptable;
import bicharo.util.script.parser.TagParser;

/**
 *
 * @author root
 */
public abstract class AbstractCommandHandler {
    
    protected final TagParser       parser;
    
    public AbstractCommandHandler(TagParser parser) {
        this.parser = parser;
    }
    
    public abstract boolean handle(String rawCommand, Scriptable entity);
    
}
