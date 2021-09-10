/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.listeners;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.UUID;
import server.GameServer;
import server.UnitLedger;
import server.UserLedger;
import telos.lib.core.player.Player;
import telos.lib.core.unit.Unit;
import telos.lib.core.player.PlayerFactions;
import telos.lib.core.unit.UnitTypes;

import telos.lib.network.messages.LoginMessage;
import telos.lib.network.messages.LoginResponseMessage;
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

          //register if nonexistant
          if (UserLedger.getPlayer(loginMessage.getUsername()) == null) {
              Player p = new Player();
              p.setUsername(loginMessage.getUsername());
              p.setPassword(loginMessage.getPassword().hashCode());
              p.setCredits(500);
              p.setLumber(500);
              p.setIron(500);
              p.setOil(0);
              p.setCrystals(0);
              p.setFaction(PlayerFactions.SPACE_MARINES); // need to provide a way for users to select this
              UserLedger.registerPlayer(p, source);
              System.out.println("Registered player");
          }
          // return if exists
          else if (UserLedger.getPlayer(loginMessage.getUsername()).getPassword() == loginMessage.getPassword().hashCode()) {
                Player p = UserLedger.addUser(loginMessage.getUsername(), source);
                source.send(new LoginResponseMessage(true, 0, 0));
                Unit u2 = UnitLedger.createUnit(UnitLedger.getUnitInfo(UnitTypes.WORKER), p, GameServer.getChunk(new Vector2f(0, 0)), new Vector3f(1.0f, 1.0f, 1.0f));
                //Unit u = new Unit("Worker", 50);
                u2.setLoc(new Vector3f(1.0f, 1.0f, 1.0f));
                source.send(new CreateUnitMessage(u2));
                System.out.println("Successful login");
          }
          else {
              source.send(new LoginResponseMessage(false, -1, -1));
                System.out.println("Failed login");
              //reject
          }
        }
        else {
            System.out.println("Unidentified message received.");
        }
    }
}