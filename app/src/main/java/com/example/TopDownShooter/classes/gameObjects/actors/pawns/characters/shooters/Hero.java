package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Hero is ShooterCharacter that is controlled by a user and fight enemies
 * (or other Heroes on a multiplayer game).
 */
public class Hero extends Shooter {

    private static final float MAX_SPEED = 60;

    public Hero(Game myGame, Position initPosition){
        super(myGame, initPosition, new BitmapLoader(R.drawable.womangreen_gun, myGame.getResources()), new BitmapLoader(R.drawable.womangreen_stand, myGame.getResources()));

        this.health = 100;


    }



    @Override
    public float getSpeed() {
        return MAX_SPEED;
    }

    @Override
    public Position getBulletSpawnPosition() {

        float x = (float) Math.cos(direction) * 40;
        float y = (float) Math.sin(direction) * 40;
        return new Position(viewPosition().getX() + x, viewPosition().getY() + y);
    }
}
