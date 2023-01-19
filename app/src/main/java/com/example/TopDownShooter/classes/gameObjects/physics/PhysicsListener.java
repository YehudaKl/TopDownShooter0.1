package com.example.TopDownShooter.classes.gameObjects.physics;

import com.example.TopDownShooter.dataTypes.Position;

/**
 * An interfaces that specifies a listener to a physical body object
 */
public interface PhysicsListener {

    void onColliding(PhysicalBody physicalBody);
    void onPositionChanged(Position position);
    void onRotationChanged(float rotation);
}
