package com.example.TopDownShooter.classes.gameObjects;

import com.example.TopDownShooter.classes.events.surveys.Survey;
import com.example.TopDownShooter.classes.games.Game;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * GameObject is a generic class for all objects that take part in the game
 * or affect the game.
 */

public abstract class GameObject {

    CompositeDisposable compositeDisposable;

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
        this.compositeDisposable = new CompositeDisposable();

    }




    private void onSurvey (Survey<? extends GameObject> survey){
        //survey.check(this);
    }

    // Method for subscribing to events in game class in order to make sure that the object
    // unsubscribes when it gets invalid
    // In order to use the method the subscription code must be done in the method argument so the
    // disposable will be caught

    protected void subscribeToObservable(Disposable d){
        compositeDisposable.add(d);
    }

    public boolean getIsValid(){
        return isValid;
    }


    public void invalidate(){
        isValid = false;
        compositeDisposable.clear();

    }
}
