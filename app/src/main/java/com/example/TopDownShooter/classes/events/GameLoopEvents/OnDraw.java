package com.example.TopDownShooter.classes.events.GameLoopEvents;

import android.graphics.Canvas;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

/**
 * The DrawEvent is triggered by the game each draw. Every actor that needs to be drawn to the screen each frame must listen
 */
public class OnDraw extends OnEvent {

    protected final Canvas canvas;

    public OnDraw(Game game, Canvas canvas){
        super(game);

        this.canvas = canvas;

    }

    public Canvas getCanvas(){
        return canvas;
    }

}
