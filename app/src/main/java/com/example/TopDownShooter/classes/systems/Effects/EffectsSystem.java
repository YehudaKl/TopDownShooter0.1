package com.example.TopDownShooter.classes.systems.Effects;

import android.graphics.Canvas;
import com.example.TopDownShooter.classes.systems.Effects.Effect;

import com.example.TopDownShooter.classes.games.Game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * EffectsSystem handles the effects in the game. Each game has an EffectsSystem and the game objects should
 * use the system in order to ask for an effect to be drawn.
 * The game must call EffectsSystem.draw() in order to draw the effects
 */
public class EffectsSystem {
    private Game myGame;
    private ArrayList<Effect> effectsToDraw;

    public EffectsSystem(Game myGame){
        this.myGame = myGame;
    }



    public void signNewEffect(Effect effect){
        effectsToDraw.add(effect);
    }

    public void draw(Canvas canvas){
        if(effectsToDraw == null){return;}
        for(Iterator<Effect> iterator = effectsToDraw.iterator(); iterator.hasNext();){
            Effect effect = iterator.next();
            if(effect.getState() == EffectState.ON_DRAW){
                effect.drawEffect(canvas);
            }
            else{// state == DONE
                iterator.remove();
            }
        }
    }


}
