package com.example.TopDownShooter.classes.gameObjects.guns;

import android.media.SoundPool;

import androidx.annotation.RawRes;

import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.actors.projectiles.bullets.Bullet;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;

import java.util.TimerTask;

/**
 * A Gun shoots bullets and can be owned by a shooter
 * The gun itself is not an actor therefore it has no shape size or appearance.
 * Its the shooter's responsibility to change its bitmap according to its current gun
 */
public abstract class Gun extends GameObject {
    private Shooter owner;
    private final PhysicalSpecification bulletPhysicalSpecification;
    private final BitmapLoader bulletBitmapLoader;
    private final int shootSound;
    private final int reloadSound;
    private final float launchingPower;
    private final boolean isAutomatic;
    private final int reloadTime;
    private final int coolDownTime;
    private final int maxAmmo;
    private final float damage;

    private boolean isPressed;
    private boolean isReloading;
    private boolean isCoolDowned;
    private int currentAmmo;

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void setOwner(Shooter owner) {
        this.owner = owner;
    }

    public Gun(Game myGame, PhysicalSpecification bulletPhysicalSpecification, BitmapLoader bulletBitmapLoader, @RawRes int shootSoundResId, @RawRes int reloadSoundResId, float launchingPower, boolean isAutomatic, int reloadTime, int coolDownTime, int maxAmmo, float damage) {
        super(myGame);
        this.bulletPhysicalSpecification = bulletPhysicalSpecification;
        this.bulletBitmapLoader = bulletBitmapLoader;
        this.shootSound = myGame.getSoundPool().load(myGame.getContext(), shootSoundResId, 1);
        this.reloadSound = myGame.getSoundPool().load(myGame.getContext(), reloadSoundResId, 1);
        this.launchingPower = launchingPower;
        this.isAutomatic = isAutomatic;
        this.reloadTime = reloadTime;
        this.coolDownTime = coolDownTime;
        this.maxAmmo = maxAmmo;
        this.damage = damage;

        this.isPressed = false;
        this.isReloading = false;
        this.isCoolDowned = true;
        this.currentAmmo = maxAmmo;
    }

    public void press(){
        if(owner == null){return;}
        if(!canShoot()){return;}

        Bullet bullet = new Bullet(myGame, owner, bulletPhysicalSpecification, bulletBitmapLoader, damage, launchingPower);
        myGame.getSoundPool().play(shootSound, 1, 1, 0, 0, 1);//conf
        currentAmmo--;
        isPressed = true;
        isCoolDowned = false;
        coolDown();


    }

    private void coolDown(){
        myGame.getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                isCoolDowned = true;
            }
        }, coolDownTime);
    }

    public void release(){
        if(owner == null){return;}
        isPressed = false;
    }

    public void reload(int ammoAmount){
        if(owner == null || ammoAmount <= 0 || isPressed){return;}


        isReloading = true;
        myGame.getSoundPool().play(reloadSound, 1, 1, 0, 0, 1);//conf
        myGame.getTimer().schedule(new TimerTask() {
            @Override
            public void run() {

                if(ammoAmount + currentAmmo > maxAmmo){
                    currentAmmo = maxAmmo;
                }
                else{
                    currentAmmo+= ammoAmount;
                }

                isReloading = false;

            }
        }, reloadTime);
    }

    private boolean canShoot(){
        if(isAutomatic){
            return(currentAmmo > 0 && isCoolDowned);
        }else{
            return(!isPressed && currentAmmo > 0 && isCoolDowned);
        }
    }
}
