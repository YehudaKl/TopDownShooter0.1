package com.example.TopDownShooter.classes.events.physicalEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalBody;
import com.example.TopDownShooter.classes.games.Game;

import org.jbox2d.dynamics.contacts.Contact;

/**
 * OnCollisionStart is triggered when two physical bodies end to collide
 */
public class OnCollisionEnd extends OnEvent {
    PhysicalBody<?> bodyA;
    PhysicalBody<?> bodyB;

    public PhysicalBody<?> getBodyA() {
        return bodyA;
    }

    public PhysicalBody<?> getBodyB() {
        return bodyB;
    }

    public OnCollisionEnd(Game game, Contact contact) {
        super(game);

        try{
            bodyA = (PhysicalBody<?>) contact.getFixtureA().getBody().getUserData();
            bodyB = (PhysicalBody<?>) contact.getFixtureB().getBody().getUserData();
        }catch (ClassCastException e){

        }

    }
}
