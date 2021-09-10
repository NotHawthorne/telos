/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import telos.lib.core.player.Player;
import telos.lib.core.ResourceTypes;
import telos.lib.core.unit.Unit;
import telos.lib.core.unit.UnitTypes;
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
        int storedPlayers = 0;
        try {
            Statement s = _conn.createStatement();
            ResultSet res = s.executeQuery("SELECT * FROM users");
            if (res != null) {
                while (res.next()) {
                    Player p = new Player();
                    p.setId(res.getInt("id"));
                    p.setUsername(res.getString("username"));
                    p.setPassword(res.getInt("password"));
                    p.setCredits(res.getInt("credits"));
                    p.setIron(res.getInt("iron"));
                    p.setLumber(res.getInt("lumber"));
                    p.setCrystals(res.getInt("crystals"));
                    p.setFaction(PlayerFactions.valueOf(res.getString("faction")));
                    ret.put(p.getUsername(), p);
                    storedPlayers++;
                }
                res.close();
            }
        } catch (Exception e) {
            System.out.println("Error loading stored players from SQL DB ");
            e.printStackTrace();
        }
        System.out.println("Loaded " + storedPlayers + " stored players!");
        return ret;
    }
    
    public static Map<UnitTypes, Unit> loadUnitInfo() {
        Map<UnitTypes, Unit> ret = new HashMap<UnitTypes, Unit>();
        System.out.println("Loading unit info...");
        int loadedUnits = 0;
        try {
            Statement s = _conn.createStatement();
            ResultSet res = s.executeQuery("SELECT * FROM unit_info");
            if (res != null) {
                while (res.next()) {
                    Unit u = new Unit();
                    u.setName(res.getString("name"));
                    u.setMaxHp(res.getInt("max_hp"));
                    u.setHp(res.getInt("max_hp"));
                    u.setMinDmg(res.getInt("min_dmg"));
                    u.setMaxDmg(res.getInt("max_dmg"));
                    u.setAttackRange(res.getInt("attack_range"));
                    u.setCanBuild(res.getInt("can_build") > 0 ? true : false);
                    Map<ResourceTypes, Integer> cost = new HashMap<>();
                    for (int i = 1; i != 6; i++) {
                        if (res.getInt("cost_val" + String.valueOf(i)) > 0) {
                            cost.put(ResourceTypes.valueOf(res.getString("cost_type" + String.valueOf(i))), res.getInt("cost_val" + String.valueOf(i)));
                        }
                    }
                    u.setCost(cost);
                    u.setTrainingTime(res.getInt("training_time"));
                    u.setFaction(PlayerFactions.valueOf(res.getString("faction")));
                    u.setType(UnitTypes.valueOf(res.getString("name").toUpperCase()));
                    ret.put(u.getType(), u);
                    loadedUnits++;
                    System.out.println("Loaded unit template");
                    System.out.println(u.toString());
                }
                res.close();
            }
        } catch (Exception e) {
            
        }
        System.out.println("Loaded " + loadedUnits + " unit types!");
        return ret;
    }
    
    public static Map<Integer, Unit> loadStoredUnits(Map<UnitTypes, Unit> inf) {
        Map<Integer, Unit> ret = new HashMap<Integer, Unit>();
        System.out.println("Loading spawned units...");
        int spawnedUnits = 0;
        try {
            Statement s = _conn.createStatement();
            ResultSet res = s.executeQuery("SELECT * FROM units");
            if (res != null) {
                while (res.next()) {
                    // load from template
                    Unit u = new Unit(inf.get(UnitTypes.valueOf(res.getString("type"))));
                    
                    // get info about this one
                    u.setId(res.getInt("id"));
                    u.setHp(res.getInt("hp"));
                    u.setLoc(new Vector3f(res.getFloat("loc_x"), res.getFloat("loc_y"), res.getFloat("loc_z")));
                    u.setChunkX(res.getInt("chunk_x"));
                    u.setChunkY(res.getInt("chunk_y"));
                    u.setOwner(res.getString("owner_id"));
                    GameServer.getChunk(new Vector2f(res.getInt("chunk_x"), res.getInt("chunk_y"))).addUnit(u);
                    ret.put(res.getInt("id"), u);
                    System.out.println("Loaded spawned unit");
                    System.out.println(u.toString());
                    spawnedUnits++;
                }
            }
        } catch (Exception e) {
            
        }
        System.out.println("Spawned " + spawnedUnits + " units!");
        return ret;
    }
    
    public static int saveUnit(Unit u) {
        String generatedColumns[] = { "id" };
        try {
            String st = "INSERT INTO units(owner_id, hp, loc_x, loc_y, loc_z, chunk_x, chunk_y, type) VALUES(";
            st += "'" + u.getOwner() + "', ";
            st += String.valueOf(u.getHp()) + ", ";
            st += String.valueOf(u.getLoc().x) + ", ";
            st += String.valueOf(u.getLoc().y) + ", ";
            st += String.valueOf(u.getLoc().z) + ", ";
            st += String.valueOf(u.getChunkX()) + ", ";
            st += String.valueOf(u.getChunkY()) + ", ";
            st += "'" + String.valueOf(u.getType()) + "');";
            PreparedStatement s = _conn.prepareStatement(st, generatedColumns);
            s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            if (rs.next())
                System.out.println("Inserted new unit id " + (int)rs.getLong(1));
            return (int)rs.getLong(1);
       } catch (Exception e) {
            System.out.println("Error saving unit");
            e.printStackTrace();
            return -1;
        }
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
            return false;
        }
        return true;
    }
}
