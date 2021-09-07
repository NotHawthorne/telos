/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.net.listeners;

import com.jme3.network.Client;
import com.jme3.network.MessageListener;
import com.jme3.network.Message;
import telos.lib.network.messages.LoginMessage;

/**
 *
 * @author Beefaroni
 */
public class LoginMessageListener implements MessageListener<Client> {
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof LoginMessage) {
            LoginMessage m = (LoginMessage) message;
            System.out.println("Ayy");
        }
        else {
            System.out.println("Unhandled message received.");
        }
    }
}
