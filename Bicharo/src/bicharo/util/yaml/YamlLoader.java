/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package bicharo.util.yaml;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;
/**
*
* @author Bob
*/
public class YamlLoader implements AssetLoader {
    
    //Allows for the loading of YAML files from the compiled assets jar
    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        Yaml yaml              = new Yaml();
        InputStream openStream = assetInfo.openStream();
        Object obj             = yaml.load(openStream);
        return obj;
    }
    
}
