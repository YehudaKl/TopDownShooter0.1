package com.example.TopDownShooter.classes.events;

import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStatusChanged;
import com.example.TopDownShooter.classes.games.Game;

public class OnGameEnd extends OnGameStatusChanged {

    //TODO add needed information about the event

    public OnGameEnd(Game game) {
        super(game);
    }
}
