package com.example.TopDownShooter.classes.gameObjects.actors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.assets.ActorAsset;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.gameObjects.ActorProperties.ActorProperty;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

import java.util.ArrayList;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * An Actor is a GameObject that has a position and can be seen by the user during the game
 */
// event bus
    // live data


public abstract class Actor extends GameObject {

    protected Position position;
    protected double direction;// In radians only!!
    protected Game myGame;
    private boolean isVisible;
    private ActorAsset asset;
    private int resourceId;









    public Actor(Game myGame, Position initPosition, int resourceId){
        super();
        this.myGame = myGame;
        this.position = initPosition;
        isVisible = true;
        this.resourceId = resourceId;
        this.direction = 0;

        this.asset = new ActorAsset(myGame, this, BitmapFactory.decodeResource(myGame.getContext().getResources(), resourceId));

    }

    // Constructor with direction param
    public Actor(Game myGame, Position initPosition, int resourceId, double direction){
        super();
        this.myGame = myGame;
        this.position = initPosition;
        isVisible = true;
        this.resourceId = resourceId;
        this.direction = direction;
        this.asset = new ActorAsset(myGame, this, BitmapFactory.decodeResource(myGame.getContext().getResources(), resourceId));



    }







    public void onDraw(OnDraw onDraw){
        if(!isVisible){return;}

        asset.draw(onDraw.getCanvas());

    }

    public Position getPosition(){
        return position;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getDirection() {
        return direction;
    }


    // Returns the distance between this actor and another actor
    public double getDistanceBetween(Actor other){
        double deltaX = this.getPosition().getX() - other.getPosition().getX();
        double deltaY = this.getPosition().getY() - other.getPosition().getY();

        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    // Sets the direction of the actor to be facing other given position
    public void facePosition(Position position){

        double x = position.getX() - this.position.getX();
        double y = position.getY() - this.position.getY();

        this.direction = Math.atan2(y, x);
    }




    protected void setVisibility(boolean isVisible){
        this.isVisible = isVisible;
    }




}
