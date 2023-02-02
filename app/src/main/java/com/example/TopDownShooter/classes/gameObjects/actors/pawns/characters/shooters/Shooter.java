package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import android.widget.Toast;

import com.example.TopDownShooter.classes.assets.Asset;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Shooter is a character that can use pistols and guns as weapons
 */
public abstract class Shooter extends Character {
    public Shooter(Game myGame, Position initPosition, Asset asset) {
        super(myGame, initPosition, asset);
    }

    // Method for shooting, must be used only by the owner!
    public void shoot(){
        Toast.makeText(myGame.getContext(), "Shoot !", Toast.LENGTH_SHORT).show();
    }

}
