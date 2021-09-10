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

/**
 *
 * @author Alyssa Kozma
 */
@Serializable
public class MoveUnitMessage extends AbstractMessage {
    private String _id;
    private int _dbId;
    private Vector3f _targetLoc;
    public MoveUnitMessage() { }
    public MoveUnitMessage(String id, Vector3f targetLoc, int dbId) {
        _id = id;
        _targetLoc = targetLoc;
        _dbId = dbId;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public int getDbId() {
        return _dbId;
    }

    public void setDbId(int _dbId) {
        this._dbId = _dbId;
    }
    
    public void setUUID(String id) { _id = id; }
    public void setTargetLoc(Vector3f loc) { _targetLoc = loc; }
    public String getUUID() { return _id; }
    public Vector3f getTargetLoc() { return _targetLoc; }
    public String toString() { return "Move Unit: " + _id + " to " + _targetLoc.toString(); }
}
