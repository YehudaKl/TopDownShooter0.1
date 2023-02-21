package com.example.TopDownShooter.classes.gameObjects.actors.projectiles.bullets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import com.example.TopDownShooter.classes.assets.BitmapLoader;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.actors.projectiles.Projectile;
import com.example.TopDownShooter.classes.gameObjects.physics.CollisionListener;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalBody;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;

/**
 * A Bullet is a projectile that guns shoot
 */
public class Bullet extends Projectile implements CollisionListener {

    private final PhysicalBody<Bullet> physicalBody;
    protected float damage;
    private final BitmapLoader bitmapLoader;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public Bullet(Game myGame, Shooter sourceCharacter, PhysicalSpecification<Bullet> physicalSpecification, BitmapLoader bitmapLoader, float damage, float launchingPower){
        super(myGame, sourceCharacter, sourceCharacter.getBulletSpawnPosition());
        this.physicalBody = new PhysicalBody(myGame, this, physicalSpecification);
        this.physicalBody.setCollisionListener(this);
        this.bitmapLoader = bitmapLoader;
        this.damage = damage;
        this.physicalBody.applyRotation(sourceCharacter.viewDirection());

        // Creating the vector for the launching
        float x = (float) Math.cos(physicalBody.getAngle()) * launchingPower;
        float y = (float) Math.sin(physicalBody.getAngle()) * launchingPower;
        Vector force = new Vector(x, y);
        this.physicalBody.applyForce(force);


    }

    @Override
    protected void draw(Canvas canvas) {



        // Creating the drawable
        BitmapDrawable drawable = new BitmapDrawable(myGame.getResources(), bitmapLoader.getBitmap(physicalBody.getAngle()));


        // Draw from the center
        final int WIDTH = drawable.getIntrinsicWidth();
        final int HEIGHT = drawable.getMinimumHeight();

        int boundX = (int)(viewPosition().getX() - WIDTH/2);
        int boundY = (int)(viewPosition().getY() - HEIGHT/2);

        drawable.setBounds(boundX, boundY, WIDTH + boundX, HEIGHT + boundY);

        drawable.draw(canvas);


    }

    @Override
    public void onCollisionStart(Actor other) {
        if (other instanceof Character){
            ((Character) other).reduceHealth(damage);
            invalidate();
        }
    }

    @Override
    public void onCollisionEnd(Actor other) {

    }

    @Override
    public Position viewPosition() {
        return physicalBody.getPosition();
    }


}
