package com.example.TopDownShooter.classes.events;


import com.example.TopDownShooter.classes.games.Game;

/**
 * An abstract parent for all events to be generated in the game by the EventBus engine
 */
public abstract class OnEvent {

    protected final Game game;

    public OnEvent(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

}
