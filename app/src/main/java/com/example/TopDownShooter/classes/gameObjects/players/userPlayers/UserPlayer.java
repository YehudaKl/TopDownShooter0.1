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

public abstract class UserPlayer extends Player {


    public UserPlayer(Game myGame){ super(myGame);}

   @Override
    public void updatePawn(Pawn pawn, UpdateTrace updateTrace){

        float coordinateX = updateTrace.getJoystickActuatorX() * pawn.MAX_SPEED * updateTrace.getDeltaTime();
        float coordinateY = updateTrace.getJoystickActuatorX() * pawn.MAX_SPEED * updateTrace.getDeltaTime();

       Vector velocity = new Vector(coordinateX, coordinateY);

       if(velocity.isEmpty()){return;}

       pawn.updateVelocity(velocity);
       pawn.updateDirection(velocity.getDirection());

       pawn.step();

   }
}
