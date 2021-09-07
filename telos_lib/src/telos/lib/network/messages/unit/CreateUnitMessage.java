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
public class CreateUnitMessage extends AbstractMessage {
    private String _id;
    private Vector3f _loc;
    private String _name;
    private int _hp;
    
    public CreateUnitMessage() { }
    public CreateUnitMessage(String id, Vector3f loc, String name, int hp) {
        _id = id;
        _loc = loc;
        _name = name;
        _hp = hp;
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
