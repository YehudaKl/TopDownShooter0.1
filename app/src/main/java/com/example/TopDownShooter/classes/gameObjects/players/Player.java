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
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

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

    public void onJoinedGame(){
        this.motionState = PawnMotionState.MOVING;
    }


    // The function is used by the pawn in order to update himself
    public void updatePawn(UpdateTrace updateTrace){

        updateDirection();
        // Update velocity

        if(motionState != PawnMotionState.STANDING){
            updateVelocity(updateTrace.getDeltaTime());

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
    protected abstract void updateVelocity(float deltaTime);


    // Method for getting the closest actor to you from a provided list of actors
    protected Actor getClosestActor(ArrayList<? extends Actor> actors){
        if(actors == null || actors.size() == 0){
            return null;
        }

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

    // Method that gets an array list of pawns subclasses and returns a list without its team members
    // Note! this method should not be used when the game has no teams so myPawn's team is null
    // In case of a scenario like this the method will return the list it was provided by
    protected ArrayList<Pawn> clearMyTeam(ArrayList<? extends Pawn> pawns){
        if(myPawn.getTeam() == null){
            // If myPawn's team is null, probably because the game is not teams game
            return new ArrayList<>(pawns);
        }
        ArrayList<Pawn> toReturn = new ArrayList();
        for(Pawn pawn: pawns){
            if(!pawn.getTeam().equals(myPawn.getTeam())){
                toReturn.add(pawn);
            }
        }

        return toReturn;


    }

    // Method that gets an array list of pawns subclasses and returns a list with only its team members
    // Note! this method should not be used when the game has no teams so myPawn's team is null
    // In case of a scenario like this the method will return an empty list
    protected ArrayList<Pawn> getMyTeam(ArrayList<? extends Pawn> pawns){
        if(myPawn.getTeam() == null){
            // If myPawn's team is null, probably because the game is not teams game
            return new ArrayList<>();
        }

        ArrayList<Pawn> toReturn = new ArrayList();

        for(Pawn pawn: pawns) {
            if (pawn.getTeam().equals(myPawn.getTeam())) {
                toReturn.add(pawn);
            }
        }

        return toReturn;
    }

}
