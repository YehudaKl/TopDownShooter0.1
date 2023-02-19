package com.example.TopDownShooter.classes.gameObjects.actors.projectiles.bullets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import com.example.TopDownShooter.classes.assets.Asset;
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
public abstract class Bullet extends Projectile implements CollisionListener {

    private final PhysicalBody<Bullet> physicalBody;
    protected float damage;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public Bullet(Game myGame, float damage, Shooter sourceCharacter, Position initPosition, Asset asset, PhysicalSpecification<Bullet> physicalSpecification){
        super(myGame, sourceCharacter, initPosition, asset);
        this.physicalBody = new PhysicalBody(myGame, this, physicalSpecification);
        this.physicalBody.setCollisionListener(this);
        this.damage = damage;
        this.physicalBody.applyRotation(sourceCharacter.viewDirection());
        float x = (float) Math.cos(physicalBody.getAngle()) * 1000;
        float y = (float) Math.sin(physicalBody.getAngle()) * 1000;
        this.physicalBody.applyForce(new Vector(x, y));


    }

    @Override
    protected void draw(Canvas canvas) {

        double degrees = Math.toDegrees(physicalBody.getAngle() + (float)Math.PI/2);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) degrees);
        Bitmap originBitmap = asset.getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix, true);

        // Creating the drawable
        BitmapDrawable drawable = new BitmapDrawable(myGame.getResources(), bitmap);


        final int WIDTH = drawable.getIntrinsicWidth();
        final int HEIGHT = drawable.getMinimumHeight();

        // Draw from the center
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
