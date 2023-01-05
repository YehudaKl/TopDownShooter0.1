package com.example.TopDownShooter.classes.gameObjects.players;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStatusChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;

import java.util.ArrayList;

/**
 * A Player is a class that owns a pawn or pawns in the game, and in charge of "telling" them
 * what to do in each update().Usually a UserPlayer determines how to update the pawn by
 * a UI controllers such as joystick or buttons.
 * On the other hand, an AIPlayer determines his pawn actions by logical algorithms
 */
public abstract class Player extends GameObject{

    public final float MAX_PAWN_SPEED;

    protected Pawn myPawn;
    protected PawnMotionState motionState;

    public Player(Game myGame, Pawn myPawn, float maxSpeed){
        super(myGame);

        this.MAX_PAWN_SPEED = maxSpeed;
        this.myPawn = myPawn;
        this.motionState = PawnMotionState.STANDING;
    }


    public void onGameStart(OnGameStart onGameStart){
        this.motionState = PawnMotionState.MOVING;
    }

    public void onGameEnd(OnGameEnd onGameEnd){}

    public void onGameStatusChanged(OnGameStatusChanged onGameStatusChanged){
        switch (onGameStatusChanged.getNewStatus()){
            case RUN:
                // Code for game continue after a pause
                this.motionState = PawnMotionState.MOVING;
                break;
            case PAUSED:
                this.motionState = PawnMotionState.STANDING;
                break;
            default:
                break;
        }
    }


    // The function is used by the pawn in order to update himself
    public void updatePawn(UpdateTrace updateTrace){

        updateDirection();
        // Update velocity

        if(motionState != PawnMotionState.STANDING){
            updateVelocity();

            // Update position
            double newX = myPawn.getPosition().getX() + myPawn.getVelocity().getCoordinateX();
            double newY = myPawn.getPosition().getY() + myPawn.getVelocity().getCoordinateY();

            myPawn.getPosition().setX(newX);
            myPawn.getPosition().setY(newY);
        }

    }

    public void OnPawnDied(){
        invalidate();
    }

    // TODO use one method for updating the velocity. maybe with converting all for this to vectors

    // Method that each child class implements in order to specify the updating of the direction
    protected abstract void updateDirection();

    // Method that each child class implements in order to specify the updating of the velocity(movement)
    protected abstract void updateVelocity();


    // Method for getting the closest actor to you from a provided list of actors
    protected Actor getClosestActor(ArrayList<? extends Actor> actors){
        Actor toReturn = actors.get(0);
        double minDistance = toReturn.getDistanceBetween(myPawn);

        for(Actor actor: actors){
            double distance = actor.getDistanceBetween(myPawn);
            if(distance < minDistance && actor != (Actor)myPawn){
                minDistance = distance;
                toReturn = actor;
            }
        }

        return toReturn;

    }

}
