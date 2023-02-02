package com.example.TopDownShooter.classes.gameObjects.players.userPlayers;

import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.UIEvents.OnShoot;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.games.Game;

/**
 * ShooterUserPlayer is a player for pawns of type shooter .
 * Generally a ShooterUserPlayer should be suitable for each type of character that use the regular shoot button on the screen
 * for shooting
 */
public class ShooterUserPlayer extends UserPlayer{

    public ShooterUserPlayer(Game myGame) {
        super(myGame);
    }

    @Override
    public void updatePawn(Pawn pawn, UpdateTrace updateTrace) {
        Shooter shooter;
        try{
            shooter = (Shooter) pawn;
        }catch(ClassCastException e){
            return;
        }
        super.updatePawn(pawn, updateTrace);

        if(updateTrace.getIsShootPressed()){
            shooter.shoot();
        }

    }
}
