package com.example.TopDownShooter.classes.events.GameStatusEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.enums.GameState;

/**
 * A parent class for all event about the game's status such as onGameStart onGamePaused ect..
 */
public class OnGameStateChanged extends OnEvent {

    private final GameState newState;

    public OnGameStateChanged(Game game, GameState state){
        super(game);

        this.newState = state;
    }

    public GameState getNewState(){
        return newState;
    }
}
