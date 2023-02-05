package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.events.physicalEvents.OnPhysicalUpdate;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;

import org.jbox2d.collision.Collision;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;


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

    public void applyForce(Vector force){
        if(body == null){return;}
        body.applyForce(new Vec2(force.getCoordinateX(), -force.getCoordinateY()), body.getWorldCenter());
    }
    public void applyVelocity(Vector vector){
        if(body == null){return;}
        body.setLinearVelocity(new Vec2(vector.getCoordinateX(), -vector.getCoordinateY()));
    }

    public void applyRotation(float rotation){
        if(body == null){return;}
        body.setTransform(body.getPosition(), -rotation);
    }






}
