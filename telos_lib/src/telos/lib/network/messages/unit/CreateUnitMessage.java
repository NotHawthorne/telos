/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.network.messages.unit;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.util.UUID;
import telos.lib.core.unit.Unit;

/**
 *
 * @author Alyssa Kozma
 */
@Serializable
public class CreateUnitMessage extends AbstractMessage {
    private String _id;
    private Vector3f _loc;
    private String _name;
    private int _hp;
    private int _maxHp;
    private String _type;
    private String _owner;
    private int _dbId;
    private int _chunkX;
    private int _chunkY;
    
    public CreateUnitMessage() { }
    public CreateUnitMessage(String id, Vector3f loc, String name, int hp) {
        _id = id;
        _loc = loc;
        _name = name;
        _hp = hp;
    }
    
    public CreateUnitMessage(Unit u) {
        _id = u.getUUID();
        _dbId = u.getId();
        _loc = u.getLoc();
        _hp = u.getHp();
        _maxHp = u.getMaxHp();
        _type = String.valueOf(u.getType());
        _owner = u.getOwner();
        _chunkX = u.getChunkX();
        _chunkY = u.getChunkY();
        _name = u.getName();
    }

    public int getDbId() {
        return _dbId;
    }

    public void setDbId(int _dbId) {
        this._dbId = _dbId;
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

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public int getMaxHp() {
        return _maxHp;
    }

    public void setMaxHp(int _maxHp) {
        this._maxHp = _maxHp;
    }

    public String getType() {
        return _type;
    }

    public void setType(String _type) {
        this._type = _type;
    }

    public String getOwner() {
        return _owner;
    }
    public void setOwner(String _owner) {
        this._owner = _owner;
    }
    
    public Vector3f getLoc() { return _loc; }
    public String getUUID() { return _id; }
    public String getName() { return _name; }
    public int getHp() { return _hp; }
    public void setLoc(Vector3f loc) { _loc = loc; }
    public void setUUID(String id) { _id = id; }
    public void setName(String name) { _name = name;}
    public void setHp(int hp) { _hp = hp; }
}
