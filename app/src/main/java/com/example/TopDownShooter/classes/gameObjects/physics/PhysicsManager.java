package com.example.TopDownShooter.classes.gameObjects.physics;

import android.view.Gravity;

import com.example.TopDownShooter.classes.events.physicalEvents.OnPhysicalUpdate;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * A physical manager for the game that manages all physics.
 */


public class PhysicsManager extends GameObject {

    private final PublishSubject<OnPhysicalUpdate> onPhysicalUpdateObservable;

    private final World world;

    public PublishSubject<OnPhysicalUpdate> getOnPhysicalUpdateObservable() {
        return onPhysicalUpdateObservable;
    }

    public World getWorld() {
        return world;
    }

    public PhysicsManager(Game myGame) {
        super(myGame);

        this.onPhysicalUpdateObservable = PublishSubject.create();
        this.world = createWorld();
    }

    // A public method for the game to update the physics manager each update cycle
    public void update(float deltaTime){
        updateWorld();
    }

    // Method for declaring and returning a world after setting its settings
    private World createWorld(){
        Vec2 gravity = new Vec2(0.f, 0.f);// No gravity in a top down game!
        boolean doSleep = true;
        return new World(gravity, doSleep);
    }

    // Update the box2d world using World.step()
    // and triggering OnPhysicalUpdate event to inform the physical bodies
    private void updateWorld(){
        // Updating according to the defaults suggested in the documentation
        float timeStamp  = 1.f/60.f;
        int velocityIterations = 8;
        int positionIterations = 3;

        world.step(timeStamp, velocityIterations, positionIterations);
        onPhysicalUpdateObservable.onNext(new OnPhysicalUpdate(myGame));
    }
}
