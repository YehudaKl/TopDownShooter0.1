package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.dataTypes.Position;

import org.jbox2d.common.Vec2;

/**
 * An interfaces that specifies a listener to a physical body object
 */
public interface CollisionListener {

    void onCollisionStart(Actor other);
    void onCollisionEnd(Actor other);

}
