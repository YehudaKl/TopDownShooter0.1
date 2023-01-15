package com.example.TopDownShooter.classes.gameObjects.players.userPlayers;

import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;

/**
 * User player is a player that controls a character owned by the user of the app
 */

public class UserPlayer extends Player {

    private float joystickActuatorX;
    private float joystickActuatorY;


    public UserPlayer(Game game, Pawn pawn){
        super(game, pawn, 300/*conf*/);

        this.joystickActuatorX = 0;
        this.joystickActuatorY = 0;
    }


    @Override
    public void updatePawn(UpdateTrace updateTrace){
        super.updatePawn(updateTrace);
        joystickActuatorX = updateTrace.getJoystickActuatorX();
        joystickActuatorY = updateTrace.getJoystickActuatorY();

    }



    @Override
    protected void updateDirection() {
        // Checks that the pawn has moved in the current update. Else, no resetting of direction is needed
        if(myPawn.getVelocity().getCoordinateX() != 0 || myPawn.getVelocity().getCoordinateY() != 0){

            myPawn.setDirection(myPawn.getVelocity().getDirection());
        }
    }

    @Override
    public void updateVelocity(float deltaTime){
        myPawn.getVelocity().setCoordinateX(joystickActuatorX* MAX_PAWN_SPEED * deltaTime);
        myPawn.getVelocity().setCoordinateY(joystickActuatorY * MAX_PAWN_SPEED * deltaTime);

    }

    @Override
    public void onGameStart(OnGameStart onGameStart){
        super.onGameStart(onGameStart);
        this.motionState = PawnMotionState.MOVING;
    }
}
