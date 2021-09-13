package telos;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import telos.lib.core.unit.Unit;
import telos.lib.network.messages.unit.MoveUnitMessage;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    ActionListener actionListener;
    WorldManager wm;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    public Main() {
        this.actionListener = new ActionListener() {
            
            public CollisionResults castFromCursor() {
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = WorldManager.cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                Vector3f dir = WorldManager.cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                // 1. Reset results list.
                CollisionResults results = new CollisionResults();
                // 2. Aim the ray from cam loc to cam direction.
                Ray ray = new Ray(click3d, dir);
                // 3. Collect intersections between Ray and Shootables in results list.
                // DO NOT check collision with the root node, or else ALL collisions will hit the skybox! Always make a separate node for objects you want to collide with.
                WorldManager.root.collideWith(ray, results);
                return results;
            }
            
            public Unit getCollidedUnit(CollisionResults results) {
                Unit ret = null;
                for (int i = 0; i < results.size(); i++) {
                    // For each hit, we know distance, impact point, name of geometry.
                    float dist = results.getCollision(i).getDistance();
                    Vector3f pt = results.getCollision(i).getContactPoint();
                    String hit = results.getCollision(i).getGeometry().getName();
                    Node n = results.getCollision(i).getGeometry().getParent();
                    int x = 0;
                    
                    // travel up scene tree to see if what we hit is a child of a unit
                    while (n != null && !(n instanceof Unit)) {
                        x++;
                        n = n.getParent();
                    }
                    if (n != null) {
                        ret = (Unit)n;
                        break ;
                    } else {
                        ret = null;
                    }
                }
                return ret;
            }
            
            public Vector3f getCollidedLoc(CollisionResults results) {
                return results.getCollision(0).getContactPoint();
            }
            
            @Override
            public void onAction(String name, boolean keyPressed, float tpf) {
               // if (!keyPressed) {
                    switch (name) {
                        case "Select":
                            CollisionResults results = castFromCursor();
                            Unit clickedUnit = getCollidedUnit(results);
                            WorldManager.select(clickedUnit);
                            break ;
                        case "Interact":
                            if (WorldManager.selectedUnit != null) {
                                CollisionResults res = castFromCursor();
                                Vector3f targetLoc = getCollidedLoc(res);
                                WorldManager.conn.getClient().send(new MoveUnitMessage(WorldManager.selectedUnit.getUUID(), targetLoc, WorldManager.selectedUnit.getId()));
                            }
                            break ;
                        default:
                            break ;
                    }
                }
           // }
        };
        
    }

    @Override
    public void simpleInitApp() {
        wm = new WorldManager();
        System.out.println("Starting");
        inputManager.removeListener(flyCam);
        setPauseOnLostFocus(false);
        WorldManager.initWorld(stateManager, rootNode, assetManager, this, wm, inputManager, actionListener, guiNode);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
