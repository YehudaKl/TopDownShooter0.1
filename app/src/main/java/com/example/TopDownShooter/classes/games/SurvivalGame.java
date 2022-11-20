package com.example.TopDownShooter.classes.games;

import android.content.Context;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.UI.Joystick;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Hero;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters.Zombie;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.players.AIPlayers.ZombiePlayer;
import com.example.TopDownShooter.classes.gameObjects.players.UserPlayer;
import com.example.TopDownShooter.dataTypes.Position;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO(rules for the game)
 */

public class SurvivalGame extends Game{

    private  HashMap<String, Team> teams;


    private Hero hero;
    private Joystick joystick;

    private int joystickPointerId;




    public SurvivalGame(Context context){// *Note the survival game dose not need pre given actors

        super(context, new ArrayList<Actor>());

        teams = initializeTeams();

        this.joystick = new Joystick(this, new Position(150, 950), 70, 40);
        signNewActor(joystick);



        this.hero = new Hero(this, new Position(400, 400));
        hero.setOwner(new UserPlayer(this, hero, joystick));
        signNewActor(hero);
        signNewGameParticipant(hero);

        setIsDebugging(true);

        //temp---------------------
            this.joystickPointerId = 0;
        //-------------------------

        spawnZombie(new Position(150, 150));

        startGame();
    }

    @Override
    public Team findMyTeam(Character character){

        if(character instanceof Shooter){
            return teams.get("Player");
        }
        else{
            return teams.get("Zombies");
        }
    }

    @Override
    public HashMap<String, Team> getTeamsMap() {
        return teams;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        // TODO replace with full event-GUI system(without the joystick)

        //Handle touch event actions
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(joystick.getIsPressed()){
                    //numberOfSpellsToCast++;
                }
                else if(joystick.isPressed((double)event.getX(), (double)event.getY())){
                    // Joystick is pressed in this event -> setIsPressed(true) and store ID
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }
                else{
                    // Joystick was not pressed previously and is not pressed at this event -> cast spell
                    //numberOfSpellsToCast++;
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                // Joystick was pressed previously and now is moved
                if(joystick.getIsPressed()){
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                    return true;
                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // Joystick was let got of -> setIsPressed(false) and resetActuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                    return true;
                }

        }
        return super.onTouchEvent(event);

    }

    public Joystick getJoystick() {
        return joystick;
    }
    public Hero getHero(){return this.hero;}




    // The function adds a new enemy to the enemies array and to the actors-array of the parent class
    private void spawnZombie(Position position){
        Zombie zombie = new Zombie(this, position);

        zombie.setOwner(new ZombiePlayer(this, zombie));
        signNewActor(zombie);
        signNewGameParticipant(zombie);
    }




    private HashMap<String, Team> initializeTeams(){
        HashMap<String, Team> toReturn = new HashMap();

        toReturn.put("Player", new Team(1, new Paint(R.color.green)));
        toReturn.put("Zombies", new Team(2, new Paint(R.color.red)));

        return toReturn;
    }
}
