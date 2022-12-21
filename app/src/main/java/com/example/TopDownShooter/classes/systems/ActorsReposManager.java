package com.example.TopDownShooter.classes.systems;

import com.example.TopDownShooter.classes.events.surveys.Survey;
import com.example.TopDownShooter.classes.events.surveys.SurveyContract;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The actors manager class injects the dependencies of the actors repo into the game class.
 * Objects that needs reference to all valid actors of a specific type permanently can use the actorsReposManager
 * instead of using the survey system each time they need a reference.
 */
public class ActorsReposManager {

    private final Game myGame;
    private final HashMap<String, ActorsRepository<?>> repos;


    public ActorsReposManager(Game myGame){
        this.myGame = myGame;
        this.repos = new HashMap<>();


    }

    // Method for stating a repository associated with a name.
    public <T extends Actor> void startRepo(String name, T generator){

        ArrayList<T> initialActors = new ArrayList<>();
        myGame.getSurveyPublisher().onNext(new Survey<T>(myGame, initialActors, generator, obj -> true));
        repos.put(name, new ActorsRepository<T>(myGame, initialActors));

    }

    // Method for getting your repo;
    public ArrayList<?> getMyRepo(String name){
        return repos.get(name).getActors();
    }

    // Method for removing the repository if there is no more need for it.
    // It is important to call this method in order to reduce the amount of active repos;
    public void endRepo(String name){
        repos.remove(name);
    }
}
