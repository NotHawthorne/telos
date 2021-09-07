/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Beefaroni
 */
public class WorldEntity extends Node {
    public String _id;
    public Vector3f _loc;
    public WorldEntity() { }
    public WorldEntity(String id, Vector3f loc) {
        _id = id;
        _loc = loc;
    }
    public void setUUID(String id) { _id = id; }
    public void setLoc(Vector3f loc) { _loc = loc; }
    public String getUUID() { return _id; }
    public Vector3f getLoc() { return _loc; }
}
