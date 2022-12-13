package com.example.TopDownShooter.classes.events.actorValidationEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;

/**
 * OnActorValid event is triggered when a new actor gets valid and joins the game.
 * The event should be triggered by the actor itself by getting the observable from the game class
 */
public class OnActorValid extends OnEvent {

    private final Actor validActor;

    public Actor getValidActor() {
        return validActor;
    }

    public OnActorValid(Game myGame, Actor validActor){
        super(myGame);
        this.validActor = validActor;
    }


}
