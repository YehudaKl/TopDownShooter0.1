package com.example.TopDownShooter.classes.gameObjects.actors.projectiles;

import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Bullet is a projectile that guns shoot
 */
public abstract class Bullet extends Projectile{

    public Bullet(Game myGame, Position initPosition, int resourceID){
        super(myGame, initPosition, resourceID);



    }



}
