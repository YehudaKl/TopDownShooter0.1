package com.example.TopDownShooter.classes.gameObjects.players;

import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.interfaces.GameParticipant;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A Player is a class that owns a pawn or pawns in the game, and in charge of "telling" them
 * what to do in each update().Usually a UserPlayer determines how to update the pawn by
 * a GUI controlling system such as joystick or buttons attached to him.
 * On the other hand, an AIPlayer determines his pawn actions by logical algorithms
 */
public abstract class Player extends GameObject implements GameParticipant {

    public final float MAX_PAWN_SPEED;

    protected Pawn myPawn;
    protected Game myGame;
    protected PawnMotionState motionState;

    public Player(Game myGame, Pawn myPawn, float maxSpeed){
        this.MAX_PAWN_SPEED = maxSpeed;

        this.myPawn = myPawn;
        this.myGame = myGame;

        this.motionState = PawnMotionState.STANDING;


    }

    @Override
    public void onGameStart(){
        this.motionState = PawnMotionState.MOVING;
    }

    @Override
    public void onGamePause(){
        this.motionState = PawnMotionState.STANDING;
    }

    // The function is used by the pawn in order to update himself
    public void updatePawn(){

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
        Invalidate();
    }

    // Method that each child class implements in order to specify the updating of the direction
    protected abstract void updateDirection();

    // Method that each child class implements in order to specify the updating of the velocity(movement)
    protected abstract void updateVelocity();

    // The following methods returns the closest child class of actor next to the pawn
    // myPawn is added to the ignored actors by default
    //------------------------------------------------------------
    protected Actor getClosestActor(ArrayList<Actor> toIgnore){
        toIgnore.add((Actor)myPawn);
        ArrayList<Actor> list  = myGame.getActors(toIgnore);
        Actor toReturn = list.get(0);
        double minDistance = toReturn.getDistanceBetween(myPawn);

        for(Actor actor: list){
            double distance = actor.getDistanceBetween(myPawn);
            if(distance < minDistance){
                minDistance = distance;
                toReturn = actor;
            }
        }

        return toReturn;

    }

    protected Pawn getClosestPawn(ArrayList<Pawn> toIgnore){

        //Ignore myPawn
        toIgnore.add(myPawn);

        ArrayList<Pawn> list  = myGame.getPawns(toIgnore);

        Pawn toReturn = list.get(0);
        double minDistance = toReturn.getDistanceBetween(myPawn);

        for(Pawn pawn: list){
            double distance = pawn.getDistanceBetween(myPawn);
            if(distance < minDistance){
                minDistance = distance;
                toReturn = pawn;
            }
        }

        return toReturn;

    }

    protected Character getClosestCharacter(ArrayList<Character> toIgnore){
        //Ignore myPawn
        if((Character)myPawn != null) {
            toIgnore.add((Character) myPawn);
        }



        ArrayList<Character> list = myGame.getCharacters(toIgnore);

        Character toReturn = list.get(0);
        if(toReturn == null){return null;}
        double minDistance = toReturn.getDistanceBetween(myPawn);

        for(Character character: list){
            double distance = character.getDistanceBetween(myPawn);
            if(distance < minDistance){
                minDistance = distance;
                toReturn = character;
            }
        }

        return toReturn;

    }


    //------------------------------------------------------------


}
