package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters;

import android.util.Log;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.assets.Asset;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

import java.util.TimerTask;

/**
 * A Zombie is a monster that can bite from short distance and cause damage.
 * For more information about zombie as an enemy see ZombiePlayer class.
 */
public class Zombie extends Monster {

    private final float BITE_DAMAGE;
    private static final float MAX_SPEED = 30;//conf
    public static final float BITE_COOLDOWN_TIME = 1500;//conf
    public static final float BITE_RANGE = 100;// The maximal distance from a character in order to bite(inclusive)

    private boolean canBite;// If zombie in bite-cooldown false, else true


    public Zombie(Game myGame, Position initPosition){
        // TODO replace null
        super(myGame, initPosition, new Asset(R.drawable.zombie1_stand, myGame.getResources()));

        this.health = 1;//conf
        this.BITE_DAMAGE = 20;//conf


        this.canBite = true;


    }


    public void bite(Character character){
        double distance = getDistanceBetween(character);
        if(distance >= BITE_RANGE || !canBite){ return;}

        character.reduceHealth(BITE_DAMAGE);

        // Start the cooldown process
        //TODO adam do it with lambda
        canBite = false;
        myGame.getTimer().schedule(new TimerTask() {
            @Override
            public void run() { setCanBite(true); }
        }, (long) BITE_COOLDOWN_TIME);
    }


    @Override
    public float getSpeed() {
        return MAX_SPEED;
    }

    public void setCanBite(boolean canBite){
        this.canBite = canBite;
    }

    // Returns weather some character is in range of the zombie bite, while ignoring the bite cooldown
    // Should be used by the player in order to determine what to do.
    public boolean isInRangeOfBite(Character character){
        return getDistanceBetween(character) <= BITE_RANGE;
    }
}

