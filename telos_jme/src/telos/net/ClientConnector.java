/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.net;

import com.jme3.network.AbstractMessage;
import telos.net.listeners.LoginMessageListener;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import telos.lib.network.messages.LoginMessage;
import com.jme3.network.serializing.Serializer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import telos.lib.network.messages.unit.CreateUnitMessage;
import telos.lib.network.messages.unit.MoveUnitMessage;
import telos.net.listeners.CreateUnitMessageListener;

/**
 *
 * @author Beefaroni
 */
public class ClientConnector {
    private Client _client;
    private String _host = "localhost";
    private int _port = 4242;
    private Map<Class<? extends AbstractMessage>, MessageListener> _listeners = new HashMap<>();
    private LoginMessageListener _listener;
    
    public void registerListeners() {
        _listeners.put(LoginMessage.class, new LoginMessageListener());
        _listeners.put(CreateUnitMessage.class, new CreateUnitMessageListener());
        _listeners.put(MoveUnitMessage.class, null);
    }
    
    public ClientConnector() {
        _listener = new LoginMessageListener();
    }
    
    public ClientConnector(String host, int port) {
        _listener = new LoginMessageListener();
        _host = host;
        _port = port;
    }
    
    public void Connect() {
        try {
            registerListeners();
            for (Entry<Class<? extends AbstractMessage>, MessageListener> e : _listeners.entrySet() ) {
                Serializer.registerClass(e.getKey());
            }
            _client = Network.connectToServer(_host, _port);
            for (Entry<Class<? extends AbstractMessage>, MessageListener> e : _listeners.entrySet() ) {
                if (e.getValue() != null) {
                    _client.addMessageListener(e.getValue(), e.getKey());
                }
            }
            _client.start();
            System.out.println("Client listener running...");
        } catch (Exception e) {
            System.out.println("Error connecting to server..." + e.toString());
        }
    }
    
    // should maybe return response
    public void Login(String username, String password) {
        _client.send(new LoginMessage(username, password));
    }
    
    /**
     * This function should load the client connector config from a config file in the future.
     * @param filepath 
     */
    
    public void LoadCfg(String filepath) { }
}
