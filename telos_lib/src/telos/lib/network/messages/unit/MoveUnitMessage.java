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
    private Vector3f _targetLoc;
    public MoveUnitMessage() { }
    public MoveUnitMessage(String id, Vector3f targetLoc) {
        _id = id;
        _targetLoc = targetLoc;
    }
    public void setUUID(String id) { _id = id; }
    public void setTargetLoc(Vector3f loc) { _targetLoc = loc; }
    public String getUUID() { return _id; }
    public Vector3f getTargetLoc() { return _targetLoc; }
}
