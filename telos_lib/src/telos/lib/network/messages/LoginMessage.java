/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Alyssa Kozma
 */
@Serializable
public class LoginMessage extends AbstractMessage {
    private String _username;
    private String _password;
    
    public LoginMessage() {}
    public LoginMessage(String username, String password) {
        _username = username;
        _password = password;
    }
    public String getUsername() { return _username; }
    public String getPassword() { return _password; }
    public void setUsername(String newUsername) { _username = newUsername; }
    public void setPassword(String newPassword) { _password = newPassword; }
}
