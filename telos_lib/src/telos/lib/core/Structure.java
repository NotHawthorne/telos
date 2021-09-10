/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

import telos.lib.core.unit.Unit;
import com.jme3.math.Vector2f;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

/**
 *
 * @author Alyssa Kozma
 */
public class Structure {
    private String _id;
    private String _owner;
    private Vector2f _loc;
    private int _hp;
    private int _maxHp;
    private Map<ResourceTypes, Integer> _cost;
    private int _buildTime;
    private Queue<Entry<Unit, Integer>> _productionQueue; //todo: extend entry for instantiation
    private StructureTypes _type;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public Vector2f getLoc() {
        return _loc;
    }

    public void setLoc(Vector2f _loc) {
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
