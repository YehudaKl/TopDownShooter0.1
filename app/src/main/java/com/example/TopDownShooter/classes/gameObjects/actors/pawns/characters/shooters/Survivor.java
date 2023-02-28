package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.gameObjects.guns.Pistol;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Survivor is a weak shooter that uses a pistol
 */
public class Survivor extends Shooter {
    private static final int MAX_SPEED = 60;
    private static final int MAX_HEALTH = 100;


    public Survivor(Game myGame, Position initPosition){
        super(myGame, initPosition, MAX_HEALTH, new BitmapLoader(R.drawable.womangreen_gun, myGame.getResources()), new BitmapLoader(R.drawable.womangreen_stand, myGame.getResources()), new Pistol(myGame));
    }


    @Override
    public int getSpeed() {
        return MAX_SPEED;
    }

    @Override
    public Position getBulletSpawnPosition() {

        float x = (float) Math.cos(viewDirection()) * 80;
        float y = (float) Math.sin(viewDirection()) * 80;

        return new Position(viewPosition().getX() + x, viewPosition().getY() + y);
    }
}
