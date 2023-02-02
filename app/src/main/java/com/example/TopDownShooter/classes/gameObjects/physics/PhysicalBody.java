package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.events.physicalEvents.OnPhysicalUpdate;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;


/**
 * The physical body is responsible to notify a physicalListener
 * about the physical state of its owner actor
 * The physical body manages all interaction with the physical engine
 */

public class PhysicalBody extends GameObject {

    private final Actor owner;
    private final Body body;


    public Actor getActor(){
        return owner;
    }

    public Position getPosition(){
        return new Position(body.getPosition().x, body.getPosition().y);
    }

    public PhysicalBody(Game myGame, Actor owner, PhysicalSpecification physicalSpecification) {
        super(myGame);

        this.owner = owner;

        // Creating the body using the body and the fixture def
        this.body = myGame.getPhysicsManager().createBody(physicalSpecification.getBodyDef());
        body.createFixture(physicalSpecification.getFixtureDef());


    }





}
