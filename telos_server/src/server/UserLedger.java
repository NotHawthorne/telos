/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.HostedConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import telos.lib.core.player.Player;

/**
 *
 * @author Beefaroni
 */
public class UserLedger {
    private static Map<String, HostedConnection> _users = new HashMap<>();
    private static Map<String, Player> _playerData = new HashMap<String, Player>();
    
    public static void init() {
        _playerData = Scribe.loadStoredPlayers();
        for (Player p : _playerData.values()) {
            UserLedger.loadPlayer(p.getUsername(), p);
        }
    }
    
    public static void registerPlayer(Player p, HostedConnection conn) {
        Scribe.registerPlayer(p);
        addUser(p.getUsername(), conn);
        loadPlayer(p.getUsername(), p);
    }
    
    public static Player addUser(String name, HostedConnection conn) {
        _users.put(name, conn);
        return _playerData.get(name);
        //System.out.println("User " + name + " auth'd!");
    }
    public static void loadPlayer(String name, Player p) {
        _playerData.put(name, p);
    }
    public static Player getPlayer(String name) {
        return _playerData.get(name);
    }
    public static Player getPlayer(HostedConnection conn) {
        return _playerData.get(findUser(conn));
    }
    public static HostedConnection getConn(String user) {
        return _users.get(user);
    }
    public static String findUser(HostedConnection conn) {
        for (Entry<String, HostedConnection> e : _users.entrySet()) {
            if (e.getValue() == conn) {
                return e.getKey();
            }
        }
        return null;
    }
}
