package bicharo.util.script;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Bawb
 */
public interface Scriptable {
    
    void setScript(Script Script);
    
    public Script getScript();
    
    public void setProximity();
    
    public boolean inProx();
    
    public void setName(String name);
    
    public void setModel(Spatial model);
    
    public Spatial getModel();
    
    public String getName();
    
    public Vector3f getLocalTranslation();
    
}
