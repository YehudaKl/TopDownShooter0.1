package com.example.TopDownShooter.classes.gameObjects.ActorProperties;

import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * ActorProperty is an actor that is attached to other actor and belongs to it.
 * The Properties of the actor "servers" the actor in some way.
 * For a character might has shield, hat and pistol as its properties.
 * Properties are not visible and it is up to their owner to update his drawable according to them.
 * Each actor has its own properties in a properties ArrayList
 */

public abstract class ActorProperty extends GameObject {

    public ActorProperty(Game myGame, Actor owner){
        super();


    }
}
