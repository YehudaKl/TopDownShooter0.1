package com.example.TopDownShooter.classes.gameObjects.players.AIPlayers;
import android.util.Log;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters.Zombie;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.systems.repository.ActorQualifier;
import com.example.TopDownShooter.classes.systems.repository.ActorsRepository;
import com.example.TopDownShooter.dataTypes.enums.CharacterHealthState;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;
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
    private final Zombie myZombie;// A saved reference to my pawn as Zombie
    private final ActorsRepository<Character> repository;
    private ZombieObjective objective;

    public ZombiePlayer(Game myGame, Pawn myPawn) {
        super(myGame, myPawn, 5/*conf*/);
        this.myZombie = (Zombie)myPawn;
        // Adding myZombie to the ignored actors of the repository
        ArrayList<Character> list = new ArrayList<>();
        list.add(myZombie);

        this.repository = new ActorsRepository<>(myGame, Character.class, list);
        this.objective = ZombieObjective.TRACK;// A default objective
    }

    @Override
    public void invalidate(){
        super.invalidate();
        repository.end();
    }

    @Override
    public void onGameStart(OnGameStart onGameStart) {
        super.onGameStart(onGameStart);
        repository.start();
        trackedCharacter = findNewTrackedCharacter();
    }

    @Override
    public void onJoinedGame() {
        super.onJoinedGame();
        repository.start();
        trackedCharacter = findNewTrackedCharacter();
    }

    @Override
    public void updatePawn(UpdateTrace updateTrace){
        super.updatePawn(updateTrace);

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
        if(trackedCharacter == null){
            objective = ZombieObjective.WAIT;
            return;
        }
        myPawn.facePosition(trackedCharacter.getPosition());
    }

    @Override
    protected void updateVelocity() {

        double x = 0;
        double y = 0;

        if(objective == ZombieObjective.TRACK){
            // Update the pawn that it goes towards what it is faced to(the tracked character)
            x = Math.cos(myPawn.getDirection()) * MAX_PAWN_SPEED;
            y = Math.sin(myPawn.getDirection()) * MAX_PAWN_SPEED;
        }



        myPawn.getVelocity().setCoordinateX(x);
        myPawn.getVelocity().setCoordinateY(y);
    }

    // Search for a closer character to track
    private Character findNewTrackedCharacter(){
        // Return null if the repository is empty
        if(repository.isEmpty()){ return null;}

        if(myPawn.getTeam() == null){
            // If there is no team - get the closes actor directly
            return (Character) getClosestActor(repository.getActors());
        }
        // returning the closest character from a different team!
        return (Character) getClosestActor(clearMyTeam(repository.getActors()));
    }

    // Method that generates a new objective for the zombie player by a set of logic rules. May be extended to a full objective-generation system
    // for all pawns
    private ZombieObjective generateObjective(){

        // If a tracked character exists and in the range of bite: bite
        if(trackedCharacter != null && !trackedCharacter.isDead() && myZombie.isInRangeOfBite(trackedCharacter)){
            return ZombieObjective.BITE;
        }
        else{
            return ZombieObjective.TRACK;
        }
    }


}
