/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos;

import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;
import world.HelloTerrain;

/**
 *
 * @author Beefaroni
 */
public class WorldManager {
    public static Node root;
    public static AssetManager assetManager;
    public static Camera cam;
    public static BulletAppState state;
    public static Main main;
    public static Map<Integer, Map<Integer, HelloTerrain>> chunks = new HashMap<Integer, Map<Integer, HelloTerrain>>();
    
    public static void setRoot(Node newRoot) {
        root = newRoot; 
    }

    public static void setState(BulletAppState state) {
        WorldManager.state = state;
    }
    
    public static void setAssetManager(AssetManager man) { assetManager = man; }
    public static void setCamera(Camera newCam) { cam = newCam; }
    
    public static HelloTerrain getChunk(int x, int y) {
        if (!chunks.containsKey(y)) {
            chunks.put(y, new HashMap<Integer, HelloTerrain>());
        }
        if (!chunks.get(y).containsKey(x)) {
            chunks.get(y).put(x, new HelloTerrain());
        }
        return chunks.get(y).get(x);
    }
}
