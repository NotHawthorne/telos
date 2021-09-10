/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.network.messages;

import com.jme3.math.Vector2f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Alyssa Kozma
 */
@Serializable
public class ChunkRequestMessage extends AbstractMessage {
    private Vector2f _coords;

    public ChunkRequestMessage() { }
    public ChunkRequestMessage(Vector2f coords) {
        this.setCoords(coords);
    }
    
    public Vector2f getCoords() {
        return _coords;
    }

    public void setCoords(Vector2f coords) {
        this._coords = coords;
    }
    
}
