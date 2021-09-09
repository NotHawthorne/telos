/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import telos.lib.core.Player;
import telos.lib.core.player.PlayerFactions;

/**
 *
 * @author Beefaroni
 */
public class Scribe {
    private static Connection _conn;
    
    public Scribe() {

    }
    
    public static void init() {
        System.out.println("Initializing Scribe...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            _conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/telos", "telos", "notarealpassword");
        } catch (Exception e) {
            System.out.println("Error connecting to SQL database ");
            e.printStackTrace();
        }
    }
    
    public static Map<String, Player> loadStoredPlayers() {
        Map<String, Player> ret = new HashMap<String, Player>();
        System.out.println("Loading stored players");
        try {
            Statement s = _conn.createStatement();
            ResultSet res = s.executeQuery("SELECT * FROM users");
            if (res != null) {
                while (res.next()) {
                    Player p = new Player();
                    p.setUsername(res.getString("username"));
                    p.setPassword(res.getInt("password"));
                    p.setCredits(res.getInt("credits"));
                    p.setIron(res.getInt("iron"));
                    p.setLumber(res.getInt("lumber"));
                    p.setCrystals(res.getInt("crystals"));
                    p.setFaction(PlayerFactions.valueOf(res.getString("faction")));
                    ret.put(p.getUsername(), p);
                }
                res.close();
            }
        } catch (Exception e) {
            System.out.println("Error loading stored players from SQL DB ");
            e.printStackTrace();
        }
        return ret;
    }
    
    public static boolean registerPlayer(Player p) {
        try {
            Statement s = _conn.createStatement();
            String st = "INSERT INTO users (username, password, credits, iron, lumber, oil, crystals, faction) VALUES(";
            st += "'" + p.getUsername() + "', ";
            st += String.valueOf(p.getPassword()) + ", ";
            st += String.valueOf(p.getCredits()) + ", ";
            st += String.valueOf(p.getIron()) + ", ";
            st += String.valueOf(p.getLumber()) + ", ";
            st += String.valueOf(p.getOil()) + ", ";
            st += String.valueOf(p.getCrystals())+ ", ";
            st += "'" + p.getFaction().toString() + "');";
            s.executeUpdate(st);
        } catch (Exception e) {
            System.out.println("Error registering player. ");
            e.printStackTrace();
        }
        return true;
    }
}
