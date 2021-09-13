/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import telos.lib.core.player.Player;
import telos.lib.core.unit.Unit;
import telos.lib.core.unit.UnitTypes;
import telos.lib.network.messages.unit.CreateUnitMessage;

/**
 *
 * @author Alyssa Kozma
 */
public class UnitLedger {
    private static Map<Integer, Unit> _units = new HashMap<>();
    private static Map<UnitTypes, Unit> _unitInfo = new HashMap<>();
    public static void addUnit(Unit u) {
        _units.put(u.getId(), u);
        Player owner = UserLedger.getPlayer(u.getOwner());
        owner.setOwnedUnits(owner.getOwnedUnits() + 1);
        for (Player p : GameServer.getChunk(new Vector2f(u.getChunkX(), u.getChunkY())).getPlayers().values()) {
            UserLedger.getConn(p.getUsername()).send(new CreateUnitMessage(u));
        }
    }

    // u = from unitInfo
    public static Unit createUnit(Unit u, Player p, Chunk c, Vector3f loc) {
        Unit ret = new Unit(u);
        ret.setOwner(p.getUsername());
        ret.setChunkX((int)c.getCoords().x);
        ret.setChunkY((int)c.getCoords().y);
        ret.setUUID(UUID.randomUUID().toString());
        ret.setLoc(loc);
        ret.setId(Scribe.saveUnit(ret));
        addUnit(ret);
        c.addUnit(ret);
        return ret;
    }
    public static void init() {
        _unitInfo = Scribe.loadUnitInfo();
        _units = Scribe.loadStoredUnits(_unitInfo);
    }
    public static Unit getUnitInfo(UnitTypes type) {
        return _unitInfo.get(type);
    }
    public static Unit getUnit(Integer id) {
        return _units.get(id);
    }
}
