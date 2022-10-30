package com.example.TopDownShooter.classes.gameObjects.players.AIPlayers;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters.Zombie;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.enums.CharacterHealthState;
import com.example.TopDownShooter.dataTypes.enums.ZombieObjective;

import java.util.ArrayList;

/**
 * A zombie that is controlled by a ZombiePlayer class (AI) will always go track the closest
 * character (that is not from his team). The zombie wont rest till it eats his enemies brains (bite them).
 * The zombie dose not try to avoid any counter attack.
 *
 */
public class ZombiePlayer extends AIPlayer {

    private Character trackedCharacter;
    private Zombie myZombie;// A saved reference to my pawn as Zombie
    private ZombieObjective objective;

    public ZombiePlayer(Game myGame, Pawn myPawn) {
        super(myGame, myPawn, 5/*conf*/);
        this.myZombie = (Zombie)myPawn;
        this.objective = ZombieObjective.TRACK;// A default objective
    }

    @Override
    public void onGameStart() {
        super.onGameStart();
        trackedCharacter = findNewTrackedCharacter();
    }


    @Override
    public void updatePawn(){
        super.updatePawn();

        objective = generateObjective();


        switch(objective){

            case TRACK:
                Character newTrackedCharacter = findNewTrackedCharacter();
                if(trackedCharacter != null && newTrackedCharacter != trackedCharacter){
                    // Shout something funny
                }
                trackedCharacter = newTrackedCharacter;
                break;

            case BITE:
                if(trackedCharacter != null){
                    myZombie.bite(trackedCharacter);
                }
                break;

            default:
                break;
        }



    }

    @Override
    protected void updateDirection() {
        if(trackedCharacter == null){return;}
        myPawn.facePosition(trackedCharacter.getPosition());
    }

    @Override
    protected void updateVelocity() {
        if(objective == ZombieObjective.BITE){return;}
        // Update the pawn that it goes towards what it is faced to(the tracked character)
        myPawn.getVelocity().setCoordinateX(Math.cos(myPawn.getDirection()) * MAX_PAWN_SPEED );
        myPawn.getVelocity().setCoordinateY(Math.sin(myPawn.getDirection()) * MAX_PAWN_SPEED );
    }

    // Search for a closer character to track
    private Character findNewTrackedCharacter(){
        // returning the closest character while ignoring our team
        return getClosestCharacter(myGame.getTeam(myZombie.getTeam()));
    }


    @Override
    public void onGamePause() {

    }

    @Override
    public void onGameQuit() {

    }

    // Method that generates a new objective for the zombie player by a set of logic rules. May be extended to a full objective-generation system
    // for all pawns
    private ZombieObjective generateObjective(){

        // If a tracked character exists and in the range of bite: bite
        if(trackedCharacter != null && myZombie.isInRangeOfBite(trackedCharacter)){
            return ZombieObjective.BITE;
        }
        // else: track
        else{
            return ZombieObjective.TRACK;
        }
    }
}
