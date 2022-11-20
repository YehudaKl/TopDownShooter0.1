package com.example.TopDownShooter.classes.gameObjects.actors.projectiles;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;

/**
 * A projectile is an actor that moves by a set of physical rules and used for hitting other characters.
 * Each projectile has a source pawn which points out who is responsible for the shooting of the projectile.
 *
 */
public abstract class Projectile extends Actor {



    private Character sourceCharacter;
    private Vector velocity;
    private float speed;



    public Projectile(Game myGame, Position initPosition, int resourceId){
        super(myGame, initPosition, resourceId);

        this.speed = 40;//conf

    }

    @Override
    public void update(){
        super.update();
        updateVelocity();

        double x = position.getX() + velocity.getCoordinateX();
        double y = position.getY() + velocity.getCoordinateY();

        position.setX(x);
        position.setY(y);
    }

    protected abstract void updateVelocity();


}
