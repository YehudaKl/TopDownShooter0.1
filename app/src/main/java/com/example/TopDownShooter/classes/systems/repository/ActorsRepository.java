package com.example.TopDownShooter.classes.systems.repository;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * ActorsRepository is a repository that holds references to all valid actor from the same type in a game.
 * Each actor that needs a reference to other actor must get it from a repository.
 * Actors that should be ignored by the repository (for example the objects that uses the repository),
 * must be included in the actors to ignore arrayList.
 */
public class ActorsRepository <T extends Actor> extends GameObject{

    private final Class<T> classOfType;
    private boolean isInAction;
    /**
     * @param classOfType
     *  Class object of the generic type must be provided in order to perform casts
     */


    private final ArrayList<T> actorsList;
    private final ArrayList<T> actorsToIgnore;

    private void addActor(T actor){
        actorsList.add(actor);
    }
    private boolean removeActor(T actor){
        return actorsList.remove(actor);
    }

    // Constructor with no actorsToIgnore
    public ActorsRepository(Game myGame, Class<T> classOfType){
       this(myGame, classOfType, new ArrayList<>());
    }

    public ActorsRepository(Game myGame, Class<T> classOfType, ArrayList<T> actorsToIgnore){
        super(myGame);
        this.classOfType = classOfType;
        this.actorsToIgnore = actorsToIgnore;
        this.actorsList = new ArrayList<>();
        this.isInAction = false;

    }

    // Method to be executed when OnActorValid event is triggered
    public void onActorValid(OnActorValid onActorValid){

        try{
            // Try to cast the actor to the repository's type
            T actorToAdd = classOfType.cast(onActorValid.getValidActor());

            // Ensure that the actor is not in the actorsToIgnore list
            for(T ignoredActor:actorsToIgnore){
                if(ignoredActor == actorToAdd){
                    return;
                }
            }
            // Try to cast the actor to the repository's type
            addActor(actorToAdd);
        }catch(ClassCastException e){
            // failed to cast
        }

    }

    // Method to be executed when OnActorInvalid event is triggered
    public void onActorInvalid(OnActorInvalid onActorInvalid){
        // Try to cast the actor to the repository's type
        try{
            removeActor(classOfType.cast(onActorInvalid.getInvalidActor()));
        }catch(ClassCastException e){
            // failed to cast
        }

    }

    // Return a copy of the actors list
    public ArrayList<T> getActors(){
        return new ArrayList<>(actorsList);
    }

    // Method for stopping the repository's work (unsubscribing to the observables)
    // Must be called when the last user-object goes invalid
    public void end(){
        if(!this.isInAction){return;}

      invalidate();
    }

    // Method for starting the repository's work (subscribing to the observables)
    // Must be called in order start use the repository

    public void start(){
        if(this.isInAction){return;}

        subscribeToGameObservable(myGame.getOnActorValidObservable().subscribe(this::onActorValid));
        subscribeToGameObservable(myGame.getOnActorInvalidObservable().subscribe(this::onActorInvalid));

        this.isInAction = true;

    }

    public int getSize(){
        return actorsList.size();
    }

    public boolean isEmpty(){
        return(getSize() == 0);
    }

}
