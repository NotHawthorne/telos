/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

/**
 *
 * @author Alyssa Kozma
 */
public class ResourceCost {
    private ResourceTypes _type;
    private int _cost;

    public ResourceTypes getType() {
        return _type;
    }

    public void setType(ResourceTypes _type) {
        this._type = _type;
    }

    public int getCost() {
        return _cost;
    }

    public void setCost(int _cost) {
        this._cost = _cost;
    }
    
}
