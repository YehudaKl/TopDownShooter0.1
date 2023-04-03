package com.example.TopDownShooter.classes.games;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.DrawableRes;
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
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.events.physicalEvents.OnCollisionEnd;
import com.example.TopDownShooter.classes.events.physicalEvents.OnCollisionStart;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.systems.GameLoop;
import com.example.TopDownShooter.classes.systems.Map;
import com.example.TopDownShooter.classes.systems.ObservableProvider;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.enums.GameState;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import java.lang.ref.SoftReference;
import java.util.Timer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import shiffman.box2d.Box2DProcessing;

/**
 * A Game is a generic class for all games that can be played by the user.
 * A Game class is a view that the activity of the game uses as its content view.
 * Generally different classes of games function as different game modes that can be played
 *
 * The game default state after loading is paused! the game has to be resumed by the parent activity
 */
public abstract class Game extends SurfaceView implements SurfaceHolder.Callback, ContactListener{

    private ObservableProvider observableProvider;
    private GameState state;
    private Map map;
    private Box2DProcessing physicsManager;
    private LoadingListener loadingListener;
    private GameLoop gameLoop;
    private boolean isDebugging;
    private Timer timer;// A timer for all classes in the game.
    private SoundPool soundPool;
    private UpdateTrace updateTrace;

    /**
     * Listener for the game's loading state
     */
    public interface LoadingListener{

        void LoadingStarted();
        void LoadingEnded();
    }

    public Game(Context context) {
        super(context);
        init();

    }

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
        switch (state){
            case LOADING:
                loadingListener.LoadingStarted();
                break;
            case ENDED:
                loadingListener.LoadingEnded();
                break;
            default:
                break;
        }

    }

    public ObservableProvider getObservableProvider(){return observableProvider;}

    public Box2DProcessing getPhysicsManager(){
        return physicsManager;
    }

    public GameState getState() {
        return state;
    }

    public Timer getTimer(){
        return timer;
    }

    public Map getMap() {
        return map;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    // This method must be used by any UI element that wants to identify the game
    // The UI element pass himself as an argument to the update notify method for its type;
    public UpdateTrace getUpdateTrace(){
        return updateTrace;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        load();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    // Physical -------------------------------------------
    @Override
    public void beginContact(Contact contact) {
        observableProvider.getOnCollisionStartObservable().onNext(new OnCollisionStart(this, contact));
    }

    @Override
    public void endContact(Contact contact) {
        observableProvider.getOnCollisionEndObservable().onNext(new OnCollisionEnd(this, contact));
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    //------------------------------------------------------

    @Override
    public void draw(Canvas canvas) {
        if(canvas == null){// TODO: figure out way it is null
            return;
        }

        super.draw(canvas);

        if(map != null){
            map.draw(canvas);
        }
        if(isDebugging){
            drawDebugInformation(canvas);
        }

        observableProvider.getOnDrawObservable().onNext(new OnDraw(this, canvas));
    }

    public void update(){
        physicsManager.step(gameLoop.getDeltaTime());
        observableProvider.getOnPreUpdateObservable().onNext(new OnPreUpdate(this, updateTrace));
        updateTrace.deltaTimeNotify(gameLoop);
        observableProvider.getOnUpdateObservable().onNext(new OnUpdate(this, updateTrace));

        if(map != null){
            map.updateMap(getCurrentThemeActor());
        }

    }

    // This method must be called at the child class not before all objects have been created!!
    // It is recommended to call this method at the end of the constructor


    public void endGame(){
        gameLoop.stopLoop();
        this.state = GameState.ENDED;
        observableProvider.getOnGameEndObservable().onNext(new OnGameEnd(this));
    }

    public void resumeGame(){
        if(gameLoop.getState() == Thread.State.TERMINATED){
            gameLoop = new GameLoop(this, getHolder());
        }
        gameLoop.startLoop();
        this.state = GameState.RUNNING;
        observableProvider.getOnGameStatusChangedObservable().onNext(new OnGameStateChanged(this, state));
    }

    public void pauseGame(){
        gameLoop.stopLoop();
        this.state = GameState.PAUSED;
        observableProvider.getOnGameStatusChangedObservable().onNext(new OnGameStateChanged(this, state));
    }


    protected void drawDebugInformation(Canvas canvas){
        drawUPS(canvas);
        drawFPS(canvas);
    }

    // Changes the isDebugging to the given boolean value. Should be used by the child in order to debug
    protected void setIsDebugging(boolean b){
        isDebugging = b;
    }

    // Method for loading all needed components and data

    private void init(){
        this.state = GameState.INIT;
        //Get surface holder and add the game class as a callback so the game loop
        // will be able to lock the canvas between frames

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.observableProvider = new ObservableProvider();

        this.physicsManager = new Box2DProcessing();
        // Setting a world with no gravity
        this.physicsManager.createWorld(new Vec2(0, 0));
        this.physicsManager.setContactListener(this);


        this.gameLoop = new GameLoop(this, surfaceHolder);
        this.isDebugging = false;
        this.timer = new Timer();
        this.soundPool = generateSoundPool();
        this.updateTrace = new UpdateTrace();

    }

    private void load(){
        this.state = GameState.LOADING;
        this.loadingListener.LoadingStarted();
        this.map = loadMap();
        // Calls draw once! to prove successful loading
        Canvas canvas = getHolder().lockCanvas();
        if(canvas != null){
            draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }

        this.state = GameState.LOADED;
        observableProvider.getOnGameStartObservable().onNext(new OnGameStart(this));

        if(loadingListener != null){
            loadingListener.LoadingEnded();
        }
    }

    protected abstract Map loadMap();
    protected abstract Actor getCurrentThemeActor();

    protected abstract int getMapResourceId();

    private SoundPool generateSoundPool(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                .build();
        SoundPool soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();
        return  soundPool;
    }


    // Debug methods -------------------------------------------------------
    private void drawUPS(@NonNull Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());

        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.mangeta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);

    }

    private void drawFPS(@NonNull Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());

        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.mangeta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);

    }



    //-------------------------------------------------------------------




}
