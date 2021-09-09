/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Torus;
import java.util.HashMap;
import java.util.Map;
import telos.lib.core.Unit;
import world.HelloTerrain;

/**
 *
 * @author Beefaroni
 */
public class WorldManager extends AbstractAppState {
    public static Node root;
    public static AssetManager assetManager;
    public static Camera cam;
    public static BulletAppState state;
    public static Main main;
    public static Map<Integer, Map<Integer, HelloTerrain>> chunks = new HashMap<Integer, Map<Integer, HelloTerrain>>();
    public static Map<String, Unit> units =  new HashMap<String, Unit>();
    public static Unit selectedUnit;
    public static Geometry mark;
    
    
    protected static void initMark() {
      Torus ring = new Torus(30, 30, 0.05f, 0.3f);
      mark = new Geometry("BOOM!", ring);
      Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      mark_mat.setColor("Color", ColorRGBA.Red);
      mark.setMaterial(mark_mat);
      mark.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2, new Vector3f(1, 0, 0)));
    }
    
    public static void snapMarker(Unit target) {
        if (target != null) {
            mark.setLocalTranslation(new Vector3f(
                    WorldManager.selectedUnit.getLoc().x, 
                    WorldManager.getChunk(0,0).getTerrain().getHeight(
                            new Vector2f(
                                    WorldManager.selectedUnit.getLoc().x, 
                                    WorldManager.selectedUnit.getLoc().z
                            )
                    ),
                    WorldManager.selectedUnit.getLoc().z)
            );
            root.attachChild(mark);
        } else {
            root.detachChild(mark);
        }
    }
    
    public static void select(Unit target) {
        selectedUnit = target;
        main.unitSelectionDisplay.setText(target == null ? "None" : target.getName());
        snapMarker(target);
    }
    
    public static void init() {
        System.out.println("Init");
        initMark();
        System.out.println("Init2");
    }
    
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
    
    @Override
    public void update(float tpf) {
        for (Unit u : units.values() ) {
            u.update(tpf);
        }
        snapMarker(selectedUnit);
    }
}
