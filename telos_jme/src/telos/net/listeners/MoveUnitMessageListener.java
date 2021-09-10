/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.net.listeners;

import com.jme3.network.Client;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import telos.WorldManager;
import telos.lib.core.unit.Unit;
import telos.lib.network.messages.LoginMessage;
import telos.lib.network.messages.unit.MoveUnitMessage;

/**
 *
 * @author Beefaroni
 */
public class MoveUnitMessageListener implements MessageListener<Client> {
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof MoveUnitMessage) {
          // do something with the message
          MoveUnitMessage m = (MoveUnitMessage) message;
          WorldManager.main.enqueue( () -> {
              Unit u = WorldManager.units.get(m.getDbId());
              u.setTravelDestination(m.getTargetLoc());
              if (WorldManager.selectedUnit == u) {
                  System.out.println("SNAPPING");
                  WorldManager.snapMarker(u);
              }
          });
        }
        else {
            System.out.println("Unidentified message received.");
        }
    }
}
