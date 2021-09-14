/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

import com.jme3.math.Vector3f;

/**
 *
 * @author Alyssa Kozma
 */
public class ResourceNode {
    private int _id;
    private ResourceTypes _type;
    private int _amt;
    private Vector3f _loc;

    public ResourceNode(int _id, ResourceTypes _type, int _amt, Vector3f _loc) {
        this._id = _id;
        this._type = _type;
        this._amt = _amt;
        this._loc = _loc;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public ResourceTypes getType() {
        return _type;
    }

    public void setType(ResourceTypes _type) {
        this._type = _type;
    }

    public int getAmt() {
        return _amt;
    }

    public void setAmt(int _amt) {
        this._amt = _amt;
    }

    public Vector3f getLoc() {
        return _loc;
    }

    public void setLoc(Vector3f _loc) {
        this._loc = _loc;
    }
    
}
