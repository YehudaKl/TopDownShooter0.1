package com.example.TopDownShooter.classes.gameObjects.actors.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * Health bar. will be refactored with the GUI refactoring
 */
public class HealthBar extends Actor {

    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_MARGIN = 5;
    private static final int DEFAULT_DISTANCE_TO_PLAYER = 10;
    private static final Paint DEFAULT_BORDER_PAINT = new Paint (Color.GRAY);
    private static final Paint DEFAULT_PAINT = new Paint(Color.GREEN);


    private Character character;
    private Paint borderPaint;
    private Paint paint;
    private int width;
    private int height;
    private int margin;
    private float progressPercent;


    public HealthBar(Game myGame, Character character) {


        super(myGame, character.getPosition(), 0);
        this.character = character;

        this.borderPaint = DEFAULT_BORDER_PAINT;
        this.paint = DEFAULT_PAINT;

        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.margin = DEFAULT_MARGIN;

        this.progressPercent = calculatePercent();

    }

    @Override
    public void update(){
        position = new Position(character.getPosition());
    }

    @Override
    public void draw(Canvas canvas){

        // drawing the border
//        float left = (float)position.getX() - width/2;
//        float top = (float)position.getY() + DEFAULT_DISTANCE_TO_PLAYER + height;
//
//        float right = (float)position.getX() + width/2;
//        float bottom = (float)position.getY() + DEFAULT_DISTANCE_TO_PLAYER;
//
//
//        canvas.drawRect(left, top, right, bottom, borderPaint);
//
//        left += margin;
//        top += margin;
//        right = (right - margin) * progressPercent;
//        bottom -= margin;
//
//        canvas.drawRect(left, top, right, bottom, paint);
    }


    // This method must be called by the character after its health changed!!
    public void updateProgressPercent(){
        progressPercent = calculatePercent();
    }





    private float calculatePercent(){
        float health = character.getHealth();
        float maxHealth = character.MAX_HEALTH;

        return (health/maxHealth) * 100;
    }







}

