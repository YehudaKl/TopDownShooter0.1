package com.example.TopDownShooter.classes.gameObjects.actors;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.TopDownShooter.classes.assets.ActorAsset;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * An Actor is a GameObject that has a position and can be seen by the user during the game
 */
// event bus
    // live data


public abstract class Actor extends GameObject {

    protected Position position;
    protected float direction;// In radians only!!
    private boolean isVisible;
    private ActorAsset asset;
    private int resourceId;



    // Constructor with direction param
    public Actor(Game myGame, Position initPosition, int resourceId, float direction){
        super(myGame);
        this.position = initPosition;
        isVisible = true;
        this.resourceId = resourceId;
        this.direction = direction;
        this.asset = new ActorAsset(myGame, this, BitmapFactory.decodeResource(myGame.getContext().getResources(), resourceId));

        // Subscribing to the OnDraw
        subscribeToGameObservable(myGame.getOnDrawObservable().subscribe(this::onDraw));
        // Declare the new actor as valid
        myGame.getOnActorValidObservable().onNext(new OnActorValid(myGame, this));


    }






    @Override
    public void invalidate() {
        myGame.getOnActorInvalidObservable().onNext(new OnActorInvalid(myGame, this));
        super.invalidate();
    }

    public void onDraw(OnDraw onDraw){
        draw(onDraw.getCanvas());
    }

    public Position getPosition(){
        return position;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getDirection() {
        return direction;
    }


    // Returns the distance between this actor and another actor
    public float getDistanceBetween(Actor other){
        double deltaX = this.getPosition().getX() - other.getPosition().getX();
        double deltaY = this.getPosition().getY() - other.getPosition().getY();

        return (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    // Sets the direction of the actor to be facing other given position
    public void facePosition(Position position){

        float x = position.getX() - this.position.getX();
        float y = position.getY() - this.position.getY();

        this.direction = (float)Math.atan2(y, x);
    }


    protected void setVisibility(boolean isVisible){
        this.isVisible = isVisible;
    }

    protected void draw(Canvas canvas){
        if(!isVisible || asset == null){return;}

        asset.draw(canvas);
    }




}
