package com.example.TopDownShooter.classes.gameObjects.players.userPlayers;

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

    public ShooterUserPlayer(Game game, Shooter shooter) {
        super(game, shooter);
        myGame.getOnShootObservable().subscribe(this::OnShoot);
    }

    public void OnShoot(OnShoot onShoot){
        try{
            ((Shooter) myPawn).shoot();

        }catch(ClassCastException e){

        }
    }
}
