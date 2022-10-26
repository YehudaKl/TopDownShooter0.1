package com.example.TopDownShooter.classes.gameObjects.actors;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Hero is ShooterCharacter that is controlled by a user and fight enemies
 * (or other Heroes on a multiplayer game).
 */

// TODO extend from this class more types of heroes like soldier, general, agent ext..
public class Hero extends Shooter {



    public Hero(Game myGame, Position initPosition){
        super(myGame, initPosition, R.drawable.womangreen_stand);

        this.health = 100;


    }

    @Override
    public void onGameStart() {
        super.onGameStart();
    }

    @Override
    public void onGamePause() {
        super.onGameStart();
    }

    @Override
    public void onGameQuit() {

    }



}
