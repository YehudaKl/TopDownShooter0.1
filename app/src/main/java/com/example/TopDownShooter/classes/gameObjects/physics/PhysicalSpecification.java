package com.example.TopDownShooter.classes.gameObjects.physics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

/**
 * An interface that specifies the object measurements and settings in the box2d physical world
 */
public interface PhysicalSpecification {

    BodyDef getBodyDef();
    FixtureDef getFixtureDef();
}
