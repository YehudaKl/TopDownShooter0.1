package com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.actors.projectiles.bullets.Bullet;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;
import com.example.TopDownShooter.dataTypes.Position;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import shiffman.box2d.Box2DProcessing;

public class DefaultBulletPhysicalSpecification implements PhysicalSpecification {
    private static final float WIDTH = 10;
    private static final float HEIGHT = 17;
    private static DefaultBulletPhysicalSpecification instance;

    public static DefaultBulletPhysicalSpecification getSpecification(){
        if(instance == null){
            instance = new DefaultBulletPhysicalSpecification();
        }

        return instance;
    }
    @Override
    public BodyDef getBodyDef(Position position, Box2DProcessing physicalManager) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        bodyDef.position.set(physicalManager.coordPixelsToWorld(position.getX(), position.getY()));

        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef(Box2DProcessing physicalManager) {

        PolygonShape shape = new PolygonShape();
        // Creating measured width and height after processing
        float measuredWidth = physicalManager.scalarPixelsToWorld(WIDTH/2);
        float measuredHeight = physicalManager.scalarPixelsToWorld(HEIGHT/2);
        shape.setAsBox(measuredWidth, measuredHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100;
        fixtureDef.friction = 3f;
        fixtureDef.restitution = 0;

        return fixtureDef;
    }
}
