package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters;

import android.graphics.Canvas;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
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


    public Character(Game myGame, Position initPosition, int resourceId){
        super(myGame, initPosition, resourceId);

        this.MAX_HEALTH = 100; // conf

        this.team = myGame.findMyTeam(this);
        this.health = MAX_HEALTH;
        this.healthState = CharacterHealthState.ALIVE;

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

        // Invalidating the character in order to make it deleted next frame
        team.OnMemberDied();
        owner.OnPawnDied();
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
    private boolean isDead(){
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
