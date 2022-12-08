package com.example.TopDownShooter.classes.events.surveys;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.games.Game;

import java.util.ArrayList;

/**
 * The Survey class is used in order to count or get reference to a group of game objects in the game.
 * Each time a survey event is triggered each subscriber must call the check() and pass itself as a parameter.
 * And each subscriber that comply with the survey's contract requirements will sign into the survey.
 * The object that invites the survey is the generator, which will be ignored by the survey.
 *
 */
public class Survey <T extends GameObject> extends OnEvent {

    private final SurveyContract<T> contract;
    private final T generator;
    private ArrayList<T> signedObjects;

    public Survey(Game myGame, T generator, SurveyContract<T> contract){
        super(myGame);
        this.generator = generator;
        this.contract = contract;
    }

    public void check(T obj){
        if(obj != generator && contract.isComply(obj)){
            signedObjects.add(obj);
        }

    }

    public SurveyContract<T> getContract() {
        return contract;
    }

    public ArrayList<T> getSignedObjects(){
        return signedObjects;
    }
}
