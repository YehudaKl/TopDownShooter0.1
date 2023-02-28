package com.example.TopDownShooter.classes.systems;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class for holding static helper methods for players in the game.
 */
public class GameplayStatics {


    // Static method for getting the closest actor to a given pawn from a provided list of actors' subclasses
    public static Actor getClosestActor(ArrayList<? extends Actor> actors, Pawn pawn){

        if(actors == null || actors.size() == 0){
            return null;
        }

        Actor toReturn = actors.get(0);
        double minDistance = toReturn.getDistanceBetween(pawn);

        for(Actor actor: actors){
            double distance = actor.getDistanceBetween(pawn);
            if(distance < minDistance && actor != (Actor)pawn){
                minDistance = distance;
                toReturn = actor;
            }
        }

        if(toReturn == (Actor) pawn){
            return null;
        }
        return toReturn;

    }

    /**
     *
     * @param characters list of characters
     * @param pawn pawn to be compared to
     * @return the closest alive character to the pawn from the characters list
     * if no pawn is alive the method would return null
     */
    public static Character getClosestAliveCharacter(ArrayList<? extends Character> characters, Pawn pawn){
        if(characters == null || characters.size() == 0){
            return null;
        }

        // Clearing the dead characters
        clearDeadCharacters(characters);


        return (Character) getClosestActor(characters, pawn);
    }

    /**
     * Static method that gets an array list of pawns subclasses and another pawn
     * The method returns a list without its team members
     * Note! this method should not be used when the game has no teams so myPawn's team is null
     * In case of a scenario like this the method will return the list it was provided by
     * If the other pawn is in the pawns list, the other pawn will be removed.
     */
    public static void clearTeamMembers(ArrayList<? extends Pawn> pawns, Pawn otherPawn){

        if(otherPawn.getTeam() == null){
            // If myPawn's team is null, probably because the game is not teams game
            return;
        }

        Iterator<? extends Pawn> iterator = pawns.iterator();

        while(iterator.hasNext()){
            Pawn p = iterator.next();
            if(p == otherPawn || p.getTeam().equals(otherPawn.getTeam())){
                iterator.remove();
            }
        }

    }

    /**
     * Static method that gets an array list of pawns subclasses and another pawn
     * The method returns a list with only its team members
     * Note! this method should not be used when the game has no teams so myPawn's team is null
     * In case of a scenario like this the method will return the list it was provided by
     * If the other pawn is in the pawns list, the other pawn will be removed
     * @param pawns list of pawns to be cleared
     * @param otherPawn a pawn to be compared with
     */
    public static void clearNotTeamMembers(ArrayList<? extends Pawn> pawns, Pawn otherPawn){

        if(otherPawn.getTeam() == null){
            // If myPawn's team is null, probably because the game is not teams game
            return;
        }

        Iterator<? extends Pawn> iterator = pawns.iterator();
        Pawn p = iterator.next();
        while(iterator.hasNext()){
            if(p == otherPawn || !p.getTeam().equals(otherPawn.getTeam())){
                iterator.remove();
            }
        }
    }

    public static void clearDeadCharacters(ArrayList<? extends Character> characters){
        if (characters == null) {
            return;
        }

        Iterator<? extends Character> iterator = characters.iterator();

        while(iterator.hasNext()){
            if(iterator.next().isDead()){
                iterator.remove();
            }
        }
    }

    /**
     *
     * @param characters list of characters
     * @param otherPawn pawn to be compared to
     * @return the closest alive character from an opposing team of the other pawn
     */
    public static Character getClosestEnemy(ArrayList<? extends Character> characters, Pawn otherPawn){
        ArrayList<? extends Character> clonedCharactersList = new ArrayList<>(characters);
        clearDeadCharacters(clonedCharactersList);
        clearTeamMembers(clonedCharactersList, otherPawn);
        return (Character) getClosestActor(clonedCharactersList, otherPawn);
    }

    /**
     *
     * @param characters list of characters
     * @param otherPawn pawn to be compared to
     * @return the closest alive character from the same team of the other pawn
     */
    public static Character getClosestTeamMember(ArrayList<? extends Character> characters, Pawn otherPawn){
        ArrayList<? extends Character> clonedCharactersList = new ArrayList<>(characters);
        clearDeadCharacters(clonedCharactersList);
        clearNotTeamMembers(clonedCharactersList, otherPawn);
        return (Character) getClosestActor(clonedCharactersList, otherPawn);
    }

}
