package com.example.TopDownShooter.classes.events.GameStatusEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

/**
 * A parent class for all event about the game's status such as onGameStart onGamePaused ect..
 */
public class OnGameStatusChanged extends OnEvent {

    private final GameStatus newStatus;

    public OnGameStatusChanged(Game game, GameStatus newStatus){
        super(game);

        this.newStatus = newStatus;
    }
}
