package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalBody;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations.DefaultCharacterPhysicalSpecification;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;
import com.example.TopDownShooter.dataTypes.enums.CharacterHealthState;


/**
 *  A Character is a humane-like pawn that can walk around and attack.
 *  The Character class is the parent class of all shooters and monsters in the game.
 *  The character class draws the center of the asset in its position
 *  and rotated according to its rotation
 *  The character does not use his own native velocity. Instead it refers to the physical
 *  body's velocity as its velocity
 */
public abstract class Character extends Pawn {
    public final int MAX_HEALTH;
    protected float  health;
    protected final PhysicalBody<Character> physicalBody;
    private BitmapLoader bitmapLoader;
    private CharacterHealthState healthState;


    public Character(Game myGame, Position initPosition){
        super(myGame, initPosition);

        this.MAX_HEALTH = 100; // conf
        this.health = MAX_HEALTH;

        this.physicalBody = new PhysicalBody(myGame, this, DefaultCharacterPhysicalSpecification.getSpecification());
        this.healthState = CharacterHealthState.ALIVE;


    }

    @Override
    public void update(UpdateTrace updateTrace){
        if(isDead()){
            die();
            return;
        }

        super.update(updateTrace);

        updatePosition(physicalBody.getPosition());
        physicalBody.applyRotation(viewDirection());
        updateDirection(physicalBody.getAngle());


    }

    @Override
    protected void draw(Canvas canvas) {
        // Creating a rotated bitmap according to the direction


        if(physicalBody == null){
            return;
        }
        // Creating the drawable
        BitmapDrawable drawable = new BitmapDrawable(myGame.getResources(), getCurrentStateBitmapLoader().getBitmap(physicalBody.getAngle()));

        // Draw from the center
        final int WIDTH = drawable.getIntrinsicWidth();
        final int HEIGHT = drawable.getMinimumHeight();

        int boundX = (int)(viewPosition().getX() - WIDTH/2);
        int boundY = (int)(viewPosition().getY() - HEIGHT/2);

        drawable.setBounds(boundX, boundY, WIDTH + boundX, HEIGHT + boundY);

        drawable.draw(canvas);




    }

    @Override
    public void updateVelocity(Vector velocity) {
        physicalBody.applyVelocity(velocity);

    }

    @Override
    public Vector viewVelocity() {
        return physicalBody.getVelocity();
    }

    protected void die(){
        invalidate();
    }

    // Method for providing the right bitmap loader for the current frame
    // For example a shooter would return an image with a gun he is holding it
    // and with out a gun while he is standing

    protected abstract BitmapLoader getCurrentStateBitmapLoader();


    // Function for adding health only!!(positive argument)
    public boolean addHealth(float amount){

        if(amount <= 0){ return false; }

        float opt_health =  health + amount;
        if(opt_health > MAX_HEALTH){return false;}

        return true;

    }

    // Function for reducing health only!!(positive argument)
    public boolean reduceHealth(float amount){
        if(amount <= 0){return false;}

        health -= amount;


        return true;
    }

    // Function that checks if the character is dead by a pre written condition.
    // By default a Character is considered as dead if the health equals or below 0.
    // In case of an exception for that, the method should be Overridden by the child class with an alternative implementation
    // Should be used by the character in his update method
    public boolean isDead(){
        return (health <= 0);
    }

    public float getHealth(){
        return health;
    }

    public CharacterHealthState getHealthState() {
        return healthState;
    }
}
