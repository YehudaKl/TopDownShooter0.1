package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import com.example.TopDownShooter.R;
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



    public Hero(Game myGame, Position initPosition){
        super(myGame, initPosition, R.drawable.womangreen_stand);

        this.health = 100;


    }





}
