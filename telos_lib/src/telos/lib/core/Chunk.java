/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector2f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;
import telos.lib.core.ChunkTerrain;
import telos.lib.core.player.Player;
import telos.lib.core.Structure;
import telos.lib.core.unit.Unit;

/**
 *
 * @author Alyssa Kozma
 */
public class Chunk {
    private int _seed = -1;
    private Vector2f _coords = null;
    private Map<Integer, Unit> _units = new HashMap<>();
    private Map<String, Player> _players = new HashMap<>();
    private Map<String, Structure> _structures = new HashMap<>();
    private ChunkTerrain _terrain = null;
    private AssetManager _assetManager = null;
    private Camera _cam = null;
    private Node _rootNode = null;
    private BulletAppState _state = null;
    private boolean _isServer = true;
    
    public Chunk(Vector2f coords) {
        _coords = coords;
        _seed = genSeed((int)coords.x, (int)coords.y);
        _isServer = true;
        _terrain = new ChunkTerrain(null, null, null, null, _seed);
    }
    
    public Chunk(Vector2f coords, AssetManager m, Camera c, Node root, BulletAppState st) {
        _coords = coords;
        _seed = genSeed((int)coords.x, (int)coords.y);
        _assetManager = m;
        _cam = c;
        _rootNode = root;
        _state = st;
    }
    
    public void createTerrain() {
        if (_coords == null || _seed == -1) {
            System.err.println("failed to initialize chunk, null coords");
            return ;
        }
        if (_terrain == null) {
            _terrain = new ChunkTerrain(_assetManager, _cam, _rootNode, _state, _seed);
        }
        _terrain.spawnTerrain();
        _terrain.spawnResources();
    }

    public AssetManager getAssetManager() {
        return _assetManager;
    }

    public void setAssetManager(AssetManager _assetManager) {
        this._assetManager = _assetManager;
    }

    public Camera getCam() {
        return _cam;
    }

    public void setCam(Camera _cam) {
        this._cam = _cam;
    }

    public Node getRootNode() {
        return _rootNode;
    }

    public void setRootNode(Node _rootNode) {
        this._rootNode = _rootNode;
    }

    public BulletAppState getState() {
        return _state;
    }

    public void setState(BulletAppState _state) {
        this._state = _state;
    }

    public boolean isIsServer() {
        return _isServer;
    }

    public void setIsServer(boolean _isServer) {
        this._isServer = _isServer;
    }

    public Map<String, Structure> getStructures() {
        return _structures;
    }

    public void setStructures(Map<String, Structure> _structures) {
        this._structures = _structures;
    }

    public ChunkTerrain getTerrain() {
        return _terrain;
    }

    public void setTerrain(ChunkTerrain _terrain) {
        this._terrain = _terrain;
    }
    
    public static int genSeed(int x, int y) {
        int xx = x % 2 == 0 ? x / 2 : (x + 1) / -2;
        int yy = y % 2 == 0 ? y / 2 : (y + 1) / -2;
        return (xx >= yy ? (xx * xx + xx + yy) : (yy * yy + xx));
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
