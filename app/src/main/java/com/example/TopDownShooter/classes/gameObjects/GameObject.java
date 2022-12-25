package com.example.TopDownShooter.classes.gameObjects;

import com.example.TopDownShooter.classes.events.surveys.Survey;
import com.example.TopDownShooter.classes.games.Game;

import io.reactivex.rxjava3.functions.Consumer;

/**
 * GameObject is a generic class for all objects that take part in the game
 * or affect the game.
 */

public abstract class GameObject {

    /**
     * @param isValid determines the validation of the object in the context of its game.
     *                not valid actor would be deleted from the game at the update.
     *                In order to set the validation of an object to be false the method Invalidate() must be called from a sub class.
     *                Note!. It is important to delete any references to this object before the method is called in order to prevent crashes
     */
    protected final Game myGame;
    private boolean isValid;

    public GameObject(Game myGame){
        this.isValid = true;
        this.myGame = myGame;

        // TODO make it subscribe to the actual type of the object at run time
        //myGame.getOnSurveyObservable().subscribe((Consumer<Survey<? extends GameObject>>) survey -> onSurvey(survey));
    }




    private void onSurvey (Survey<? extends GameObject> survey){
        //survey.check(this);
    }

    public boolean getIsValid(){
        return isValid;
    }

    //TODO adam 
    public void invalidate(){
        isValid = false;

    }
}
