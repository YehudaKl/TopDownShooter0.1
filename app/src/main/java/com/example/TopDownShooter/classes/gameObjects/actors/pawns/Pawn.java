package com.example.TopDownShooter.classes.gameObjects.actors.pawns;

import android.widget.Toast;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStateChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.games.TeamsGame;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;
;

/**
 * A Pawn is an actor that can be controlled by a player or an AI.
 */
public abstract class Pawn extends Actor{

    protected Player owner;
    protected Team myTeam;
    protected PawnMotionState motionState;

    public void setTeam(Team team){
        this.myTeam = team;
    }

    public Team getTeam() {
        return myTeam;
    }

    public void setMotionState(PawnMotionState motionState) {
        this.motionState = motionState;
    }

    public PawnMotionState getMotionState() {
        return motionState;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    // Constructor for with direction for pawns that has to be initialized to current direction.
    public Pawn(Game myGame){
        super(myGame);

        this.motionState = PawnMotionState.MOVING;

        subscribeToObservable(myGame.getObservableProvider().getOnUpdateObservable().subscribe(this::onUpdate));
        subscribeToObservable(myGame.getObservableProvider().getOnGameStartObservable().subscribe(this::onGameStart));
        subscribeToObservable(myGame.getObservableProvider().getOnGameStatusChangedObservable().subscribe(this::onGameStatusChanged));
    }


    public void onUpdate(OnUpdate onupdate){

        if(motionState == PawnMotionState.FROZE){ return;}

        update(onupdate.getUpdateTrace());
    }

    protected void update(UpdateTrace updateTrace){
        if(owner == null || !owner.getIsValid()){
            return;
        }

        owner.updatePawn(this, updateTrace);
    }


    public void onGameStart(OnGameStart onGameStart){
        if(myGame instanceof TeamsGame){

            myTeam = ((TeamsGame) myGame).generateMeTeam(this);
        }
    }


    public void onGameStatusChanged(OnGameStateChanged onGameStateChanged) {
    }

    public abstract int getSpeed();
    public abstract void updateDirection(float direction);
    public abstract void updatePosition(Position position);
    public abstract void updateVelocity(Vector velocity);
    public abstract Vector viewVelocity();










}
