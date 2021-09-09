/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

import telos.lib.core.player.PlayerFactions;

/**
 *
 * @author Alyssa Kozma
 */

public class Player {
    private String _username;
    private int _password;
    private int _credits;
    private int _iron;
    private int _lumber;
    private int _oil;
    private int _crystals;
    private PlayerFactions _faction;

    public int getPassword() {
        return _password;
    }

    public void setPassword(int _password) {
        this._password = _password;
    }

    public PlayerFactions getFaction() {
        return _faction;
    }

    public void setFaction(PlayerFactions _faction) {
        this._faction = _faction;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public void setCredits(int _credits) {
        this._credits = _credits;
    }

    public void setIron(int _iron) {
        this._iron = _iron;
    }

    public void setLumber(int _lumber) {
        this._lumber = _lumber;
    }

    public void setOil(int _oil) {
        this._oil = _oil;
    }

    public void setCrystals(int _crystals) {
        this._crystals = _crystals;
    }

    public String getUsername() {
        return _username;
    }

    public int getCredits() {
        return _credits;
    }

    public int getIron() {
        return _iron;
    }

    public int getLumber() {
        return _lumber;
    }

    public int getOil() {
        return _oil;
    }

    public int getCrystals() {
        return _crystals;
    }
    
}
