/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.net.listeners;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import telos.GameCam;
import telos.GuiManager;
import telos.WorldManager;
import telos.lib.network.messages.ChunkResponseMessage;

/**
 *
 * @author Alyssa Kozma
 */
public class ChunkResponseMessageListener implements MessageListener<Client> {
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof ChunkResponseMessage) {
            WorldManager.main.enqueue(() -> {
                ChunkResponseMessage m = (ChunkResponseMessage) message;
                System.out.println("Loading chunk!");
                WorldManager.loadChunk(m.getX(), m.getY(), m.getSeed());
                if (!GuiManager.gameInterfaceLoaded)
                    GuiManager.createGameInterface();
            });
        }
    }
}
