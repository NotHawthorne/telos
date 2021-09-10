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
public interface StructureTypes {
    public enum SpaceMarines implements StructureTypes {
        COMMAND_CENTER,
        FACTORY,
        EXTRACTOR,
        TURRET,
    }
    public enum SpaceOrcs implements StructureTypes {
        KEEP,
        WARRENS,
        MINE,
    }
    public enum SpaceElves implements StructureTypes {
        TEMPLE,
        TRAINING_GROUNDS,
        HARVEST_CIRCLE,
    }
}
