/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telos.lib.core;

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

/**
 *
 * @author Beefaroni
 */
public class Unit extends WorldEntity {
    private String _name;
    private int _hp;
    private String _owner;
    private Vector3f _travelDestination;
    private Spatial _model;
    private CharacterControl _controller;
    private CapsuleCollisionShape _shape;
    private NavMeshPathfinder _nav;
    
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
        return _name.equals(super.getName()) ? _name : super.getName();
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
            System.out.println(_nav.getPath().getWaypoints().indexOf(_nav.getNextWaypoint()) + "/" +  _nav.getPath().getWaypoints().size());
            if (_nav.getPath().getWaypoints().size() > _nav.getPath().getWaypoints().indexOf(_nav.getNextWaypoint()) + 1) {
                _nav.goToNextWaypoint();
            }
        }
        setLoc(getLocalTranslation());
        _nav.setPosition(getLocalTranslation());
        //Vector3f wayPoint = 
    }
}
