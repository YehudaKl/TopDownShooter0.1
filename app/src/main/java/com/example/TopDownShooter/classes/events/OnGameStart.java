package com.example.TopDownShooter.classes.events;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

/**
 * The OnGameStartEvent is triggered after the game was successfully loaded.
 * This event is an indicator for any gameObject that all other gameObjects, systems and other components have been
 * initialized.
 * Calculations and other initialisations that relay on other gameObjects must be performed in this event(like finding a team)
 */
public class OnGameStart extends OnEvent {

    public OnGameStart(Game game){
        super(game);
    }
}
