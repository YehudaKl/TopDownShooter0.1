package com.example.TopDownShooter.classes.games;

import com.example.TopDownShooter.classes.Team;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.Pawn;

/**
 * An interface for games that has teams in their game rules.
 */
public interface TeamsGame {

    Team generateMeTeam(Pawn pawn);
}
