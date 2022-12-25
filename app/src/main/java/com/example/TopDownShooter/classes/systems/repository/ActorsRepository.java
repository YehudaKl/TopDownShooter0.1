package com.example.TopDownShooter.classes.systems.repository;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;

import java.util.ArrayList;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * ActorsRepository is a repository that holds references to all valid actor from the same type in a game.
 * Each actor that needs a reference to other actor must get it from a repository
 */
public class ActorsRepository <T extends Actor>{

    private final Game myGame;
    private final ArrayList<T> actorsList;

    private void addActor(T actor){
        actorsList.add(actor);
    }
    private boolean removeActor(T actor){
        return actorsList.remove(actor);
    }

    public ActorsRepository(Game myGame){
       this(myGame, new ArrayList<>());
    }



    public ActorsRepository(Game myGame, ArrayList<T> actorsList){
        this.myGame = myGame;
        this.actorsList = actorsList;

        // Subscribing to the needed observables
        myGame.getOnActorValidObservable().subscribe((Consumer<OnActorValid>) onActorValid -> onActorValid(onActorValid));
        myGame.getOnActorInvalidObservable().subscribe((Consumer<OnActorInvalid>) onActorInvalid -> onActorInvalid(onActorInvalid));

    }

    // Method to be executed when OnActorValid event is triggered
    public void onActorValid(OnActorValid onActorValid){
        // Try to cast the actor to the repository's type
        try{
            addActor((T)onActorValid.getValidActor());
        }catch(ClassCastException e){

        }

    }

    // Method to be executed when OnActorInvalid event is triggered
    public void onActorInvalid(OnActorInvalid onActorInvalid){
        // Try to cast the actor to the repository's type
        try{
            removeActor((T)onActorInvalid.getInvalidActor());
        }catch(ClassCastException e){

        }

    }

    // Return a copy of the actors list
    public ArrayList<T> getActors(){
        return new ArrayList<>(actorsList);
    }

}
