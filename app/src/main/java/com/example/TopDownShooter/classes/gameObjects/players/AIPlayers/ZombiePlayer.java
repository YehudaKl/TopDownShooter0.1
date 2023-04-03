package com.example.TopDownShooter.classes.gameObjects.players.AIPlayers;
import android.util.Log;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters.Zombie;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.systems.repository.ActorsRepository;
import com.example.TopDownShooter.dataTypes.Vector;
import com.example.TopDownShooter.dataTypes.enums.ZombieObjective;
import com.example.TopDownShooter.classes.systems.GameplayStatics;

import java.util.ArrayList;

/**
 * A zombie that is controlled by a ZombiePlayer class (AI) will always go track the closest
 * character (that is not from his team). The zombie wont rest till it eats his enemies brains (bite them).
 * The zombie dose not try to avoid any counter attack.
 *
 */

public class ZombiePlayer extends AIPlayer<Zombie> {

    private final ActorsRepository<Character> repository;

    public ZombiePlayer(Game myGame) {
        super(myGame);

        this.repository = new ActorsRepository<>(myGame, Character.class);
        repository.start();
        subscribeToObservable(myGame.getObservableProvider().getOnGameEndObservable().subscribe(this::onGameEnd));

    }

    @Override
    public void invalidate(){
        super.invalidate();
        repository.end();
    }


    public void onGameEnd(OnGameEnd onGameEnd){
        invalidate();
    }



    // Search for a closer character to track
    private Character findNewTrackedCharacter(Zombie zombie){
        // Return null if the repository is empty
        if(repository.isEmpty()){ return null;}

        // Creating the current zombie to the cleaned actors
        ArrayList<Character> toClean = new ArrayList<>();
        toClean.add(zombie);

        // returning the closest character from a different team!
        return GameplayStatics.getClosestEnemy(repository.getActorsCleaned(toClean), zombie);
    }

    // Method that generates a new objective for the zombie player by a set of logic rules. May be extended to a full objective-generation system
    // for all pawns
    private ZombieObjective generateObjective(Zombie zombie, Character trackedCharacter){

            if(trackedCharacter == null){
                return ZombieObjective.WAIT;
            }

            // If a tracked character in the range of bite: bite
            if(zombie.isInRangeOfBite(trackedCharacter) && !trackedCharacter.isDead()){
                return ZombieObjective.BITE;
            }


            return ZombieObjective.TRACK;
        }


    @Override
    public void updatePawn(Zombie zombie, UpdateTrace updateTrace) {
        // Making sure that the pawn is a zombie, if not the method returns

        Character trackedCharacter = findNewTrackedCharacter(zombie);
        ZombieObjective objective = generateObjective(zombie, trackedCharacter);

        switch(objective){

            case TRACK:
                float coordinateX = (float) Math.cos(zombie.getDirectionTo(trackedCharacter)) * zombie.getSpeed() * updateTrace.getDeltaTime();
                float coordinateY = (float) Math.sin(zombie.getDirectionTo(trackedCharacter)) * zombie.getSpeed() * updateTrace.getDeltaTime();
                Vector velocity = new Vector(coordinateX, coordinateY);
                zombie.updateVelocity(velocity);
                zombie.updateDirection(velocity.getDirection());
                break;
            case BITE:
                zombie.bite(trackedCharacter);
                break;
            default:
                zombie.updateVelocity(new Vector(0, 0));
                break;

        }



    }
}
