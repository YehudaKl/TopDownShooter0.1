package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.assets.Asset;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Hero is ShooterCharacter that is controlled by a user and fight enemies
 * (or other Heroes on a multiplayer game).
 */
public class Hero extends Shooter {

    private static final float MAX_SPEED = 200;

    public Hero(Game myGame, Position initPosition){
        super(myGame, initPosition, new Asset(R.drawable.womangreen_stand, myGame.getResources()));

        this.health = 100;


    }

    @Override
    public float getSpeed() {
        return MAX_SPEED;
    }
}
