package com.example.TopDownShooter.classes.games;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Hero;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters.Zombie;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.players.AIPlayers.ZombiePlayer;
import com.example.TopDownShooter.classes.gameObjects.players.userPlayers.ShooterUserPlayer;
import com.example.TopDownShooter.classes.gameObjects.players.userPlayers.UserPlayer;
import com.example.TopDownShooter.dataTypes.Position;

import java.util.HashMap;

/**
 * TODO(rules for the game)
 */

public class SurvivalGame extends Game{

    private  HashMap<String, Team> teams;

    private Hero hero;


    public SurvivalGame(Context context){
        super(context);
    }

    public SurvivalGame(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        super.surfaceCreated(surfaceHolder);

        teams = initializeTeams();



        this.hero = new Hero(this, new Position(400, 400));
        hero.setOwner(new ShooterUserPlayer(this, hero));

        setIsDebugging(true);

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


    public Hero getHero(){return this.hero;}

    @Override
    protected void startGame() {
        super.startGame();
        spawnZombie(new Position(100, 100));
    }

    // The function adds a new enemy to the enemies array and to the actors-array of the parent class
    private void spawnZombie(Position position){
        // TODO decide how to spawn the zombie better

        Zombie zombie = new Zombie(this, position);

        zombie.setOwner(new ZombiePlayer(this, zombie));
        zombie.onJoinedGame();


    }




    private HashMap<String, Team> initializeTeams(){
        HashMap<String, Team> toReturn = new HashMap();

        toReturn.put("Player", new Team(1, new Paint(R.color.green)));
        toReturn.put("Zombies", new Team(2, new Paint(R.color.red)));

        return toReturn;
    }
}
