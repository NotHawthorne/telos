package telos;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.bullet.PhysicsSpace;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Torus;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.BaseStyles;
import telos.lib.core.unit.Unit;
import telos.lib.network.messages.unit.MoveUnitMessage;
import telos.net.ClientConnector;
import world.ChunkTerrain;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    ClientConnector c;
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
                                c.getClient().send(new MoveUnitMessage(WorldManager.selectedUnit.getUUID(), targetLoc, WorldManager.selectedUnit.getId()));
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
        System.out.println("Starting");
        wm = new WorldManager();
        stateManager.attach(wm);
        WorldManager.root = rootNode;
        WorldManager.assetManager = assetManager;
        WorldManager.main = this;
        WorldManager.init();
        inputManager.addMapping("Select",
            new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click
        inputManager.addMapping("Interact",
            new MouseButtonTrigger(MouseInput.BUTTON_RIGHT)); // trigger 2: left-button click
        inputManager.addListener(this.actionListener, "Select");
        inputManager.addListener(this.actionListener, "Interact");

        // Add some elements
        GuiManager.guiNode = guiNode;
        GuiManager.createGameInterface();
        
        ((SimpleApplication) this).getFlyByCamera().setEnabled(false);
        inputManager.removeListener(flyCam);
        GameCam gc = new GameCam(GameCam.UpVector.Y_UP);
        WorldManager.state = gc;
        WorldManager.state.setDebugEnabled(true);
        gc.registerWithInput(inputManager);
        gc.setCenter(new Vector3f(1,1,1));
        gc.setEnabled(true);
        stateManager.attach(gc);
        WorldManager.setCamera(getCamera());
        System.out.println("Here");
        try {
            c = new ClientConnector();
            c.Connect();
            Thread.sleep(500);
            c.Login("NotHawthorne", "notarealpassword");
        }
        catch (Exception e) {
            System.out.println("Error connecting to server" + e.toString());
        }
        //ChunkTerrain t = WorldManager.getChunk(0, 0);
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
