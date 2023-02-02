package com.example.TopDownShooter.classes.gameObjects.players;

import androidx.annotation.NonNull;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.events.GameLoopEvents.OnUpdate;
import com.example.TopDownShooter.classes.events.GameLoopEvents.UpdateTrace;
import com.example.TopDownShooter.classes.events.GameStatusEvents.OnGameStateChanged;
import com.example.TopDownShooter.classes.events.OnGameEnd;
import com.example.TopDownShooter.classes.events.OnGameStart;
import com.example.TopDownShooter.classes.gameObjects.GameObject;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;
import com.example.TopDownShooter.dataTypes.Vector;
import com.example.TopDownShooter.dataTypes.enums.GameState;
import com.example.TopDownShooter.dataTypes.enums.PawnMotionState;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A Player is a class that owns a pawn or pawns in the game, and in charge of "telling" them
 * what to do in each update().Usually a UserPlayer determines how to update the pawn by
 * a UI controllers such as joystick or buttons.
 * On the other hand, an AIPlayer determines his pawn actions by logical algorithms
 */
public abstract class Player extends GameObject{

    public Player(Game myGame){
        super(myGame);

    }

    public abstract void updatePawn(Pawn pawn, UpdateTrace updateTrace);



}
