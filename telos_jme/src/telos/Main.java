package telos;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import telos.net.ClientConnector;
import world.HelloTerrain;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    ClientConnector c;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        ((SimpleApplication) this).getFlyByCamera().setEnabled(false);
        WorldManager.root = rootNode;
        WorldManager.assetManager = assetManager;
        inputManager.removeListener(flyCam);
        GameCam gc = new GameCam(GameCam.UpVector.Y_UP);
        WorldManager.state = gc;
        gc.registerWithInput(inputManager);
        gc.setCenter(new Vector3f(20,0.5f,20));
        gc.setEnabled(true);
        stateManager.attach(gc);
        WorldManager.setCamera(getCamera());
        HelloTerrain t = new HelloTerrain();
        try {
            c = new ClientConnector();
            c.Connect();
            Thread.sleep(500);
            c.Login("NotHawthorne", "notarealpassword");
        }
        catch (Exception e) {
            System.out.println("Error connecting to server" + e.toString());
        }
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
