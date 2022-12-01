package com.example.TopDownShooter.classes.events.GameStatusEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

/**
 * A parent class for all event about the game's status such as onGameStart onGamePaused ect..
 */
public abstract class OnGameStatusChanged extends OnEvent {

    public OnGameStatusChanged(Game game){
        super(game);
    }
}
