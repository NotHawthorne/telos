/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.net.listeners;

import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import telos.WorldManager;
import telos.lib.core.Unit;
import telos.lib.network.messages.unit.CreateUnitMessage;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import telos.GameCam;

/**
 *
 * @author Beefaroni
 */
public class CreateUnitMessageListener implements MessageListener<Client> {
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof CreateUnitMessage) {
            //spawn unit in world
            CreateUnitMessage m = (CreateUnitMessage) message;
            Unit u = new Unit(m.getName(), m.getHp());
            u.getController().setPhysicsSpace(WorldManager.state.getPhysicsSpace());
            u.setUUID(m.getUUID());
            u.setLoc(m.getLoc());
            u.setModel(WorldManager.assetManager.loadModel("/Models/paladin_prop_j_nordstrom/paladin_prop_j_nordstrom.j3o"));
            u.setLocalTranslation(u.getLoc());
            u.getController().setPhysicsLocation(m.getLoc());
            u.getController().setSpatial(u.getModel());
            WorldManager.main.enqueue(() -> {
                u.updateNav(WorldManager.getChunk(0,0).navMesh);
                WorldManager.root.attachChild(u);
                WorldManager.state.getPhysicsSpace().add(u);
                
                //snap to ground logic
                System.out.println("Init: " + u.getLocalTranslation().toString());
                Vector3f snapToGroundVec = new Vector3f(u.getLoc().x, WorldManager.getChunk(0,0).getTerrain().getHeight(new Vector2f(u.getLoc().x, u.getLoc().z)), u.getLoc().z);
                u.setLoc(snapToGroundVec);
                u.setLocalTranslation(snapToGroundVec);
                System.out.println("after: " + snapToGroundVec.toString());
                ((GameCam)WorldManager.state).setCenter(snapToGroundVec);
            });
            WorldManager.units.put(m.getUUID(), u);
            /*
            Box b = new Box(1, 1, 1);
            Geometry geom = new Geometry("Box", b);

            Material mat = new Material(WorldManager.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Blue);
            geom.setMaterial(mat);
            geom.setLocalTranslation(m.getLoc());

            WorldManager.root.attachChild(geom)
            ;*/
        }
    }
}
