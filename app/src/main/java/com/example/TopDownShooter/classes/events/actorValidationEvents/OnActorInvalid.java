package com.example.TopDownShooter.classes.events.actorValidationEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;

/**
 * OnActorInvalid event is triggered when an actor gets invalid and quits the game.
 * The event should be triggered by the actor itself by getting the observable from the game class
 */
public class OnActorInvalid <T extends GameObject> extends OnEvent {

    private final T invalidActor;

    public T getInvalidActor() {
        return invalidActor;
    }

    public OnActorInvalid(Game myGame, T invalidActor){
        super(myGame);
        this.invalidActor = invalidActor;
    }


}