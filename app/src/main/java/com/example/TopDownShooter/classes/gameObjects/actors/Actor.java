package com.example.TopDownShooter.classes.gameObjects.actors;

import android.graphics.Canvas;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * An Actor is a GameObject that has a position and can be seen by the user during the game
 */



public abstract class Actor extends GameObject {

    private boolean isVisible;

    // Constructor with direction param
    public Actor(Game myGame){
        super(myGame);
        isVisible = true;

        // Subscribing to the OnDraw
        subscribeToObservable(myGame.getOnDrawObservable().subscribe(this::onDraw));
        // Declare the new actor as valid
        myGame.getOnActorValidObservable().onNext(new OnActorValid(myGame, this));


    }






    @Override
    public void invalidate() {
        myGame.getOnActorInvalidObservable().onNext(new OnActorInvalid(myGame, this));
        super.invalidate();
    }

    public void onDraw(OnDraw onDraw){
        if(isVisible){
            draw(onDraw.getCanvas());
        }
    }

    public abstract Position viewPosition();
    public abstract float viewDirection();


    // Returns the distance between this actor and another actor
    public float getDistanceBetween(Actor other){
        double deltaX = this.viewPosition().getX() - other.viewPosition().getX();
        double deltaY = this.viewPosition().getY() - other.viewPosition().getY();

        return (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    // Get the direction to another actor
    public float getDirectionTo(Actor actor){

        float x = actor.viewPosition().getX() - this.viewPosition().getX();
        float y = actor.viewPosition().getY() - this.viewPosition().getY();

        return (float)Math.atan2(y, x);
    }



    protected void setVisibility(boolean isVisible){
        this.isVisible = isVisible;
    }

    protected abstract void draw(Canvas canvas);



}
