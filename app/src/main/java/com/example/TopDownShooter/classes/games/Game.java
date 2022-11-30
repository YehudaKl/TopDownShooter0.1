package com.example.TopDownShooter.classes.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters.Monster;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Hero;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.shooters.Shooter;
import com.example.TopDownShooter.classes.interfaces.GameParticipant;
import com.example.TopDownShooter.classes.systems.Effects.EffectsSystem;
import com.example.TopDownShooter.classes.systems.GameLoop;
import com.example.TopDownShooter.classes.gameObjects.actors.*;
import com.example.TopDownShooter.dataTypes.enums.GameState;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;

/**
 * A Game is a generic class for all games that can be played by the user.
 * A Game class is a view that the activity of the game uses as its content view.
 * Generally different classes of games function as different game modes that can be played
 */
public abstract class Game extends SurfaceView implements SurfaceHolder.Callback{


    private GameLoop gameLoop;
    private EventBus eventBus;
    private EffectsSystem effectsSystem;
    private Context context;
    private boolean isDebugging;
    private GameState gameState;
    protected ArrayList<GameParticipant> gameParticipants;
    protected ArrayList<Actor> actors;
    public Timer timer;// A timer for all classes in the game.


    public Game(Context context, ArrayList<Actor> actors){
        super(context);

        //Get surface holder and add the game class as a callback so the game loop
        // will be able to lock the canvas between frames

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.gameLoop = new GameLoop(this, surfaceHolder);
        this.eventBus = EventBus.getDefault();
        this.effectsSystem = new EffectsSystem(this);
        this.context = getContext();
        this.isDebugging = false;
        this.gameState = GameState.LOAD;
        this.actors = actors;
        this.gameParticipants = new ArrayList<>();
        this.timer = new Timer();




    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        // TODO...

        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(isDebugging){
            drawDebugInformation(canvas);
        }
        for(Actor actor: actors){
            actor.draw(canvas);
        }

    }

    public void update(){
        if(actors == null){return;}

        for(Iterator<Actor> iterator = actors.iterator(); iterator.hasNext();){
            Actor actor = iterator.next();

            if(actor.getIsValid() == true){
                actor.update();
            }
            else{// Deleting invalid actors
                iterator.remove();
            }
        }



    }

    public EventBus getEventBus(){
        return eventBus;
    }

    //* I tried to do the following methods generic but it was to hard for me :(
    // The following methods return a list of a sub class of Actor from the actors list without the actors in the toIgnore list
    // If the toIgnore is not needed an empty array should be passed as an argument
    // ------------------------------------------------------------------
    public ArrayList<Actor> getActors(ArrayList<Actor> toIgnore){

        ArrayList<Actor> toReturn = new ArrayList();
        for(Actor actor: actors){
            if(!toIgnore.contains(actor)){
                toReturn.add(actor);
            }

        }

        return toReturn;
    }

    public ArrayList<Pawn> getPawns(ArrayList<Pawn> toIgnore){
        ArrayList<Pawn> toReturn = new ArrayList();
        for(Actor actor: actors){
            if(actor instanceof Pawn && !toIgnore.contains(actor)){
                toReturn.add((Pawn)actor);
            }
        }

        return toReturn;
    }

    public ArrayList<Character> getCharacters(ArrayList<Character> toIgnore){
        ArrayList<Character> toReturn = new ArrayList();
        for(Actor actor: actors){
            if(actor instanceof Character && !toIgnore.contains(actor)){
                toReturn.add((Character) actor);
            }
        }

        return toReturn;
    }

    public ArrayList<Monster> getMonsters(ArrayList<Monster> toIgnore){
        ArrayList<Monster> toReturn = new ArrayList();
        for(Actor actor: actors){
            if(actor instanceof Monster && !toIgnore.contains(actor)){
                toReturn.add((Monster)actor);
            }
        }

        return toReturn;
    }

    public ArrayList<Shooter> getShooters(ArrayList<Shooter> toIgnore){
        ArrayList<Shooter> toReturn = new ArrayList();
        for(Actor actor: actors){
            if(actor instanceof Shooter && !toIgnore.contains(actor)){
                toReturn.add((Shooter)actor);
            }
        }

        return toReturn;
    }

    public ArrayList<Hero> getHeroes(ArrayList<Hero> toIgnore){
        ArrayList<Hero> toReturn = new ArrayList();
        for(Actor actor: actors){
            if(actor instanceof Hero && !toIgnore.contains(actor)){
                toReturn.add((Hero)actor);
            }
        }

        return toReturn;
    }

    //--------------------------------------------------------------------

    public ArrayList<Character> getTeam(Team team){
        ArrayList<Character> characters = getCharacters(new ArrayList());

        for (Iterator<Character> iterator = characters.iterator(); iterator.hasNext();) {
            Character character = iterator.next();
            if(character.getTeam().ID != team.ID) {
                iterator.remove();
            }
        }

        return characters;
    }



    public EffectsSystem getEffectsSystem(){
        return effectsSystem;
    }



    // This method must me called at the child class not before all objects have been created!!
    // It is recommended to call this method at the end of the constructor
    protected void startGame(){
        if(gameParticipants == null){return;}

        for(GameParticipant gm: gameParticipants){
            gm.onGameStart();
        }
    }

    protected void pauseGame(){
        for(GameParticipant gm: gameParticipants){
            gm.onGamePause();
        }
    }

    //--------------------------------------------------------

    // Function that adds a new actor to the game. Should be used by subclasses during the game.
    // If the addition files, the function returns false
    public boolean signNewActor(Actor actor){
        actors.add(actor);
        return true;
    }


    protected boolean signNewGameParticipant(GameParticipant gameParticipant){
        gameParticipants.add(gameParticipant);
        return true;
    }


    protected void drawDebugInformation(Canvas canvas){
        drawUPS(canvas);
        drawFPS(canvas);
    }

    // Changes the isDebugging to the given boolean value. Should be used by the child in order to debug
    protected void setIsDebugging(boolean b){
        isDebugging = b;
    }



    // Abstract method that each game should implement in order to define how characters are divided
    // to teams. This method is very imported because it defines a major part of the game's rules and mode.
    // For instance a FFA game should give a unique team for each character.
    public abstract Team findMyTeam(Character character);

    // Each child class must be able to return a map of its teams.
    // Note! it's up to the child class to implement the team management
    public abstract HashMap<String, Team> getTeamsMap();

    // Debug methods -------------------------------------------------------
    private void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.mangeta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);

    }

    private void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.mangeta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);

    }

    //-------------------------------------------------------------------




}
