package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters;

import android.graphics.Canvas;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.gameObjects.actors.UI.HealthBar;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.enums.CharacterHealthState;


/**
 *  A Character is a humane-like pawn that can walk around and attack.
 *  The Character class is the parent class of all shooters and monsters in the game.
 */
public abstract class Character extends Pawn {
    public final int MAX_HEALTH;


    protected Team team;
    protected float  health;
    private CharacterHealthState healthState;
    private HealthBar healthBar;


    public Character(Game myGame, Position initPosition, int resourceId){
        super(myGame, initPosition, resourceId);

        this.MAX_HEALTH = 100; // conf

        this.team = myGame.findMyTeam(this);
        this.health = MAX_HEALTH;
        this.healthState = CharacterHealthState.ALIVE;

        this.healthBar = new HealthBar(myGame, this);
        myGame.signNewActor(healthBar);

    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }

    @Override
    public void update(){
        super.update();

        if(isDead()){
            die();
        }
    }

    protected void die(){
        this.healthState = CharacterHealthState.DEAD;
        // TODO ...

    }

    // Function for adding health only!!(positive argument)
    public boolean addHealth(float amount){

        if(amount <= 0){ return false; }

        float opt_health =  health + amount;
        if(opt_health > MAX_HEALTH){return false;}

        healthBar.updateProgressPercent();
        return true;

    }

    // Function for reducing health only!!(positive argument)
    public boolean reduceHealth(float amount){
        if(amount <= 0){return false;}

        health -= amount;

        healthBar.updateProgressPercent();
        return true;
    }

    // Function that checks if the character is dead by a pre written condition.
    // By default a Character is considered as dead if the health equals or below 0.
    // In case of an exception for that, the method should be Overridden by the child class with an alternative implementation
    // Should be used by the character in his update method
    public boolean isDead(){
        return (health <= 0);
    }

    public Team getTeam() {
        return team;
    }

    public float getHealth(){
        return health;
    }

    public CharacterHealthState getHealthState() {
        return healthState;
    }
}
