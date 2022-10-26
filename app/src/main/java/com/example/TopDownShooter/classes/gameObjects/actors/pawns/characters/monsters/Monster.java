package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Monster is a Character that can not use pistols and guns.
 * Instead, Monsters have their own special attack.
 */
public abstract class Monster extends Character {
    public Monster(Game myGame, Position initPosition, int resourceId) {
        super(myGame, initPosition, resourceId);
    }
}
