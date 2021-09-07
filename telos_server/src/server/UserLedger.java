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

/**
 *
 * @author Beefaroni
 */
public class UserLedger {
    private static Map<String, HostedConnection> _users = new HashMap<>();
    
    public static void addUser(String name, HostedConnection conn) {
        _users.put(name, conn);
        System.out.println("User " + name + " auth'd!");
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
