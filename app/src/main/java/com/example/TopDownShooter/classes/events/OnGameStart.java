package com.example.TopDownShooter.classes.events;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

/**
 * The OnGameStartEvent is triggered when the game starts
 */
public class OnGameStart extends OnEvent {

    //TODO add needed information about the event

    public OnGameStart(Game game){
        super(game);
    }
}
