package com.example.TopDownShooter.classes.gameObjects.actors.pawns;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.assets.Asset;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStateChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.players.Player;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;
;

/**
 * A Pawn is an actor that can be controlled by a player or an AI.
 */
public abstract class Pawn extends Actor{

    public final float MAX_SPEED = 200;//conf
    protected Vector velocity;
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

    public Pawn(Game myGame, Position initPosition, Asset asset){
        this(myGame, initPosition, asset, 0);

    }

    // Constructor for with direction for pawns that has to be initialized to current direction.
    public Pawn(Game myGame, Position initPosition, Asset asset, float direction){
        super(myGame, initPosition, asset, direction);

        this.velocity = new Vector(0, 0);
        this.motionState = PawnMotionState.FROZE;

        subscribeToObservable(myGame.getOnUpdateObservable().subscribe(this::onUpdate));
        subscribeToObservable(myGame.getOnGameStartObservable().subscribe(this::onGameStart));
        subscribeToObservable(myGame.getOnGameEndObservable().subscribe(this::onGameEnd));
        subscribeToObservable(myGame.getOnGameStatusChangedObservable().subscribe(this::onGameStatusChanged));
    }

    public void onUpdate(OnUpdate onupdate){

        if(motionState == PawnMotionState.FROZE){ return;}

        update(onupdate.getUpdateTrace());
    }

    public void onGameStart(OnGameStart onGameStart){
        motionState = PawnMotionState.MOVING;
    }

    public void onGameEnd(OnGameEnd onGameEnd){
        motionState = PawnMotionState.FROZE;
        invalidate();
    }

    public void onGameStatusChanged(OnGameStateChanged onGameStateChanged){
        switch (onGameStateChanged.getNewState()){
            case RUN:
                motionState = PawnMotionState.MOVING;
                break;
            case PAUSE:
                motionState = PawnMotionState.FROZE;
                break;
        }
    }


    // Returns a copy of the direction!
    // For updating the direction the function updateDirection() must be used
    public float viewDirection() {
        return direction;
    }
    // Returns a copy of the velocity!
    // For updating the velocity the function updateVelocity() must be used
    public Vector viewVelocity() {
        if(velocity == null){return null;}
        return new Vector(velocity);
    }

    public void updateDirection(float direction){
        if(motionState == PawnMotionState.FROZE){ return;}

        this.direction = direction;
    }
    public void updateVelocity(Vector velocity){
        // The method does not accept empty vectors
        if(motionState == PawnMotionState.FROZE || velocity.isEmpty()){return;}

        this.velocity = velocity;
    }


    @Override
    public void updatePosition(Position position) {
        // Blocking any position updating while motionState is FROZE
        if(motionState == PawnMotionState.FROZE){
            return;
        }
        super.updatePosition(position);
    }

    // Update the position according to the velocity
    public void step(){
        if(motionState == PawnMotionState.FROZE || velocity == null){return;}

        float newX = velocity.getCoordinateX();
        float newY = velocity.getCoordinateY();
        updatePosition(new Position(newX, newY));
    }


    protected void update(UpdateTrace updateTrace){
        if(owner == null || !owner.getIsValid()){
            return;
        }

        owner.updatePawn(this, updateTrace);
    }









}
