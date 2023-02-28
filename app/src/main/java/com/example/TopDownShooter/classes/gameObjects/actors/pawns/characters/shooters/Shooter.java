package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters;

import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.guns.Gun;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * A Shooter is a character that can uses some kind of gun as its weapon
 * In addition a shooter has two different bitmapLoaders, one for shooting and one for reloading
 */
public abstract class Shooter extends Character {
    private Gun myGun;
    private boolean isReloading;
    private BitmapLoader shootingBitmap;
    private BitmapLoader reloadingBitmap;


    public void setShootingBitmap(BitmapLoader shootingBitmap) {
        this.shootingBitmap = shootingBitmap;
    }

    public void setReloadingBitmap(BitmapLoader reloadingBitmap) {
        this.reloadingBitmap = reloadingBitmap;
    }

    public Shooter(Game myGame, Position initPosition, BitmapLoader shootingBitmap, BitmapLoader reloadingBitmap, Gun myGun) {
        super(myGame, initPosition);
        this.shootingBitmap = shootingBitmap;
        this.reloadingBitmap = reloadingBitmap;
        this.myGun = myGun;
        this.myGun.setOwner(this);
    }

    public void shoot(){
        myGun.press();
    }

    public void releaseTrigger(){
        myGun.release();
    }


    @Override
    protected BitmapLoader getCurrentStateBitmapLoader() {

        if(myGun != null && myGun.isReloading()){
            return reloadingBitmap;
        }
        else{
            return shootingBitmap;
        }
    }

    public void reload(){
        //TODO reload with the amount of ammo that the shooter has in his pocket
        myGun.reload(myGun.getMaxAmmo());
    }

    public int getAmmoInGun(){
        return myGun.getCurrentAmmo();
    }
    public boolean isReloading(){return myGun.isReloading();}


    // Return the absolute position to spawn the bullet from
    public abstract Position getBulletSpawnPosition();

}
