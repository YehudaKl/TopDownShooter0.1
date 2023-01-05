package com.example.TopDownShooter.classes.gameObjects.players;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.games.Game;

public class UserPlayer extends Player{

    public UserPlayer(Game game, Pawn pawn){
        super(game, pawn, 10/*conf*/);


    }


    @Override
    public void updatePawn(){
        super.updatePawn();

    }

    @Override
    protected void updateDirection() {
        // Checks that the pawn has moved in the current update. Else, no resetting of direction is needed
        if(myPawn.getVelocity().getCoordinateX() != 0 || myPawn.getVelocity().getCoordinateY() != 0){

            myPawn.setDirection(myPawn.getVelocity().getDirection());
        }
    }

    @Override
    public void updateVelocity(){
        myPawn.getVelocity().setCoordinateX(0.5* MAX_PAWN_SPEED);
        myPawn.getVelocity().setCoordinateY(0.5 * MAX_PAWN_SPEED);

    }





}
