package com.example.TopDownShooter.classes.gameObjects.actors.pawns;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStatusChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;
;import io.reactivex.rxjava3.functions.Consumer;

/**
 * A Pawn is an actor that can be controlled by a player or an AI.
 */
public abstract class Pawn extends Actor{


    protected Player owner;
    protected Vector velocity;

    public Pawn(Game myGame, Position initPosition, int resourceId){
        super(myGame, initPosition, resourceId);

        this.owner = owner;
        this.velocity = new Vector(0, 0);
        myGame.getOnUpdateObservable().subscribe((Consumer<OnUpdate>) onUpdate -> onUpdate(onUpdate));
        myGame.getOnGameStartObservable().subscribe((Consumer<OnGameStart>) onGameStart -> onGameStart(onGameStart));
        myGame.getOnGameEndObservable().subscribe((Consumer<OnGameEnd>) onGameEnd -> onGameEnd(onGameEnd));
        myGame.getOnGameStatusChangedObservable().subscribe((Consumer<OnGameStatusChanged>) onGameStatusChanged -> onGameStatusChanged(onGameStatusChanged));
    }

    // Constructor for with direction for pawns that has to be initialized to current direction.
    public Pawn(Game myGame, Position initPosition, int resourceId, double direction){
        super(myGame, initPosition, resourceId,direction);

        this.owner = owner;
        this.velocity = new Vector(0, 0);

        // TODO avoid code duplication
        myGame.getOnUpdateObservable().subscribe((Consumer<OnUpdate>) onUpdate -> onUpdate(onUpdate));
        myGame.getOnGameStartObservable().subscribe((Consumer<OnGameStart>) onGameStart -> onGameStart(onGameStart));
        myGame.getOnGameEndObservable().subscribe((Consumer<OnGameEnd>) onGameEnd -> onGameEnd(onGameEnd));
        myGame.getOnGameStatusChangedObservable().subscribe((Consumer<OnGameStatusChanged>) onGameStatusChanged -> onGameStatusChanged(onGameStatusChanged));
    }

    public void onUpdate(OnUpdate onUpdate){
        update();
    }

    protected void update(){
        owner.updatePawn();
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
