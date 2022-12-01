package com.example.TopDownShooter.classes.gameObjects.players;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.UI.Joystick;
import com.example.TopDownShooter.classes.games.Game;

public class UserPlayer extends Player{

    //Temporary-----------
    //TODO replace with a full control UI system
    private Joystick joystick;
    //-------------------

    public UserPlayer(Game game, Pawn pawn, Joystick joystick){
        super(game, pawn, 10/*conf*/);

        this.joystick = joystick;

    }


    @Override
    public void updatePawn(OnUpdate onUpdate){
        super.updatePawn(onUpdate);

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
        myPawn.getVelocity().setCoordinateX(joystick.getActuatorX() * MAX_PAWN_SPEED);
        myPawn.getVelocity().setCoordinateY(joystick.getActuatorY() * MAX_PAWN_SPEED);

    }

    public Joystick getJoystick(){
        return joystick;
    }





}
