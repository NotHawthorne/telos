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
import telos.lib.core.unit.Unit;
import telos.lib.network.messages.unit.CreateUnitMessage;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import telos.GameCam;
import telos.lib.network.messages.ChunkRequestMessage;
import telos.WorldManager;

/**
 *
 * @author Alyssa Kozma
 */
public class CreateUnitMessageListener implements MessageListener<Client> {
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof CreateUnitMessage) {
            //spawn unit in world
            WorldManager.main.enqueue(() -> {
                CreateUnitMessage m = (CreateUnitMessage) message;
                if (WorldManager.getChunk(m.getChunkX(), m.getChunkY()) == null && 
                        (WorldManager._loadingChunk == null || 
                        WorldManager._loadingChunk != new Vector2f(m.getChunkX(), m.getChunkY()))) 
                {
                    source.send(new ChunkRequestMessage(new Vector2f(m.getChunkX(), m.getChunkY())));
                    System.out.println("Breaking unit spawn");
                    return ;
                }
                if (WorldManager.units.containsKey(m.getDbId())) {
                    System.out.println("Duplicate request");
                    return ;
                }
                Unit u = new Unit(m);
                System.out.println("Trying to spawn unit");
                // if we received a unit create message on a chunk we don't have, request that chunk and break -- unit info will come with chunk

                u.spawn();
                u.getController().setPhysicsSpace(WorldManager.state.getPhysicsSpace());
                u.setUUID(m.getUUID());
                u.setModel(WorldManager.assetManager.loadModel("/Models/unit_Harvester_A/unit_Harvester_A.j3o"));
                u.setLocalTranslation(u.getLoc());
                u.getController().setPhysicsLocation(m.getLoc());
                u.getController().setSpatial(u.getModel());
                System.out.println("Received unit id " + m.getDbId());
                u.updateNav(WorldManager.getChunk(0,0).navMesh);
                WorldManager.root.attachChild(u);
                WorldManager.state.getPhysicsSpace().add(u);

                //snap to ground logic
                Vector3f snapToGroundVec = new Vector3f(u.getLoc().x, WorldManager.getChunk(0,0).getTerrain().getHeight(new Vector2f(u.getLoc().x, u.getLoc().z)), u.getLoc().z);
                u.setLoc(snapToGroundVec);
                u.setLocalTranslation(snapToGroundVec);
                ((GameCam)WorldManager.state).setCenter(snapToGroundVec);
                WorldManager.units.put(m.getDbId(), u);
            });
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
