package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;

/**
 * The physical body is responsible to "tell" its owner actor its position in the world according
 * to physical rules.
 * The physical body manages all the interaction with the physical engine
 */

public abstract class PhysicalBody extends GameObject {

    private final Actor myActor;
    private PhysicsListener physicsListener;

    public Actor getActor(){
        return myActor;
    }

    public void setPhysicsListener(PhysicsListener physicsListener){
        this.physicsListener = physicsListener;
    }

    public PhysicalBody(Game myGame, Actor myActor) {
        super(myGame);

        this.myActor = myActor;
        subscribeToGameObservable(myGame.getOnUpdateObservable().subscribe(this::onUpdate));
    }

    public void onUpdate(OnUpdate onUpdate){
        // Ask the box2d world for my position and inform the physicsListener
    }

    public abstract Position getPosition();
    public abstract float getRotation();
    protected abstract void onColliding();



}
