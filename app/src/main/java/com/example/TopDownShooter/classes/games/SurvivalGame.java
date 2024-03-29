package com.example.TopDownShooter.classes.games;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Survivor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters.Zombie;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.gameObjects.players.AIPlayers.ZombiePlayer;
import com.example.TopDownShooter.classes.gameObjects.players.userPlayers.ShooterUserPlayer;
import com.example.TopDownShooter.classes.gameObjects.players.userPlayers.SurvivorUserPlayer;
import com.example.TopDownShooter.classes.systems.Map;
import com.example.TopDownShooter.dataTypes.Position;

import java.util.HashMap;

/**
 *
 */

public class SurvivalGame extends Game implements TeamsGame{

    private HashMap<String, Team> teams;
    private Survivor survivor;
    private SurvivorUserPlayer survivorPlayer;
    private ZombiePlayer zombiesPlayer;



    public SurvivalGame(Context context){
        super(context);
        init();
    }

    public SurvivalGame(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }


    private void init() {

        setIsDebugging(true);

        teams = initializeTeams();
        survivorPlayer = new SurvivorUserPlayer(this);
        zombiesPlayer = new ZombiePlayer(this);


        survivor = new Survivor(this, new Position(400, 400));
        survivor.setOwner(survivorPlayer);
        spawnZombie(new Position(800, 800));
        spawnZombie(new Position(1000, 1000));
    }

    @Override
    protected Map loadMap() {

        Bitmap mapOrigin = BitmapFactory.decodeResource(getResources(), R.drawable.map1);
        int mapWidth = mapOrigin.getWidth();
        int mapHeight = mapOrigin.getHeight();
        return new Map(this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map1), mapWidth/4, mapHeight/4, true));

    }

    @Override
    public Team generateMeTeam(Pawn pawn){

        if(pawn instanceof Shooter){
            return teams.get("Player");
        }
        else{
            return teams.get("Zombies");
        }
    }




    public Survivor getHero(){return this.survivor;}

    @Override
    protected Actor getCurrentThemeActor() {
        return survivor;
    }

    @Override
    protected int getMapResourceId() {
        return R.drawable.map1;
    }

    // The function adds a new enemy to the enemies array and to the actors-array of the parent class
    private void spawnZombie(Position position){

        Zombie zombie = new Zombie(this, position);
        zombie.setOwner(zombiesPlayer);
    }




    private HashMap<String, Team> initializeTeams(){
        HashMap<String, Team> toReturn = new HashMap();

        toReturn.put("Player", new Team(1, new Paint(R.color.green)));
        toReturn.put("Zombies", new Team(2, new Paint(R.color.red)));

        return toReturn;
    }


}
