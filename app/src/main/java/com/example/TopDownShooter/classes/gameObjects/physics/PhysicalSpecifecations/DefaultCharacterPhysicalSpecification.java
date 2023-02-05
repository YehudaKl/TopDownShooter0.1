package com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecifecations;

import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicalSpecification;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

/**
 * A Singleton implementer of the physicalSpec interface for default characters in the game
 * based on the box shape of all kenney assets characters
 */
public class DefaultCharacterPhysicalSpecification implements PhysicalSpecification<Character> {

    private static DefaultCharacterPhysicalSpecification instance;

    public static DefaultCharacterPhysicalSpecification getSpecification(){
        if(instance == null){
            instance = new DefaultCharacterPhysicalSpecification();
        }

        return instance;
    }
    @Override
    public BodyDef getBodyDef(Character character) {
        BodyDef bodyDef = new BodyDef();


        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef(Character character) {
        return null;
    }
}
