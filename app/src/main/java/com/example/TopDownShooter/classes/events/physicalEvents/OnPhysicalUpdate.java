package com.example.TopDownShooter.classes.events.physicalEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

import javax.sql.ConnectionEvent;

/**
 * The OnPhysicalUpdate event is triggered when the physical world is updated by the physicalManager
 * Note: the physicalUpdate occurs before the regular update so objects will be able to get their
 * updated positions each update
 */
public class OnPhysicalUpdate extends OnEvent {

    public OnPhysicalUpdate(Game game) {
        super(game);
    }
}
