package com.example.TopDownShooter.classes.events.GameStatusEvents;

import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStatusChanged;
import com.example.TopDownShooter.classes.games.Game;

public class OnGamePaused extends OnGameStatusChanged {

    //TODO add needed information about the event

    public OnGamePaused(Game game) {
        super(game);
    }
}
