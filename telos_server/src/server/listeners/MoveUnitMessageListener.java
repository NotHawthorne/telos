/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import server.UnitLedger;
import server.UserLedger;
import telos.lib.core.player.Player;
import telos.lib.core.unit.Unit;
import telos.lib.network.messages.LoginMessage;
import telos.lib.network.messages.unit.MoveUnitMessage;

/**
 *
 * @author Beefaroni
 */
public class MoveUnitMessageListener implements MessageListener<HostedConnection> {
    @Override
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof MoveUnitMessage) {
          // do something with the message
          MoveUnitMessage m = (MoveUnitMessage) message;
          System.out.println(m.toString());
          Player p = UserLedger.getPlayer(source);
          Unit u = UnitLedger.getUnit(m.getDbId());
          System.out.println(u.toString());
          if (u == null)
              System.out.println("Unit null");
          else if (p == null)
              System.out.println("Couldnt find player");
          if (p.getUsername().equals(u.getOwner())) {
              UnitLedger.getUnit(m.getDbId()).setLoc(m.getTargetLoc());
              System.out.println("Moved " + m.getUUID() + " to " + m.getTargetLoc().toString());
              source.send(message);
          }
          else {
              System.out.println("Someone tried to send a malicious packet to move someone elses unit!");
          }
        }
        else {
            System.out.println("Unidentified message received.");
        }
    }
}
