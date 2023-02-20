package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Shooter is a character that can use pistols and guns as weapons
 */
public abstract class Shooter extends Character {

    private BitmapLoader shootingBitmap;
    private BitmapLoader reloadingBitmap;
    private boolean isReloading;

    public void setShootingBitmap(BitmapLoader shootingBitmap) {
        this.shootingBitmap = shootingBitmap;
    }

    public void setReloadingBitmap(BitmapLoader reloadingBitmap) {
        this.reloadingBitmap = reloadingBitmap;
    }

    public Shooter(Game myGame, Position initPosition, BitmapLoader shootingBitmap, BitmapLoader reloadingBitmap) {
        super(myGame, initPosition);
        this.isReloading = false;
        this.shootingBitmap = shootingBitmap;
        this.reloadingBitmap = reloadingBitmap;
    }

    // Method for shooting, must be used only by the owner!
    public void shoot(){


    }

    @Override
    protected BitmapLoader getCurrentStateBitmapLoader() {
        if(isReloading){
            return reloadingBitmap;
        }

        return shootingBitmap;
    }

    // Return the absolute position to spawn the bullet from
    public abstract Position getBulletSpawnPosition();

}
