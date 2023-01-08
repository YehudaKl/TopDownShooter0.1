package com.example.TopDownShooter.classes.systems.repository;


import com.example.TopDownShooter.classes.gameObjects.actors.Actor;

/**
 * Interface for specifying a method for every class that has to sort actors in some way
 */
public interface ActorQualifier<T extends Actor> {

    boolean isQualified(T actor);
}
