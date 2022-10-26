package com.example.TopDownShooter.classes.gameObjects.actors.pawns;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.interfaces.GameParticipant;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;
;

/**
 * A Pawn is an actor that can be controlled by a player or an AI.
 */
public abstract class Pawn extends Actor implements GameParticipant {


    protected Player owner;
    protected Vector velocity;




    public Pawn(Game myGame, Position initPosition, int resourceId){
        super(myGame, initPosition, resourceId);

        this.owner = owner;
        this.velocity = new Vector(0, 0);
    }

    // Constructor for with direction for pawns that has to be initialized to current direction.
    // this constructor 
    public Pawn(Game myGame, Position initPosition, int resourceId, double direction){
        super(myGame, initPosition, resourceId,direction);

        this.owner = owner;
        this.velocity = new Vector(0, 0);
    }

    @Override
    public void update(){
        super.update();

        owner.updatePawn();
    }

    @Override
    public void onGameStart(){
        owner.onGameStart();
    }

    @Override
    public void onGamePause(){
        owner.onGamePause();
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public double getDirection() {
        return direction;
    }

    public Vector getVelocity() {
        return velocity;
    }






}
