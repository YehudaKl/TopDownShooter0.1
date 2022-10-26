package com.example.TopDownShooter.classes.systems.Effects;

import android.graphics.Canvas;

/**
 * Generic class for all effects
 */
public abstract class Effect {

    protected EffectState state;

    public Effect(){
        this.state = EffectState.ON_DRAW;
    }

    // A place to implement the effect
    public abstract void drawEffect(Canvas canvas);

    public void endEffect(){
        this.state = EffectState.DONE;
    }

    public EffectState getState() {
        return state;
    }
}
