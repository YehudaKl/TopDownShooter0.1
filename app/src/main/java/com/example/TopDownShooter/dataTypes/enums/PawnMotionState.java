package com.example.TopDownShooter.dataTypes.enums;

/**
 * The PawnMotionState enum is responsible for specifying whether the pawn is movable or not
 * Before the games' start all pawns' motion state is FROZE which means the are not able to move
 * The Player's responsibility is to change that state to moving when the game starts;
 */
public enum PawnMotionState {
    FROZE,
    MOVING
}
