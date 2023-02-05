package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.events.physicalEvents.OnPhysicalUpdate;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;


/**
 * The physical body is responsible to notify a physicalListener
 * about the physical state of its owner actor
 * The physical body manages all interaction with the physical engine
 */

public class PhysicalBody <T extends Actor> extends GameObject {

    private final T owner;
    private final Body body;


    public Actor getActor(){
        return owner;
    }

    public Position getPosition(){
        // Returning new position in pixel coordinates
        Vec2 pixelPosition = owner.getMyGame().getPhysicsManager().getBodyPixelCoord(body);

        return new Position(pixelPosition.x, pixelPosition.y);
    }

    public float getAngle(){
        // Return the right angle for screen axis system, which means negative angle of the physical angle
        return body.getAngle() * -1;
    }

    public PhysicalBody(Game myGame, T owner, PhysicalSpecification<T> physicalSpecification) {
        super(myGame);

        this.owner = owner;

        // Creating the body using the body and the fixture def
        this.body = myGame.getPhysicsManager().createBody(physicalSpecification.getBodyDef(owner));
        body.createFixture(physicalSpecification.getFixtureDef(owner));


    }





}
