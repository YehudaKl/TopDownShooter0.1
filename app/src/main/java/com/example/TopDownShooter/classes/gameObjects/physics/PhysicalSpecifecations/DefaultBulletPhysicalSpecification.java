package com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.actors.projectiles.bullets.Bullet;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class DefaultBulletPhysicalSpecification implements PhysicalSpecification<Bullet> {
    private static final float WIDTH = 20;
    private static final float HEIGHT = 34;
    private static DefaultBulletPhysicalSpecification instance;

    public static DefaultBulletPhysicalSpecification getSpecification(){
        if(instance == null){
            instance = new DefaultBulletPhysicalSpecification();
        }

        return instance;
    }
    @Override
    public BodyDef getBodyDef(Bullet bullet) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        // Setting the bullet's body initial position according to its bulletSpawnPosition
        // and converting it to world coordinates using the characters' physics manager
        Shooter shooter  = (Shooter) bullet.getSourceCharacter();
        bodyDef.position.set(bullet.getMyGame().getPhysicsManager().coordPixelsToWorld(shooter.getBulletSpawnPosition().getX(), shooter.getBulletSpawnPosition().getY()));

        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef(Bullet bullet) {

        PolygonShape shape = new PolygonShape();
        // Creating measured width and height after processing
        float measuredWidth = bullet.getMyGame().getPhysicsManager().scalarPixelsToWorld(WIDTH/2);
        float measuredHeight = bullet.getMyGame().getPhysicsManager().scalarPixelsToWorld(HEIGHT/2);
        shape.setAsBox(measuredWidth, measuredHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100;
        fixtureDef.friction = 3f;
        fixtureDef.restitution = 0;

        return fixtureDef;
    }
}
