package com.example.TopDownShooter.classes.events.actorValidationEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;

/**
 * OnActorValid event is triggered when a new actor gets valid and joins the game.
 * The event should be triggered by the actor itself by getting the observable from the game class
 */
public class OnActorValid <T extends GameObject> extends OnEvent {

    private final T validActor;

    public T getValidActor() {
        return validActor;
    }

    public OnActorValid(Game myGame, T validActor){
        super(myGame);
        this.validActor = validActor;
    }


}
