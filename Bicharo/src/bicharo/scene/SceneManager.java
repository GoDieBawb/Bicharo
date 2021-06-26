/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicharo.scene;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 *
 * @author Bob
 */
public class SceneManager {
    
    Node         scene;
    Node         rootNode;
    AssetManager assetManager;
    
    public SceneManager(AssetManager assetManager, Node rootNode) {
        this.assetManager = assetManager;
        this.rootNode     = rootNode;
        scene             = new Node();
        scene.setName("Scene");
    }
    
    public void initScene(String scenePath) {
        scene.removeFromParent();
        scene = (Node) assetManager.loadModel("Scenes/"+scenePath+".j3o");
        rootNode.attachChild(scene);
        scene.setName(scenePath);
        makeUnshaded(scene);
    }
    
    public Node getScene() {
        return scene;
    }
    
    public void update(float tpf) {
    
    }
    
    public void makeUnshaded(Node node) {
        
        SceneGraphVisitor sgv = new SceneGraphVisitor() {
            
            @Override
            public void visit(Spatial spatial) {
                
                if (spatial instanceof Geometry) {
                    
                    Geometry geom = (Geometry) spatial;
                    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                    Material tat = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
                    
                    if (geom.getMaterial().getTextureParam("DiffuseMap_1") != null) {
                        
                        String     alTexPath  = geom.getMaterial().getTextureParam("AlphaMap").getTextureValue().getName().substring(1);
                        TextureKey alkey      = new TextureKey(alTexPath, false);
                        Texture    alTex      = assetManager.loadTexture(alkey);
                        
                        tat.setTexture("Alpha", alTex);
                        //tat.setTexture("Alpha", geom.getMaterial().getTextureParam("AlphaMap").getTextureValue());
                        
                        if (geom.getMaterial().getTextureParam("DiffuseMap") != null) {
                            
                            tat.setTexture("Tex1", geom.getMaterial().getTextureParam("DiffuseMap").getTextureValue());
                            tat.getTextureParam("Tex1").getTextureValue().setWrap(Texture.WrapMode.Repeat);
                            tat.setFloat("Tex1Scale", Float.valueOf(geom.getMaterial().getParam("DiffuseMap_0_scale").getValueAsString()));
                            
                        }
                        
                        if (geom.getMaterial().getTextureParam("DiffuseMap_1") != null) {
                            
                            tat.setTexture("Tex2", geom.getMaterial().getTextureParam("DiffuseMap_1").getTextureValue());
                            tat.getTextureParam("Tex2").getTextureValue().setWrap(Texture.WrapMode.Repeat);
                            tat.setFloat("Tex2Scale", Float.valueOf(geom.getMaterial().getParam("DiffuseMap_1_scale").getValueAsString()));
                            
                        }
                        
                        if (geom.getMaterial().getTextureParam("DiffuseMap_2") != null) {
                            
                            tat.setTexture("Tex3", geom.getMaterial().getTextureParam("DiffuseMap_2").getTextureValue());
                            tat.getTextureParam("Tex3").getTextureValue().setWrap(Texture.WrapMode.Repeat);
                            tat.setFloat("Tex3Scale", Float.valueOf(geom.getMaterial().getParam("DiffuseMap_2_scale").getValueAsString()));
                            
                        }
                        
                        geom.setMaterial(tat);
                        tat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
                        
                    }
                    
                    else if (geom.getMaterial().getTextureParam("DiffuseMap") != null) {
                        mat.setTexture("ColorMap", geom.getMaterial().getTextureParam("DiffuseMap").getTextureValue());
                        mat.setFloat("AlphaDiscardThreshold", .01f);
                        geom.setMaterial(mat);
                    }
                    
                    geom.getMaterial().getAdditionalRenderState().setWireframe(true);
                    geom.getMesh().updateBound();
                    //System.out.println(geom.getModelBound());
                    
                }
                
            }
            
        };
        
        node.depthFirstTraversal(sgv);
        
    }  
    
}
