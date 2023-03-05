package com.example.TopDownShooter.classes.gameObjects.players.userPlayers;

import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Survivor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.systems.GameplayStatics;
import com.example.TopDownShooter.classes.systems.repository.ActorsRepository;

public class SurvivorUserPlayer extends ShooterUserPlayer<Survivor>{

    private final ActorsRepository<Character> repository;
    public SurvivorUserPlayer(Game myGame) {
        super(myGame);
        this.repository = new ActorsRepository<>(myGame, Character.class);
        repository.start();
    }

    @Override
    public void updatePawn(Survivor survivor, UpdateTrace updateTrace) {
        super.updatePawn(survivor, updateTrace);
        Character closestEnemy = GameplayStatics.getClosestEnemy(repository.getActors(), survivor);
        if(closestEnemy == null){
            return;
        }
        if(survivor.getDistanceBetween(closestEnemy) <= survivor.getScreamRange()){
            survivor.startScream();
        }
        else{
            survivor.stopScream();
        }

    }
}
