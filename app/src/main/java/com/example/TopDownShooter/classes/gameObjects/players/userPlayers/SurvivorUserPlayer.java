package com.example.TopDownShooter.classes.gameObjects.players.userPlayers;

import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Survivor;
import com.example.TopDownShooter.classes.games.Game;

public class SurvivorUserPlayer extends ShooterUserPlayer<Survivor>{


    public SurvivorUserPlayer(Game myGame) {
        super(myGame);
    }

    @Override
    public void updatePawn(Survivor shooter, UpdateTrace updateTrace) {
        super.updatePawn(shooter, updateTrace);

    }
}
