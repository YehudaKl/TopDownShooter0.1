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
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameStatusEvents.GameStatus;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStatusChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.events.surveys.Survey;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
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

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * A Game is a generic class for all games that can be played by the user.
 * A Game class is a view that the activity of the game uses as its content view.
 * Generally different classes of games function as different game modes that can be played
 */
public abstract class Game extends SurfaceView implements SurfaceHolder.Callback{


    private PublishSubject<OnUpdate> onUpdateObservable;
    private PublishSubject<OnDraw> onDrawObservable;
    private PublishSubject<OnGameStart> onGameStartObservable;
    private PublishSubject<OnGameEnd> onGameEndObservable;
    private PublishSubject<OnGameStatusChanged> onGameStatusChangedObservable;
    private PublishSubject<Survey<? extends GameObject>> onSurveyObservable;


    private GameLoop gameLoop;
    private EffectsSystem effectsSystem;
    private Context context;
    private boolean isDebugging;
    private GameState gameState;

    public Timer timer;// A timer for all classes in the game.


    public Game(Context context){
        super(context);

        //Get surface holder and add the game class as a callback so the game loop
        // will be able to lock the canvas between frames

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.onUpdateObservable = PublishSubject.create();
        this.onDrawObservable = PublishSubject.create();
        this.onGameStartObservable = PublishSubject.create();
        this.onGameEndObservable = PublishSubject.create();
        this.onGameStatusChangedObservable = PublishSubject.create();
        this.onSurveyObservable = PublishSubject.create();

        this.gameLoop = new GameLoop(this, surfaceHolder);
        this.effectsSystem = new EffectsSystem(this);
        this.context = getContext();
        this.isDebugging = false;
        this.gameState = GameState.LOAD;
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

        onDrawObservable.onNext(new OnDraw(this, canvas));

    }

    public void update(){

        onUpdateObservable.onNext(new OnUpdate(this, gameLoop.getDeltaTime()));
    }

    public Observable<OnDraw> getOnDrawObservable(){
        return onDrawObservable;
    }

    public Observable<OnUpdate> getOnUpdateObservable(){
        return onUpdateObservable;
    }




    public EffectsSystem getEffectsSystem(){
        return effectsSystem;
    }



    // This method must me called at the child class not before all objects have been created!!
    // It is recommended to call this method at the end of the constructor
    protected void startGame(){
        onGameStartObservable.onNext(new OnGameStart(this));
    }

    protected void endGame(){
        onGameEndObservable.onNext(new OnGameEnd(this));
    }

    protected void continueGame(){
        onGameStatusChangedObservable.onNext(new OnGameStatusChanged(this, GameStatus.RUN));
    }

    protected void pauseGame(){
        onGameStatusChangedObservable.onNext(new OnGameStatusChanged(this, GameStatus.PAUSED));
    }

    // A getter for Surveys observable in order post surveys by other classes
    // If the provided type dose not exist in game, the function will return null !
    public PublishSubject<Survey<? extends GameObject>> getSurveyPublisher(){
        return onSurveyObservable;
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
