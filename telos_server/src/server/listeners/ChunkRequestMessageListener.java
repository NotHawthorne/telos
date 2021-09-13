/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import server.Chunk;
import server.GameServer;
import server.UserLedger;
import telos.lib.core.unit.Unit;
import telos.lib.network.messages.ChunkRequestMessage;
import telos.lib.network.messages.ChunkResponseMessage;
import telos.lib.network.messages.unit.CreateUnitMessage;

/**
 *
 * @author Alyssa Kozma
 */
public class ChunkRequestMessageListener implements MessageListener<HostedConnection> {
    @Override
    public void messageReceived(HostedConnection source, Message message) {
        
        if (message instanceof ChunkRequestMessage) {
            System.out.println("Handling chunk request");
          // do something with the message
          ChunkRequestMessage chunkRequestMessage = (ChunkRequestMessage) message;
          Chunk c = GameServer.getChunk(chunkRequestMessage.getCoords());
          c.addPlayer(UserLedger.getPlayer(source));
          source.send(new ChunkResponseMessage(c.getSeed(), c.getCoords()));
          
          //send units for that chunk
          for (Unit u : c.getUnits().values()) {
              source.send(new CreateUnitMessage(u));
          }
        }
    }
}
