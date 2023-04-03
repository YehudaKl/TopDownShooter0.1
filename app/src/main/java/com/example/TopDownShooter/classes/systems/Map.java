package com.example.TopDownShooter.classes.systems;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * The Map object is responsible for drawing the map relative to its themeActor
 * Each game class must own a map object and call map.drawMap before every onDraw event
 */
public class Map {
    private final int extendRange;
    private final Bitmap originMap;
    private final Game myGame;
    private Bitmap lastMapFrame;
    private Position windowOrigin;
    private Position lastWindowOrigin;
    private final int windowWidth;
    private final int windowHeight;
    private final int worldWidth;
    private final int worldHeight;

    public Map(Game myGame, Bitmap originMap){
        this.myGame = myGame;
        this.originMap = originMap;
        this.worldWidth = originMap.getWidth();
        this.worldHeight = originMap.getHeight();
        this.extendRange = calcExtendRange();
        this.windowOrigin = new Position(0, 0);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) myGame.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.windowWidth = displayMetrics.widthPixels;
        this.windowHeight = displayMetrics.heightPixels;
    }

    // Update callback for updating the window origin
    // Should be called after the onUpdate event
    public void updateMap(Actor themeActor){
        lastWindowOrigin = windowOrigin;
        windowOrigin = calcOrigin(themeActor);
    }

    public void draw(Canvas canvas){
        if(windowOrigin == null){
            return;
        }

        Bitmap currentMapFrame;
        if(windowOrigin.equals(lastWindowOrigin)){
            currentMapFrame = lastMapFrame;

        }
        else{
            currentMapFrame = generateMapFrame();
        }

        canvas.drawBitmap(currentMapFrame, 0, 0, null);
    }

    // Returns the relative position to the window from an abs position
    // If the relative position is out of the window the method returns null
    public Position getRelativePosition(Position absPosition){
        if(windowOrigin == null){
            return  null;
        }
        float x = absPosition.getX() - windowOrigin.getX();
        float y = absPosition.getY() - windowOrigin.getY();

        if(!isPositionInContext(absPosition)){
            return null;
        }

        return new Position(x, y);
    }

    private Bitmap generateMapFrame(){
        Bitmap bitmap =  Bitmap.createBitmap(originMap, (int)windowOrigin.getX(), (int)windowOrigin.getY(), windowWidth, windowHeight);
        lastMapFrame = bitmap;
        return bitmap;
    }

    private Position calcOrigin(Actor themeActor){

        int x =  (int)themeActor.viewPosition().getX() - windowWidth/2;
        int y =  (int)themeActor.viewPosition().getY() - windowHeight/2;

        if(x < 0){
            x = 0;
        }
        if(y < 0){
            y = 0;
        }
        if(x > worldWidth - windowWidth){
            x = worldWidth - windowWidth;
        }
        if(y > worldHeight- windowHeight){
            y = worldHeight- windowHeight;
        }
        return new Position(x, y);
    }

    // Check if a given position is close enough to the screen's absolute position
    // so it can be drawn to the canvas
    private boolean isPositionInContext(Position position){
        Position extendBoundStart = new Position(windowOrigin.getX() - extendRange, windowOrigin.getY() - extendRange);
        Position extendBoundEnd = new Position(windowOrigin.getX() + windowWidth + extendRange, windowOrigin.getY() + windowHeight + extendRange);

        float x = position.getX();
        float y = position.getY();
        return (x < extendBoundEnd.getX() && x > extendBoundStart.getX() && y < extendBoundEnd.getY() && y > extendBoundStart.getY());
    }

    private int calcExtendRange(){
        return 50;
    }
}
