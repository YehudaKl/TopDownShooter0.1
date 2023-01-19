package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.events.physicalEvents.OnPhysicalUpdate;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;


/**
 * The physical body is responsible to "tell" its owner actor its position in the world according
 * to physical rules.
 * The physical body manages all the interaction with the physical engine
 */

public class PhysicalBody extends GameObject {

    private final Actor myActor;
    private final Body body;
    private PhysicsListener physicsListener;


    public Actor getActor(){
        return myActor;
    }

    public void setPhysicsListener(PhysicsListener physicsListener){
        this.physicsListener = physicsListener;
    }

    public PhysicalBody(Game myGame, Actor myActor, PhysicalSpecification physicalSpecification) {
        super(myGame);

        this.myActor = myActor;

        // Creating the body using the body and the fixture def
        World world = myGame.getPhysicsManager().getWorld();
        this.body = world.createBody(physicalSpecification.getBodyDef());
        body.createFixture(physicalSpecification.getFixtureDef());

        // Subscribes to the PhysicalUpdate event
        subscribeToObservable(myGame.getPhysicsManager().getOnPhysicalUpdateObservable().subscribe(this::onPhysicalUpdate));

    }

    public void onPhysicalUpdate(OnPhysicalUpdate onPhysicalUpdate){
        if(physicsListener == null){return;}

        physicsListener.onPositionChanged(new Position(body.getPosition().x, body.getPosition().y));
    }





}
