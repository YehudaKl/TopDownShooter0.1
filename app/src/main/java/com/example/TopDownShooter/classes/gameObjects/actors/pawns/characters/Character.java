package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.TopDownShooter.classes.assets.Asset;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalBody;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations.DefaultCharacterPhysicalSpecification;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.enums.CharacterHealthState;


/**
 *  A Character is a humane-like pawn that can walk around and attack.
 *  The Character class is the parent class of all shooters and monsters in the game.
 *  The character class draws the center of the asset in its position
 *  and rotated according to its rotation
 */
public abstract class Character extends Pawn {
    public final int MAX_HEALTH;
    protected float  health;
    protected final PhysicalBody<Character> physicalBody;
    private CharacterHealthState healthState;


    public Character(Game myGame, Position initPosition, Asset asset){
        super(myGame, initPosition, asset);

        this.MAX_HEALTH = 100; // conf
        this.health = MAX_HEALTH;

        this.physicalBody = new PhysicalBody<Character>(myGame, this, DefaultCharacterPhysicalSpecification.getSpecification());

        this.healthState = CharacterHealthState.ALIVE;


    }

    @Override
    public void update(UpdateTrace updateTrace){
        System.out.println("Position of " + this + " is X: " + viewPosition().getX() + " Y: " + viewPosition().getY());

        if(isDead()){
            die();
            return;
        }

        super.update(updateTrace);

        // Updating the position according to the physical position.
        updatePosition(physicalBody.getPosition());


    }

    @Override
    protected void draw(Canvas canvas) {
        // Creating a rotated bitmap according to the direction

        double degrees = Math.toDegrees(viewDirection());
        Matrix matrix = new Matrix();
        matrix.postRotate((float) degrees);
        Bitmap originBitmap = asset.getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix, true);

        // Creating the drawable
        BitmapDrawable drawable = new BitmapDrawable(myGame.getResources(), bitmap);


        final int WIDTH = drawable.getIntrinsicWidth();
        final int HEIGHT = drawable.getMinimumHeight();

        // Draw from the center
        int boundX = (int)(viewPosition().getX() - WIDTH/2);
        int boundY = (int)(viewPosition().getY() - HEIGHT/2);

        drawable.setBounds(boundX, boundY, WIDTH + boundX, HEIGHT + boundY);

        drawable.draw(canvas);



    }

    protected void die(){
        invalidate();
    }


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
