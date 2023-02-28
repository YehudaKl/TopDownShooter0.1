package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.dataTypes.Position;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import shiffman.box2d.Box2DProcessing;

/**
 * An interface that specifies the object measurements and settings in the box2d physical world
 */
public interface PhysicalSpecification{

    BodyDef getBodyDef(Position position, Box2DProcessing physicalManager);
    FixtureDef getFixtureDef(Box2DProcessing physicalManager);
}
