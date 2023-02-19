package com.example.TopDownShooter.classes.events.physicalEvents;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalBody;
import com.example.TopDownShooter.classes.games.Game;

import org.jbox2d.dynamics.contacts.Contact;

/**
 * OnCollisionStart is triggered when two physical bodies start to collide
 */


public class OnCollisionStart extends OnEvent {

    PhysicalBody<?> bodyA;
    PhysicalBody<?> bodyB;

    public PhysicalBody<?> getBodyA() {
        return bodyA;
    }

    public PhysicalBody<?> getBodyB() {
        return bodyB;
    }

    public OnCollisionStart(Game game, Contact contact) {
        super(game);

        try{
            bodyA = (PhysicalBody<?>) contact.getFixtureA().getBody().getUserData();
            bodyB = (PhysicalBody<?>) contact.getFixtureB().getBody().getUserData();
        }catch (ClassCastException e){

        }
    }
}
