/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.listeners;

import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.UUID;
import server.UnitLedger;
import server.UserLedger;
import telos.lib.core.Unit;

import telos.lib.network.messages.LoginMessage;
import telos.lib.network.messages.unit.CreateUnitMessage;

/**
 *
 * @author Alyssa Kozma
 */

public class LoginMessageListener implements MessageListener<HostedConnection> {
    @Override
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof LoginMessage) {
          // do something with the message
          LoginMessage loginMessage = (LoginMessage) message;
          UserLedger.addUser(loginMessage.getUsername(), source);
          
          Unit u = new Unit("Worker", 50);
          u.setUUID(UUID.randomUUID().toString());
          u.setLoc(new Vector3f(1.0f, 1.0f, 1.0f));
          UnitLedger.addUnit(u.getUUID(), u);
          source.send(new CreateUnitMessage(u.getUUID(), u.getLoc(), u.getName(), u.getHp()));
          
          
          Unit u2 = new Unit("Worker", 50);
          u2.setUUID(UUID.randomUUID().toString());
          u2.setLoc(new Vector3f(2.0f, 2.0f, 2.0f));
          UnitLedger.addUnit(u2.getUUID(), u2);
          source.send(new CreateUnitMessage(u2.getUUID(), u2.getLoc(), u2.getName(), u2.getHp()));
        }
        else {
            System.out.println("Unidentified message received.");
        }
    }
}