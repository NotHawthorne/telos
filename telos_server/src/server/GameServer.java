/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.HostedConnection;
import server.listeners.LoginMessageListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.listeners.ChunkRequestMessageListener;
import server.listeners.MoveUnitMessageListener;
import telos.lib.network.messages.ChunkRequestMessage;
import telos.lib.network.messages.ChunkResponseMessage;
import telos.lib.network.messages.LoginMessage;
import telos.lib.network.messages.LoginResponseMessage;
import telos.lib.network.messages.unit.CreateUnitMessage;
import telos.lib.network.messages.unit.MoveUnitMessage;

/**
 *
 * @author Alyssa Kozma
 */
public class GameServer {
    private Server _server;
    private int _port = 4242;
    public static Map<Class<? extends AbstractMessage>, MessageListener> _listeners = new HashMap<>();
    public static Map<String, HostedConnection> _authenticatedUsers = new HashMap<>();
    public static Map<Integer, Chunk> _chunks = new HashMap<>();
    public int seed = 42;
    //private LoginMessageListener _listener;
    
    private void registerListeners() {
        _listeners.put(LoginMessage.class, new LoginMessageListener());
        _listeners.put(CreateUnitMessage.class, null);
        _listeners.put(MoveUnitMessage.class, new MoveUnitMessageListener());
        _listeners.put(LoginResponseMessage.class, null);
        _listeners.put(ChunkRequestMessage.class, new ChunkRequestMessageListener());
        _listeners.put(ChunkResponseMessage.class, null);
    }
    
    public static Chunk getChunk(Vector2f coords) {
        if (_chunks.get(GameServer.genSeed((int)coords.x, (int)coords.y)) == null) {
            _chunks.put(GameServer.genSeed((int)coords.x, (int)coords.y), new Chunk(coords));
        }
        return _chunks.get(GameServer.genSeed((int)coords.x, (int)coords.y));
    }
    
    public Chunk getRandomStartingChunk() {
        return null;
    }
    
    public GameServer() {
        //_listener = new LoginMessageListener();
    }
    
    public GameServer(int port) {
        setPort(port);
        //_listener = new LoginMessageListener();
    }
    
    public static int genSeed(int x, int y) {
        int xx = x % 2 == 0 ? x / 2 : (x + 1) / -2;
        int yy = y % 2 == 0 ? y / 2 : (y + 1) / -2;
        return (xx >= yy ? (xx * xx + xx + yy) : (yy * yy + xx));
    }
    
    public void startServer() {
        try {
            registerListeners();
            for (Map.Entry<Class<? extends AbstractMessage>, MessageListener> entry : _listeners.entrySet()) {
                Serializer.registerClass(entry.getKey());
            }
            _server = Network.createServer(_port); 
            for (Map.Entry<Class<? extends AbstractMessage>, MessageListener> entry : _listeners.entrySet()) {
                if (entry.getValue() != null) {
                    _server.addMessageListener(entry.getValue(), entry.getKey());
                }
            }
            //_server.addMessageListener(_listener, LoginMessage.class);
            _server.start();
            System.out.println("Server running...");
        } catch (Exception e) {
            System.out.println("Error initializing gameserver " + e.toString());
        }
    }
    
    public void setPort(int newPort) { _port = newPort; }
    public int getPort() { return _port; }
}
