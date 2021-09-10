package telos.net.listeners;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jme3.math.Vector2f;
import com.jme3.network.Client;
import com.jme3.network.MessageListener;
import com.jme3.network.Message;
import telos.lib.network.messages.ChunkRequestMessage;
import telos.lib.network.messages.LoginMessage;
import telos.lib.network.messages.LoginResponseMessage;

/**
 *
 * @author Alyssa Kozma
 */
public class LoginResponseMessageListener implements MessageListener<Client> {
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof LoginResponseMessage) {
            LoginResponseMessage m = (LoginResponseMessage) message;
            if (m.getResult() == false) {
                System.out.println("Login failure");
                System.exit(1);
            }
            //load world
            source.send(new ChunkRequestMessage(new Vector2f(0, 0)));
        }
        else {
            System.out.println("Unhandled message received.");
        }
    }
}