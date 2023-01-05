package com.example.TopDownShooter.classes.events.GameLoopEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

/**
 * The pre update event is triggered by the game before each update in order to update its update trace before the update
 * Any UI that has an effect on the game controls (for example joystick). and has a reserved value in the updateTrace class!
 * must subscribe
 */
public class OnPreUpdate extends OnEvent {

    private final UpdateTrace updateTrace;

    public UpdateTrace getUpdateTrace() {
        return updateTrace;
    }

    public OnPreUpdate(Game game, UpdateTrace updateTrace) {
        super(game);
        this.updateTrace = updateTrace;
    }

}
