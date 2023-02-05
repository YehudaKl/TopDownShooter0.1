package com.example.TopDownShooter.classes.gameObjects.players.userPlayers;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.games.TeamsGame;
import com.example.TopDownShooter.dataTypes.Vector;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;

/**
 * User player is a player that controls a character owned by the user of the app
 */

public abstract class UserPlayer<T extends Pawn> extends Player<T> {


    public UserPlayer(Game myGame){ super(myGame);}

   @Override
    public void updatePawn(T pawn, UpdateTrace updateTrace){

        float coordinateX = updateTrace.getJoystickActuatorX() * pawn.getSpeed() * updateTrace.getDeltaTime();
        float coordinateY = updateTrace.getJoystickActuatorY() * pawn.getSpeed() * updateTrace.getDeltaTime();

       Vector velocity = new Vector(coordinateX, coordinateY);

       pawn.updateVelocity(velocity);

       if(velocity.isEmpty()){return;}
       pawn.updateDirection(velocity.getDirection());



   }
}
