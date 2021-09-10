/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core.unit;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import telos.lib.core.ResourceTypes;
import telos.lib.core.WorldEntity;
import telos.lib.core.player.PlayerFactions;
import telos.lib.network.messages.unit.CreateUnitMessage;

/**
 *
 * @author Beefaroni
 */
public class Unit extends WorldEntity {
    private int _id;
    private String _name;
    private int _hp;
    private int _maxHp;
    private String _owner;
    private Vector3f _travelDestination;
    private Spatial _model;
    private CharacterControl _controller;
    private CapsuleCollisionShape _shape;
    private NavMeshPathfinder _nav;
    private Map<ResourceTypes, Integer> _cost;
    private int _trainingTime;
    private UnitTypes _type;
    private int _minDmg;
    private int _maxDmg;
    private boolean _canBuild;
    private int _attackRange;
    private PlayerFactions _faction;
    private int _chunkX;
    private int _chunkY;
    
    public Unit() { super(); }
    public Unit(String name, int hp) {
        _shape = new CapsuleCollisionShape(1.5f, 6f, 1);
        this.setName(name);
        _hp = hp;
        _controller = new CharacterControl(_shape, 1f);
        _controller.setEnabled(true);
        _controller.setUp(new Vector3f(0, 1f, 0));
        _controller.setGravity(new Vector3f(0.0f, -9.8f, 0.0f));
        _controller.setApplyPhysicsLocal(true);
        _controller.setFallSpeed(15f);
        _controller.setLinearVelocity(new Vector3f(0, 1, 0));
    }
    
    //for loading from template
    public Unit(Unit u) {
        _name = u.getName();
        _hp = u.getMaxHp();
        _maxHp = u.getMaxHp();
        _cost = u.getCost();
        _trainingTime = u.getTrainingTime();
        _type = u.getType();
        _minDmg = u.getMinDmg();
        _maxDmg = u.getMaxDmg();
        _canBuild = u.canBuild();
        _attackRange = u.getAttackRange();
        _faction = u.getFaction();
    }
    
    public Unit(CreateUnitMessage m) {
        _name = m.getName();
        _id = m.getDbId();
        _hp = m.getHp();
        _maxHp = m.getMaxHp();
        _type = UnitTypes.valueOf(m.getType());
        _owner = m.getOwner();
        setLoc(m.getLoc());
        _chunkX = m.getChunkX();
        _chunkY = m.getChunkY();
    }
    
    public void spawn() {
        _shape = new CapsuleCollisionShape(1.5f, 6f, 1);
        _controller = new CharacterControl(_shape, 1f);
        _controller.setEnabled(true);
        _controller.setUp(new Vector3f(0, 1f, 0));
        _controller.setGravity(new Vector3f(0.0f, -9.8f, 0.0f));
        _controller.setApplyPhysicsLocal(true);
        _controller.setFallSpeed(15f);
        _controller.setLinearVelocity(new Vector3f(0, 1, 0));
    }
    
    @Override
    public String toString() {
        String s = super.toString();
        String inf = s + "\nUnit {";
        inf += "\n\tid: " + this.getId();
        inf += "\n\tname: " + this.getName();
        inf += "\n\thp: " + this.getHp();
        inf += "\n\tmaxHp: " + this.getMaxHp();
        inf += "\n\towner: " + this.getOwner();
        inf += "\n\ttrainingTime: " + this.getTrainingTime();
        inf += "\n\ttype: " + String.valueOf(this.getType());
        inf += "\n\tminDmg: " + this.getMinDmg();
        inf += "\n\tmaxDmg: " + this.getMaxDmg();
        inf += "\n\tattackRange: " + this.getAttackRange();
        inf += "\n\tfaction: " + String.valueOf(this.getFaction());
        inf += "\n\tchunk: (" + this.getChunkX() + "," + this.getChunkY() + ")";
        inf += "\n}";
        return inf;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getChunkX() {
        return _chunkX;
    }

    public void setChunkX(int _chunkX) {
        this._chunkX = _chunkX;
    }

    public int getChunkY() {
        return _chunkY;
    }

    public void setChunkY(int _chunkY) {
        this._chunkY = _chunkY;
    }

    public PlayerFactions getFaction() {
        return _faction;
    }

    public void setFaction(PlayerFactions _faction) {
        this._faction = _faction;
    }
    
    public int getMaxHp() {
        return _maxHp;
    }

    public void setMaxHp(int _maxHp) {
        this._maxHp = _maxHp;
    }

    public int getAttackRange() {
        return _attackRange;
    }

    public void setAttackRange(int _attackRange) {
        this._attackRange = _attackRange;
    }

    public int getMinDmg() {
        return _minDmg;
    }

    public void setMinDmg(int _minDmg) {
        this._minDmg = _minDmg;
    }

    public int getMaxDmg() {
        return _maxDmg;
    }

    public void setMaxDmg(int _maxDmg) {
        this._maxDmg = _maxDmg;
    }

    public boolean canBuild() {
        return _canBuild;
    }

    public void setCanBuild(boolean _canBuild) {
        this._canBuild = _canBuild;
    }

    public UnitTypes getType() {
        return _type;
    }

    public void setType(UnitTypes _type) {
        this._type = _type;
    }

    public CapsuleCollisionShape getShape() {
        return _shape;
    }

    public void setShape(CapsuleCollisionShape _shape) {
        this._shape = _shape;
    }

    public NavMeshPathfinder getNav() {
        return _nav;
    }

    public void setNav(NavMeshPathfinder _nav) {
        this._nav = _nav;
    }

    public Map<ResourceTypes, Integer> getCost() {
        return _cost;
    }

    public void setCost(Map<ResourceTypes, Integer> _cost) {
        this._cost = _cost;
    }

    public int getTrainingTime() {
        return _trainingTime;
    }

    public void setTrainingTime(int _trainingTime) {
        this._trainingTime = _trainingTime;
    }
    
    public void updateNav(NavMesh m) {
        _nav = new NavMeshPathfinder(m);
    }

    public Vector3f getTravelDestination() {
        return _travelDestination;
    }

    public void setTravelDestination(Vector3f _travelDestination) {
        _nav.setPosition(getLoc());
        this._travelDestination = _travelDestination;
        _nav.computePath(_travelDestination);
        //this.setLocalTranslation(_travelDestination); // TODO: walk there instead of teleporting
        //this.setLoc(_travelDestination);
    }

    public Spatial getModel() {
        return _model;
    }

    public void setModel(Spatial _model) {
        this._model = _model;
        this._model.addControl(_controller);
        this.attachChild(_model);
    }

    public CharacterControl getController() {
        return _controller;
    }
    
    @Override
    public void setName(String name) { 
        super.setName(name);
        _name = name; 
    }
    public void setHp(int hp) { _hp = hp; }
    public void setOwner(String owner) { _owner = owner; }
    public String getOwner() { return _owner; }
    @Override
    public String getName() {
        if (super.getName() == null)
            return _name;
        return super.getName();
        //return _name.equals(super.getName()) ? _name : super.getName();
    }
    public int getHp() { return _hp; }
    
    public void update(float tpf) {
        //nav
        Waypoint wp = _nav.getNextWaypoint();
        if (wp == null)
            return ;
        Vector3f vec = wp.getPosition().subtract(getLocalTranslation());
        if (!(vec.length()<1)) {
            //move the spatial to the location while its not there
            move(vec.normalize().multLocal(tpf));
            //System.out.println(_nav.getPath().getWaypoints().indexOf(_nav.getNextWaypoint()) + "/" +  _nav.getPath().getWaypoints().size());
            //System.out.println("updated");
        }
        else {
            //System.out.println(_nav.getPath().getWaypoints().indexOf(_nav.getNextWaypoint()) + "/" +  _nav.getPath().getWaypoints().size());
            if (_nav.getPath().getWaypoints().size() > _nav.getPath().getWaypoints().indexOf(_nav.getNextWaypoint()) + 1) {
                _nav.goToNextWaypoint();
            }
        }
        setLoc(getLocalTranslation());
        _nav.setPosition(getLocalTranslation());
        //Vector3f wayPoint = 
    }
}
