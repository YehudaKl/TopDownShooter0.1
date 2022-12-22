package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Shooter is a character that can use pistols and guns as weapons
 */
public abstract class Shooter extends Character {
    public Shooter(Game myGame, Position initPosition, int resourceId) {
        super(myGame, initPosition, resourceId);
    }
}
