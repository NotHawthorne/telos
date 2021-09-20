/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

import telos.lib.core.unit.Unit;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import telos.lib.core.player.PlayerFactions;
import telos.lib.network.messages.CreateStructureMessage;

/**
 *
 * @author Alyssa Kozma
 */
public class Structure {
    private String _id;
    private int _dbId;
    private String _name;
    private String _owner;
    private Vector3f _loc;
    private int _minDmg;
    private int _maxDmg;
    private int _attackRange;
    private float _attackSpeed;
    private int _hp;
    private int _maxHp;
    private Map<ResourceTypes, Integer> _cost = new HashMap<>();
    private int _buildTime;
    private Queue<Entry<Unit, Integer>> _productionQueue; //todo: extend entry for instantiation
    private StructureTypes _type;
    private String _modelPath;
    private PlayerFactions _faction;
    private int _chunkX;
    private int _chunkY;
    
    public Structure(Structure s) {
        _dbId = s.getDbId();
        _owner = s.getOwner();
        _hp = s.getHp();
        _maxHp = s.getMaxHp();
        for (Entry<ResourceTypes, Integer> e : s.getCost().entrySet()) {
            _cost.put(e.getKey(), e.getValue());
        }
        _buildTime = s.getBuildTime();
        _type = s.getType();
    }
    
    public Structure() { }
    
    public Structure(CreateStructureMessage m) {
        _id = m.getId();
        _dbId = m.getDbId();
        _loc = m.getLoc();
        _hp = m.getHp();
        _maxHp = m.getMaxHp();
        _type = StructureTypes.valueOf(m.getType());
        _owner = m.getOwner();
        _chunkX = m.getChunkX();
        _chunkY = m.getChunkY();
        _name = m.getName();
    }

    public int getChunkX() {
        return _chunkX;
    }

    public void setChunkX(int _chunkX) {
        this._chunkX = _chunkX;
    }

    public int getChunkY() {
        return _chunkY;
    }

    public void setChunkY(int _chunkY) {
        this._chunkY = _chunkY;
    }

    public PlayerFactions getFaction() {
        return _faction;
    }

    public void setFaction(PlayerFactions _faction) {
        this._faction = _faction;
    }

    public int getMinDmg() {
        return _minDmg;
    }

    public void setMinDmg(int _minDmg) {
        this._minDmg = _minDmg;
    }

    public int getMaxDmg() {
        return _maxDmg;
    }

    public void setMaxDmg(int _maxDmg) {
        this._maxDmg = _maxDmg;
    }

    public int getAttackRange() {
        return _attackRange;
    }

    public void setAttackRange(int _attackRange) {
        this._attackRange = _attackRange;
    }

    public float getAttackSpeed() {
        return _attackSpeed;
    }

    public void setAttackSpeed(float _attackSpeed) {
        this._attackSpeed = _attackSpeed;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getModelPath() {
        return _modelPath;
    }

    public void setModelPath(String _modelPath) {
        this._modelPath = _modelPath;
    }

    public int getDbId() {
        return _dbId;
    }

    public void setDbId(int _dbId) {
        this._dbId = _dbId;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public Vector3f getLoc() {
        return _loc;
    }

    public void setLoc(Vector3f _loc) {
        this._loc = _loc;
    }

    public String getOwner() {
        return _owner;
    }

    public void setOwner(String _owner) {
        this._owner = _owner;
    }

    public int getHp() {
        return _hp;
    }

    public void setHp(int _hp) {
        this._hp = _hp;
    }

    public int getMaxHp() {
        return _maxHp;
    }

    public void setMaxHp(int _maxHp) {
        this._maxHp = _maxHp;
    }

    public Map<ResourceTypes, Integer> getCost() {
        return _cost;
    }

    public void setCost(Map<ResourceTypes, Integer> _cost) {
        this._cost = _cost;
    }

    public int getBuildTime() {
        return _buildTime;
    }

    public void setBuildTime(int _buildTime) {
        this._buildTime = _buildTime;
    }

    public Queue<Entry<Unit, Integer>> getProductionQueue() {
        return _productionQueue;
    }

    public void setProductionQueue(Queue<Entry<Unit, Integer>> _productionQueue) {
        this._productionQueue = _productionQueue;
    }

    public StructureTypes getType() {
        return _type;
    }

    public void setType(StructureTypes _type) {
        this._type = _type;
    }
    
    
}
