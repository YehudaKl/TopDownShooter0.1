package com.example.TopDownShooter.classes.events.GameLoopEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

/**
 * The DrawEvent is triggered by the game each update. Every actor that needs to be updated each frame must listen
 */
public class OnUpdate extends OnEvent {

    protected final float deltaTime;

    public OnUpdate(Game game, float deltaTime){
        super(game);

        this.deltaTime = deltaTime;
    }

    public float getDeltaTime(){
        return this.deltaTime;
    }
}
