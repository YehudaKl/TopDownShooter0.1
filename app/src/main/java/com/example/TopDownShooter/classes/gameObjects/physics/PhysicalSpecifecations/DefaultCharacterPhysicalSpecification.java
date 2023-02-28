package com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;
import com.example.TopDownShooter.dataTypes.Position;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import shiffman.box2d.Box2DProcessing;

/**
 * A Singleton implementer of the physicalSpec interface for default characters in the game
 * based on the box shape of all kenney assets characters
 */
public class DefaultCharacterPhysicalSpecification implements PhysicalSpecification {
    private static final float WIDTH = 50;
    private static final float HEIGHT = 100;
    private static DefaultCharacterPhysicalSpecification instance;

    public static DefaultCharacterPhysicalSpecification getSpecification(){
        if(instance == null){
            instance = new DefaultCharacterPhysicalSpecification();
        }

        return instance;
    }
    @Override
    public BodyDef getBodyDef(Position position, Box2DProcessing physicalManager) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        // Setting the characters' body initial position according to its position
        // and converting it to world coordinates using the characters' physics manager
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
