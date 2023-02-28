package com.example.TopDownShooter.classes.gameObjects.guns;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.actors.projectiles.bullets.Bullet;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations.DefaultBulletPhysicalSpecification;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Vector;

public class Pistol extends Gun{
    private static final float DEFAULT_LAUNCHING_POWER = 200;
    private static final boolean IS_AUTOMATIC = false;
    private static final int DEFAULT_RELOAD_TIME = 2000;
    private static final int DEFAULT_COOL_DOWN_TIME = 100;
    private static final int DEFAULT_MAX_AMMO = 5;
    private static final float DEFAULT_DAMAGE = 1;
    public Pistol(Game myGame) {
        super(
                myGame,
                DefaultBulletPhysicalSpecification.getSpecification(),
                generateBitmapLoader(myGame),
                R.raw.pistol_shoot,
                R.raw.pistol_reload,
                DEFAULT_LAUNCHING_POWER,
                IS_AUTOMATIC,
                DEFAULT_RELOAD_TIME,
                DEFAULT_COOL_DOWN_TIME,
                DEFAULT_MAX_AMMO,
                DEFAULT_DAMAGE
        );
    }

    private static BitmapLoader generateBitmapLoader(Game game){
        BitmapLoader bulletBitmapLoader = new BitmapLoader(R.drawable.bulletyellowsilver_outline, game.getResources(), 90);
        bulletBitmapLoader.scale(0.5f, 0.5f);
        return bulletBitmapLoader;
    }
}
