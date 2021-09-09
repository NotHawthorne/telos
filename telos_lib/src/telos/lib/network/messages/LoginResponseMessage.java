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
public class LoginResponseMessage extends AbstractMessage {
    private boolean _result;
    private int _chunk_x;
    private int _chunk_y;

    public LoginResponseMessage(boolean result, int chunk_x, int chunk_y) {
        _result = result;
        _chunk_x = chunk_x;
        _chunk_y = chunk_y;
    }
    
    public LoginResponseMessage() { }
    
    public boolean getResult() {
        return _result;
    }

    public void setResult(boolean _result) {
        this._result = _result;
    }

    public int getChunk_x() {
        return _chunk_x;
    }

    public void setChunk_x(int _chunk_x) {
        this._chunk_x = _chunk_x;
    }

    public int getChunk_y() {
        return _chunk_y;
    }

    public void setChunk_y(int _chunk_y) {
        this._chunk_y = _chunk_y;
    }
    
    public Vector2f getChunkCoords() {
        return new Vector2f(_chunk_x, _chunk_y);
    }
}
