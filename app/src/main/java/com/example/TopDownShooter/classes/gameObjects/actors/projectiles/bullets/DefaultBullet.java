package com.example.TopDownShooter.classes.gameObjects.actors.projectiles.bullets;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.assets.Asset;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations.DefaultBulletPhysicalSpecification;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

/**
 * Default bullet for guns and other weapons
 */
public class DefaultBullet extends Bullet{

    private static final float DEFAULT_DAMAGE = 2;

    public DefaultBullet(Game myGame, Shooter sourceCharacter, Position initPosition) {
        super(myGame, DEFAULT_DAMAGE, sourceCharacter, initPosition, new Asset(R.drawable.bulletyellowsilver_outline, myGame.getResources()), DefaultBulletPhysicalSpecification.getSpecification());

    }





}
