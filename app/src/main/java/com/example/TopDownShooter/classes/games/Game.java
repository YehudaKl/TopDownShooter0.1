package com.example.TopDownShooter.classes.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnPreUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStateChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.events.UIEvents.OnShoot;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.gameObjects.physics.PhysicsManager;
import com.example.TopDownShooter.classes.systems.GameLoop;
import com.example.TopDownShooter.dataTypes.enums.GameState;

import java.util.Timer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

/**
 * A Game is a generic class for all games that can be played by the user.
 * A Game class is a view that the activity of the game uses as its content view.
 * Generally different classes of games function as different game modes that can be played
 */
public abstract class Game extends SurfaceView implements SurfaceHolder.Callback{


    private PublishSubject<OnUpdate> onUpdateObservable;
    private PublishSubject<OnPreUpdate> onPreUpdateObservable;
    private PublishSubject<OnDraw> onDrawObservable;
    private PublishSubject<OnGameStart> onGameStartObservable;
    private PublishSubject<OnGameEnd> onGameEndObservable;
    private PublishSubject<OnGameStateChanged> onGameStatusChangedObservable;

    private ReplaySubject<OnActorValid> onActorValidObservable;
    private ReplaySubject<OnActorInvalid> onActorInvalidObservable;

    private GameState state;

    private PhysicsManager physicsManager;

    private GameLoop gameLoop;
    private Context context;
    private boolean isDebugging;
    private Timer timer;// A timer for all classes in the game.
    private UpdateTrace updateTrace;


    // Setters and Getters
    public ReplaySubject<OnActorValid> getOnActorValidObservable() {
        return onActorValidObservable;
    }

    public ReplaySubject<OnActorInvalid> getOnActorInvalidObservable() {
        return onActorInvalidObservable;
    }

    public PublishSubject<OnGameStart> getOnGameStartObservable(){
        return onGameStartObservable;
    }

    public PublishSubject<OnGameEnd> getOnGameEndObservable() {
        return onGameEndObservable;
    }

    public PublishSubject<OnGameStateChanged> getOnGameStatusChangedObservable() {
        return onGameStatusChangedObservable;
    }

    public PublishSubject<OnPreUpdate> getOnPreUpdateObservable() {
        return onPreUpdateObservable;
    }

    public PhysicsManager getPhysicsManager(){
        return physicsManager;
    }

    public GameState getState() {
        return state;
    }

    public Timer getTimer(){
        return timer;
    }


    // This method must be used by any UI element that wants to identify the game
    // The UI element pass himself as an argument to the update notify method for its type;
    public UpdateTrace getUpdateTrace(){
        return updateTrace;
    }



    public Game(Context context){
        super(context);
        init();
    }

    public Game(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
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
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(isDebugging){
            drawDebugInformation(canvas);
        }

        onDrawObservable.onNext(new OnDraw(this, canvas));

    }

    public void update(){
        physicsManager.update(gameLoop.getDeltaTime());
        onPreUpdateObservable.onNext(new OnPreUpdate(this, updateTrace));
        updateTrace.deltaTimeNotify(gameLoop);
        onUpdateObservable.onNext(new OnUpdate(this, updateTrace));
    }

    public Observable<OnDraw> getOnDrawObservable(){
        return onDrawObservable;
    }

    public Observable<OnUpdate> getOnUpdateObservable(){
        return onUpdateObservable;
    }


    // This method must be called at the child class not before all objects have been created!!
    // It is recommended to call this method at the end of the constructor
    protected void startGame(){
        this.state = GameState.RUN;
        onGameStartObservable.onNext(new OnGameStart(this));
    }

    protected void endGame(){
        this.state = GameState.PAUSE;
        onGameEndObservable.onNext(new OnGameEnd(this));
    }

    protected void continueGame(){
        this.state = GameState.RUN;
        onGameStatusChangedObservable.onNext(new OnGameStateChanged(this, state));
    }

    protected void pauseGame(){
        this.state = GameState.PAUSE;
        onGameStatusChangedObservable.onNext(new OnGameStateChanged(this, state));
    }


    protected void drawDebugInformation(Canvas canvas){
        drawUPS(canvas);
        drawFPS(canvas);
    }

    // Changes the isDebugging to the given boolean value. Should be used by the child in order to debug
    protected void setIsDebugging(boolean b){
        isDebugging = b;
    }


    private void init(){

        //Get surface holder and add the game class as a callback so the game loop
        // will be able to lock the canvas between frames

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.onUpdateObservable = PublishSubject.create();
        this.onPreUpdateObservable = PublishSubject.create();
        this.onDrawObservable = PublishSubject.create();
        this.onGameStartObservable = PublishSubject.create();
        this.onGameEndObservable = PublishSubject.create();
        this.onGameStatusChangedObservable = PublishSubject.create();
        this.onActorValidObservable = ReplaySubject.create();
        this.onActorInvalidObservable = ReplaySubject.create();

        this.physicsManager = new PhysicsManager(this);

        this.state = GameState.LOAD;

        this.gameLoop = new GameLoop(this, surfaceHolder);
        this.context = getContext();
        this.isDebugging = false;
        this.timer = new Timer();
        this.updateTrace = new UpdateTrace();

    }


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
