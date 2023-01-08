package com.example.TopDownShooter.classes.events;

import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStatusChanged;
import com.example.TopDownShooter.classes.games.Game;

public class OnGameEnd extends OnEvent {

    public OnGameEnd(Game game) {
        super(game);
    }
}
