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
public class ShooterUserPlayer extends UserPlayer<Shooter>{

    public ShooterUserPlayer(Game myGame) {
        super(myGame);
    }

    @Override
    public void updatePawn(Shooter shooter, UpdateTrace updateTrace) {
        super.updatePawn(shooter, updateTrace);

        if(updateTrace.getIsShoot()){
            shooter.shoot();
        }
        else{
            shooter.releaseTrigger();
        }

        // TODO add reloading button
        if(shooter.getAmmoInGun() <= 0){
            shooter.reload();
        }

    }
}
