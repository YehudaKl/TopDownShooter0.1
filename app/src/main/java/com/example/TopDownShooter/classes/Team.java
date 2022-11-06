package com.example.TopDownShooter.classes;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.TopDownShooter.dataTypes.exceptions.IllegalTeamId;

import java.util.ArrayList;

/**
 * A Team is a simple class that defines the team of a specific character.
 * The Team class is used so the characters will be able to differ themselves from their enemies.
 * Note! it is not possible to create two team with the same id, but it is possible to create two
 * with the same color.
 */
public class Team {
    public final int ID;
    public final Paint PAINT;

    private static ArrayList<Integer> idsUsed = new ArrayList<>();

    public Team(int id, Paint paint) throws IllegalTeamId {
        // Check that the id is not already taken, if so the function will throw IllegalTeamId exception
        for(int d: idsUsed){
            if(d == id){
                throw new IllegalTeamId();
            }
        }

        this.ID = id;
        this.PAINT = paint;

        // Add the id to the used ids
        idsUsed.add(id);
    }

    // Clear an id that is not used any more
    // Note! the method must be called after a team is being eliminated are when the game is over
    public static void clearUsedId(int id){
        idsUsed.remove(id);
    }

    // TODO ...
    public void OnMemberDied() {


    }
}
