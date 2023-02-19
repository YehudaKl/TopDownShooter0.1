package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.events.physicalEvents.OnCollisionEnd;
import com.example.TopDownShooter.classes.events.physicalEvents.OnCollisionStart;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;

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
    private CollisionListener collisionListener;


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

    public CollisionListener getCollisionListener() {
        return collisionListener;
    }

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    public PhysicalBody(Game myGame, T owner, PhysicalSpecification<T> physicalSpecification) {
        super(myGame);

        this.owner = owner;

        // Creating the body using the body and the fixture def
        this.body = myGame.getPhysicsManager().createBody(physicalSpecification.getBodyDef(owner));
        body.createFixture(physicalSpecification.getFixtureDef(owner));

        // Setting the body's user data to this
        body.setUserData(this);

        // Subscribing for collision events
        subscribeToObservable(myGame.getOnCollisionStartObservable().subscribe(this::onCollisionStart));
        subscribeToObservable(myGame.getOnCollisionEndObservable().subscribe(this::onCollisionEnd));

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

    public Vector getVelocity(){
        return new Vector(body.getLinearVelocity().x, body.getLinearVelocity().y);
    }

    public void onCollisionStart(OnCollisionStart onCollisionStart){
        if(collisionListener == null){return;}

        // Checking if one on the involved physical bodies is this
         PhysicalBody<?> bodyA = onCollisionStart.getBodyA();
         PhysicalBody<?> bodyB = onCollisionStart.getBodyB();

         if(bodyA == null || bodyB == null){return;}

         if(bodyA == this){
             collisionListener.onCollisionStart(bodyB.owner);
         }
         else if(bodyB == this){
             collisionListener.onCollisionStart(bodyA.owner);
         }
    }

    public void onCollisionEnd(OnCollisionEnd onCollisionEnd){
        if(collisionListener == null){return;}
        // Checking if one on the involved physical bodies is this
        PhysicalBody<?> bodyA = onCollisionEnd.getBodyA();
        PhysicalBody<?> bodyB = onCollisionEnd.getBodyB();

        if(bodyA == this){
            collisionListener.onCollisionStart(bodyB.owner);
        }
        else if(bodyB == this){
            collisionListener.onCollisionStart(bodyA.owner);
        }
    }





}
