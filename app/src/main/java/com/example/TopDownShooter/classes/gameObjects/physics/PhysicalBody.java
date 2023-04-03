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

import shiffman.box2d.Box2DProcessing;


/**
 * The physical body is responsible to notify a physicalListener
 * about the physical state of its owner actor
 * The physical body manages all interaction with the physical engine
 */

public class PhysicalBody extends GameObject {

    private final Actor owner;
    private final Body body;
    private CollisionListener collisionListener;


    public Actor getActor(){
        return owner;
    }


    public CollisionListener getCollisionListener() {
        return collisionListener;
    }

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    public PhysicalBody(Game myGame, Actor owner, Position initPosition,  PhysicalSpecification physicalSpecification) {
        super(myGame);

        this.owner = owner;
        Box2DProcessing physicalManager = myGame.getPhysicsManager();

        // Creating the body using the body and the fixture def
        this.body = physicalManager.createBody(physicalSpecification.getBodyDef(initPosition, physicalManager));
        body.createFixture(physicalSpecification.getFixtureDef(physicalManager));

        // Setting the body's user data to this
        body.setUserData(this);

        // Subscribing for collision events
        subscribeToObservable(myGame.getObservableProvider().getOnCollisionStartObservable().subscribe(this::onCollisionStart));
        subscribeToObservable(myGame.getObservableProvider().getOnCollisionEndObservable().subscribe(this::onCollisionEnd));

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

    public void setPosition(Position position){
        body.setTransform(owner.getMyGame().getPhysicsManager().coordPixelsToWorld(position.getX(), position.getY()) , body.getAngle());
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
         PhysicalBody bodyA = onCollisionStart.getBodyA();
         PhysicalBody bodyB = onCollisionStart.getBodyB();

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
        PhysicalBody bodyA = onCollisionEnd.getBodyA();
        PhysicalBody bodyB = onCollisionEnd.getBodyB();

        if(bodyA == this){
            collisionListener.onCollisionStart(bodyB.owner);
        }
        else if(bodyB == this){
            collisionListener.onCollisionStart(bodyA.owner);
        }
    }





}
