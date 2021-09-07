/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import telos.lib.core.Unit;

/**
 *
 * @author Beefaroni
 */
public class UnitLedger {
    private static Map<String, Unit> _units = new HashMap<>();
    public static void addUnit(String id, Unit u) {
        _units.put(id, u);
    }
    public static Unit getUnit(String id) { return _units.get(id); }
}
