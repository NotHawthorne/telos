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
import telos.lib.core.Structure;
import telos.lib.core.StructureTypes;
import telos.lib.core.player.Player;
import telos.lib.network.messages.CreateStructureMessage;
import telos.lib.network.messages.unit.CreateUnitMessage;

/**
 *
 * @author Alyssa Kozma
 */
public class StructureLedger {
    private static Map<Integer, Structure> _structures = new HashMap<>();
    private static Map<StructureTypes, Structure> _structureInfo = new HashMap<>();
    public static void addStructure(Structure s) {
        _structures.put(s.getDbId(), s);
        Player owner = UserLedger.getPlayer(s.getOwner());
        owner.setOwnedUnits(owner.getOwnedUnits() + 1);
        for (Player p : GameServer.getChunk(new Vector2f(s.getChunkX(), s.getChunkY())).getPlayers().values()) {
            UserLedger.getConn(p.getUsername()).send(new CreateStructureMessage(s));
        }
    }

    // u = from unitInfo
    public static Structure createStructure(Structure s, Player p, Chunk c, Vector3f loc) {
        Structure ret = new Structure(s);
        ret.setOwner(p.getUsername());
        ret.setChunkX((int)c.getCoords().x);
        ret.setChunkY((int)c.getCoords().y);
        ret.setId(UUID.randomUUID().toString());
        ret.setLoc(loc);
        ret.setDbId(Scribe.saveStructure(ret));
        addStructure(ret);
        c.addStructure(ret);
        return ret;
    }
    public static void init() {
        _structureInfo = Scribe.loadStructureInfo();
        _structures = Scribe.loadStoredStructures(_structureInfo);
    }
    public static Structure getStructureInfo(StructureTypes type) {
        return _structureInfo.get(type);
    }
    public static Structure getStructure(Integer id) {
        return _structures.get(id);
    }
}
