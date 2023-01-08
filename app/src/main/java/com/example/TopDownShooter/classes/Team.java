package com.example.TopDownShooter.classes;

import android.graphics.Paint;

/**
 * A Team is a simple class that defines the team of a specific character.
 * The Team class is used so the characters will be able to differ themselves from their enemies.
 * Note! it is not possible to create two team with the same id, but it is possible to create two
 * with the same color.
 */
public class Team {
    public final int id;
    public final Paint paint;

    public int getId() {
        return id;
    }

    public Paint getPaint() {
        return paint;
    }


    public Team(int id, Paint paint){

        this.id = id;
        this.paint = paint;

    }

    public boolean equals(Team other){
        return(this.id == other.id);
    }
}
