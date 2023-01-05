package com.example.TopDownShooter.classes.gameObjects.actors.pawns;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStatusChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;
;import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * A Pawn is an actor that can be controlled by a player or an AI.
 */
public abstract class Pawn extends Actor{


    protected Player owner;
    protected Vector velocity;
    private CompositeDisposable disposableContainer;

    public Pawn(Game myGame, Position initPosition, int resourceId){
        this(myGame, initPosition, resourceId, 0);

    }

    // Constructor for with direction for pawns that has to be initialized to current direction.
    public Pawn(Game myGame, Position initPosition, int resourceId, double direction){
        super(myGame, initPosition, resourceId, direction);

        this.velocity = new Vector(0, 0);
        this.disposableContainer = new CompositeDisposable();


        disposableContainer.add(myGame.getOnUpdateObservable().subscribe(this::onUpdate));
        disposableContainer.add(myGame.getOnGameStartObservable().subscribe(this::onGameStart));
        disposableContainer.add(myGame.getOnGameEndObservable().subscribe(this::onGameEnd));
        disposableContainer.add(myGame.getOnGameStatusChangedObservable().subscribe(this::onGameStatusChanged));
    }

    @Override
    public void invalidate(){
        super.invalidate();
        owner.invalidate();

        disposableContainer.clear();


    }

    public void onUpdate(OnUpdate onUpdate){
        update(onUpdate.getUpdateTrace());
    }

    protected void update(UpdateTrace updateTrace){
        if(owner == null){return;}
        owner.updatePawn(updateTrace);
    }


    public void onGameStart(OnGameStart onGameStart){
        owner.onGameStart(onGameStart);
    }

    public void onGameEnd(OnGameEnd onGameEnd){
        owner.onGameEnd(onGameEnd);
    }

    public void onGameStatusChanged(OnGameStatusChanged onGameStatusChanged){
        owner.onGameStatusChanged(onGameStatusChanged);
    }

    // Method for a a pawn that was added after the game has already stared.
    // Must be called by the creator of the pawn
    public void onJoinedGame(){
        owner.onJoinedGame();
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public double getDirection() {
        return direction;
    }

    public Vector getVelocity() {
        return velocity;
    }






}
