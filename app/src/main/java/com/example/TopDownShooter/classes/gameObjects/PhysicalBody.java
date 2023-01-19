package com.example.TopDownShooter.classes.gameObjects;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * The physical body is responsible to "tell" its owner actor its position in the world according
 * to physical rules.
 * The physical body manages all the interaction with the physical engine
 */

public abstract class PhysicalBody extends GameObject {

    private final Actor myActor;


    public PhysicalBody(Game myGame, Actor myActor) {
        super(myGame);

        this.myActor = myActor;
    }

    public abstract Position getPosition();
    public abstract float getRotation();
    protected abstract void onCollision();



}
