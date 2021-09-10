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
public class ChunkResponseMessage extends AbstractMessage {
    private int _seed;
    private int _x;
    private int _y;

    public ChunkResponseMessage() { }
    
    public ChunkResponseMessage(int seed, Vector2f coords) {
        _x = (int)coords.x;
        _y = (int)coords.y;
        _seed = seed;
    }

    public int getX() {
        return _x;
    }

    public void setX(int _x) {
        this._x = _x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int _y) {
        this._y = _y;
    }
    
    public int getSeed() {
        return _seed;
    }

    public void setSeed(int seed) {
        this._seed = seed;
    }
}
