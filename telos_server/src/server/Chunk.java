/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.math.Vector2f;
import java.util.HashMap;
import java.util.Map;
import telos.lib.core.player.Player;
import telos.lib.core.Structure;
import telos.lib.core.unit.Unit;

/**
 *
 * @author Alyssa Kozma
 */
public class Chunk {
    private int _seed = 0;
    private Vector2f _coords;
    private Map<Integer, Unit> _units = new HashMap<>();
    private Map<String, Player> _players = new HashMap<>();
    private Map<String, Structure> _structures = new HashMap<>();
    
    public Chunk(Vector2f coords) {
        _coords = coords;
        _seed = GameServer.genSeed((int)coords.x, (int)coords.y);
    }
    
    public void addStructure(Structure s) {
        _structures.put(s.getId(), s);
    }
    
    public int getPlayerCount() {
        return _players.size();
    }
    
    public void addPlayer(Player p) {
        _players.put(p.getUsername(), p);
    }
    
    public void addUnit(Unit u) {
        _units.put(u.getId(), u);
    }

    public int getSeed() {
        return _seed;
    }

    public void setSeed(int _seed) {
        this._seed = _seed;
    }

    public Vector2f getCoords() {
        return _coords;
    }

    public void setCoords(Vector2f _coords) {
        this._coords = _coords;
    }

    public Map<Integer, Unit> getUnits() {
        return _units;
    }

    public void setUnits(Map<Integer, Unit> _units) {
        this._units = _units;
    }

    public Map<String, Player> getPlayers() {
        return _players;
    }

    public void setPlayers(Map<String, Player> _players) {
        this._players = _players;
    }
    
}
