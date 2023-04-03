package com.example.TopDownShooter.classes.systems;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnDraw;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnPreUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStateChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorInvalid;
import com.example.TopDownShooter.classes.events.actorValidationEvents.OnActorValid;
import com.example.TopDownShooter.classes.events.physicalEvents.OnCollisionEnd;
import com.example.TopDownShooter.classes.events.physicalEvents.OnCollisionStart;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

/**
 * The ObservableProvider holds the observables and is responsible for providing them
 */
public class ObservableProvider {

    private final PublishSubject<OnUpdate> onUpdateObservable;
    private final PublishSubject<OnPreUpdate> onPreUpdateObservable;
    private final PublishSubject<OnDraw> onDrawObservable;
    private final PublishSubject<OnGameStart> onGameStartObservable;
    private final PublishSubject<OnGameEnd> onGameEndObservable;
    private final PublishSubject<OnGameStateChanged> onGameStatusChangedObservable;

    private final PublishSubject<OnCollisionStart> onCollisionStartObservable;
    private final PublishSubject<OnCollisionEnd> onCollisionEndObservable;

    private final ReplaySubject<OnActorValid> onActorValidObservable;
    private final ReplaySubject<OnActorInvalid> onActorInvalidObservable;

    public PublishSubject<OnUpdate> getOnUpdateObservable() {
        return onUpdateObservable;
    }

    public PublishSubject<OnPreUpdate> getOnPreUpdateObservable() {
        return onPreUpdateObservable;
    }

    public PublishSubject<OnDraw> getOnDrawObservable() {
        return onDrawObservable;
    }

    public PublishSubject<OnGameStart> getOnGameStartObservable() {
        return onGameStartObservable;
    }

    public PublishSubject<OnGameEnd> getOnGameEndObservable() {
        return onGameEndObservable;
    }

    public PublishSubject<OnGameStateChanged> getOnGameStatusChangedObservable() {
        return onGameStatusChangedObservable;
    }

    public PublishSubject<OnCollisionStart> getOnCollisionStartObservable() {
        return onCollisionStartObservable;
    }

    public PublishSubject<OnCollisionEnd> getOnCollisionEndObservable() {
        return onCollisionEndObservable;
    }

    public ReplaySubject<OnActorValid> getOnActorValidObservable() {
        return onActorValidObservable;
    }

    public ReplaySubject<OnActorInvalid> getOnActorInvalidObservable() {
        return onActorInvalidObservable;
    }


    public ObservableProvider(){
        this.onUpdateObservable = PublishSubject.create();
        this.onPreUpdateObservable = PublishSubject.create();
        this.onDrawObservable = PublishSubject.create();
        this.onGameStartObservable = PublishSubject.create();
        this.onGameEndObservable = PublishSubject.create();
        this.onGameStatusChangedObservable = PublishSubject.create();
        this.onCollisionStartObservable = PublishSubject.create();
        this.onCollisionEndObservable = PublishSubject.create();
        this.onActorValidObservable = ReplaySubject.create();
        this.onActorInvalidObservable = ReplaySubject.create();
    }



    public enum EventType{
        ON_ACTOR_INVALID,
        ON_ACTOR_VALID,
        ON_DRAW,
        ON_PRE_UPDATE,
        ON_UPDATE,
        ON_GAME_STATUS_CHANGED,
        ON_COLLISION_END,
        ON_COLLISION_START,
        ON_GAME_END,
        ON_GAME_START
    }
}
