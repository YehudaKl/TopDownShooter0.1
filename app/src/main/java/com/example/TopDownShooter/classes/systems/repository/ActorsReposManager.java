package com.example.TopDownShooter.classes.systems.repository;

import com.example.TopDownShooter.classes.events.surveys.Survey;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.systems.repository.ActorsRepository;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The actors manager class injects the dependencies of the actors repo into the game class.
 * Objects that needs reference to all valid actors of a specific type permanently can use the actorsReposManager
 * instead of using the survey system each time they need a reference.
 */
public class ActorsReposManager {

    private final Game myGame;
    private final HashMap<String, ActorsRepository<? extends Actor>> repos;


    public ActorsReposManager(Game myGame){
        this.myGame = myGame;
        this.repos = new HashMap<>();


    }

    // Method for stating a repository associated with a name.
    public <T extends Actor> void startRepo(String name){

        ArrayList<T> initialActors = new ArrayList<>();
        myGame.getSurveyPublisher().onNext(new Survey<T>(myGame, initialActors, null, obj -> true));
        //repos.put(name, new ActorsRepository<T>(myGame, initialActors,));

    }

    // Method for getting your repo
    public ArrayList<? extends  Actor> getActors(String name){
        ActorsRepository<? extends Actor> repository = repos.get(name);
        if(repository != null){
            return repository.getActors();
        }

        return null;
    }

    // Method for removing the repository if there is no more need for it.
    // It is important to call this method in order to reduce the amount of active repos;
    public void endRepo(String name){
        repos.remove(name);
    }

    public <T extends Integer> T foo(T number){
         return number;
    }
}
