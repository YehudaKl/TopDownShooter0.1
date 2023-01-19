package com.example.TopDownShooter.classes.events.physicalEvents;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.TopDownShooter.classes.events.OnEvent;
import com.example.TopDownShooter.classes.games.Game;

public class OnCollision extends OnEvent {

    private final CollisionInfo collisionInfo;

    public CollisionInfo getCollisionInfo(){
        return collisionInfo;
    }

    public OnCollision(Game game, CollisionInfo collisionInfo) {
        super(game);
        this.collisionInfo = collisionInfo;
    }


}
