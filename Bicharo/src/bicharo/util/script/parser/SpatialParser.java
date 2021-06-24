/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.util.script.parser;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Bob
 */
public class SpatialParser extends AbstractTagParser {

    Node rootNode;
    
    public SpatialParser(Node rootNode) {
        this.rootNode = rootNode;
    }
    
    @Override
    public Object parseTag(String fullTag, Object object) {
        
        Object obj     = object;
        
        String strippedTag = fullTag.split("#")[0];        
        
        switch (strippedTag) {
            case "rootnode":
                obj = rootNode;
                break;
                
            case "child":
            {
                String[] strAr = fullTag.split("#");
                obj            = (Node) ((Node) obj).getChild(strAr[1]);
                break;
            }

            case "parent":
            {
                obj            = (Node) ((Spatial) obj).getParent();
                break;
            }                    

            case "location":
                obj = ((Spatial) obj).getLocalTranslation();
                break;

            case "distance":
            {
                String[] strAr  = fullTag.split(strippedTag + "#", 2);
                Vector3f check  = (Vector3f) parseTag(strAr[1], object);
                obj             = ((Vector3f) obj).distance(check);   
                break;
            }
            
            case "name":
                obj = ((Spatial) obj).getName();
                break;
            
            case "data":
                String[] strAr = fullTag.split("#");
                String parsedArg = parser.parseTag(strAr[1], object).toString();
                obj = ((Spatial) obj).getUserData(parsedArg);
                break;
            
        }
        
        return obj;
        
    }
    
}
