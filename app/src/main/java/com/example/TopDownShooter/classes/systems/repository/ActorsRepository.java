package com.example.TopDownShooter.classes.systems.repository;

import androidx.annotation.NonNull;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * ActorsRepository is a repository that holds references to all valid actor from the same type in a game.
 * Each actor that needs a reference to other actor must get it from a repository.
 * Actors that should be ignored by the repository (for example the objects that uses the repository),
 * must be included in the actors to ignore arrayList.
 * ActorsRepo can be provided by an ActorQualifier in order to specify extra conditions on which class needs
 * to be stored
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
    private final ActorQualifier<T> actorQualifier;

    // Constructor with no actorsToIgnore
    public ActorsRepository(Game myGame, Class<T> classOfType){
       this(myGame, classOfType, new ArrayList<>());
    }

    public ActorsRepository(Game myGame, Class<T> classOfType, ArrayList<T> actorsToIgnore, ActorQualifier<T> actorQualifier){
        super(myGame);
        this.actorQualifier = actorQualifier;
        this.classOfType = classOfType;
        this.actorsToIgnore = actorsToIgnore;
        this.actorsList = new ArrayList<>();
        this.isInAction = false;


    }

    public ActorsRepository(Game myGame, Class<T> classOfType, ArrayList<T> actorsToIgnore){
       this(myGame, classOfType, actorsToIgnore, actor -> {return true;});

    }

    // Adding new actor to the ignored actors.
    // Note! there is no duplication testing so duplicated actor in the ignoredActors list will be checked twice
    public void addActorToIgnore(T actor){
        actorsToIgnore.add(actor);
        // Using the refresh method for making sure that the repository does not already track the newly ignoredActor
        refresh();
    }

    // Method for cleaning the repository from ignored actors that somehow got into the repository
    public void refresh(){
        for(T actor: actorsList){
            for(T ignoredActor: actorsToIgnore){
                if(actor == ignoredActor){
                    actorsList.remove(actor);
                }
            }
        }
    }


    // Method to be executed when OnActorValid event is triggered
    public void onActorValid(OnActorValid onActorValid){

        try{
            // Try to cast the actor to the repository's type
            if(classOfType.isInstance(onActorValid.getValidActor())){
                T actorToAdd = classOfType.cast(onActorValid.getValidActor());

                // Check if the object qualifies the conditions
                if(!actorQualifier.isQualified(actorToAdd)){
                    return;
                }

                // Ensure that the actor is not in the actorsToIgnore list
                for(T ignoredActor:actorsToIgnore){
                    if(ignoredActor == actorToAdd){
                        return;
                    }
                }
                // Try to cast the actor to the repository's type
                addActor(actorToAdd);
            }

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

    // Method that gets the actors from the repository cleaned from provided actors to clean
    public ArrayList<T> getActorsCleaned(ArrayList<T> actorsToClean){
        ArrayList<T> toReturn = new ArrayList();

        for(T actor: actorsList){
            for(T actorToClean: actorsToClean){
                if(actor != actorToClean){
                    toReturn.add(actor);
                }
            }
        }

        return toReturn;
    }


    // Method for starting the repository's work (subscribing to the observables)
    // Must be called in order start use the repository

    public void start(){
        if(this.isInAction){return;}

        subscribeToObservable(myGame.getObservableProvider().getOnActorValidObservable().subscribe(this::onActorValid));
        subscribeToObservable(myGame.getObservableProvider().getOnActorInvalidObservable().subscribe(this::onActorInvalid));

        this.isInAction = true;

    }

    // Method for stopping the repository's work (unsubscribing to the observables)
    // Must be called when the last user-object goes invalid
    public void end(){
        if(!this.isInAction){return;}

      invalidate();
    }

    public int getSize(){
        return actorsList.size();
    }

    public boolean isEmpty(){
        return(getSize() == 0);
    }


    private void addActor(T actor){
        actorsList.add(actor);
    }
    private boolean removeActor(T actor){
        return actorsList.remove(actor);
    }

}
