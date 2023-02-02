package com.example.TopDownShooter.classes.gameObjects.physics;

import android.view.Gravity;

import com.example.TopDownShooter.classes.events.physicalEvents.OnPhysicalUpdate;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import io.reactivex.rxjava3.subjects.PublishSubject;
import shiffman.box2d.Box2DProcessing;

/**
 * A physical manager for the game that manages all physics.
 */


public class PhysicsManager{


    private final Box2DProcessing pBox2d;

    public Box2DProcessing getpBox2d() {
        return pBox2d;
    }

    public PhysicsManager() {
        this.pBox2d = new Box2DProcessing();
    }

    // A public method for the game to update the physics manager each update cycle
    public void update(float deltaTime){
        pBox2d.step();
    }

}
