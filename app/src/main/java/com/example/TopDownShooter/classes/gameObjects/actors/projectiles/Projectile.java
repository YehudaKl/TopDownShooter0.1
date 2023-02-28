package com.example.TopDownShooter.classes.gameObjects.actors.projectiles;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A projectile is an actor that moves by a set of physical rules and used for hitting other characters.
 * Each projectile has a source pawn which points out who is responsible for the shooting of the projectile.
 *
 */
public abstract class Projectile extends Actor{



    private final Character  sourceCharacter;

    public Character getSourceCharacter() {
        return sourceCharacter;
    }

    public Projectile(Game myGame, Character sourceCharacter){
        super(myGame);
        this.sourceCharacter = sourceCharacter;
    }





}
