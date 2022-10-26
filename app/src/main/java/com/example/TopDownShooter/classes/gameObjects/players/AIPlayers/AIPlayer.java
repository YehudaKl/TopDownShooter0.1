package com.example.TopDownShooter.classes.gameObjects.players.AIPlayers;

import com.example.TopDownShooter.classes.gameObjects.actors.Hero;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

import java.util.ArrayList;

public abstract class AIPlayer extends Player {


    public AIPlayer(Game myGame, Pawn myPawn, float maxSpeed){
        super(myGame, myPawn, maxSpeed);

    }



    @Override
    public void onGameStart() {
        super.onGameStart();
    }

    @Override
    public void onGamePause() {

    }

    @Override
    public void onGameQuit() {

    }




}
