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

/**
 *
 * @author Beefaroni
 */
public class WorldManager {
    public static Node root;
    public static AssetManager assetManager;
    public static Camera cam;
    public static BulletAppState state;
    public static void setRoot(Node newRoot) {
        root = newRoot;
    }

    public static void setState(BulletAppState state) {
        WorldManager.state = state;
    }
    
    public static void setAssetManager(AssetManager man) { assetManager = man; }
    public static void setCamera(Camera newCam) { cam = newCam; }
}
