package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

/**
 * An interface that specifies the object measurements and settings in the box2d physical world
 */
public interface PhysicalSpecification <T extends Actor>{

    BodyDef getBodyDef(T actor);
    FixtureDef getFixtureDef(T actor);
}
