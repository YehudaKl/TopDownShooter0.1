package com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.monsters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.gameObjects.actors.pawns.characters.Character;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.systems.Effects.FlashScreen;
import com.example.TopDownShooter.dataTypes.Position;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A Zombie is a monster that can bite from short distance and cause damage.
 * For more information about zombie as an enemy see ZombiePlayer class.
 */
public class Zombie extends Monster {

    private final float BITE_DAMAGE;
    public final float BITE_COOLDOWN_TIME;
    public final float BITE_RANGE;// The maximal distance from a character in order to bite(inclusive)

    private boolean canBite;// If zombie in bite-cooldown false, else true


    public Zombie(Game myGame, Position initPosition){
        super(myGame, initPosition,  R.drawable.zombie1_stand);

        this.health = 1;//conf
        this.BITE_DAMAGE = 50;//conf
        this.BITE_COOLDOWN_TIME = 1500;//conf
        this.BITE_RANGE = 100;//conf

        this.canBite = true;


    }


    public void bite(Character character){
        double distance = getDistanceBetween(character);
        if(distance >= BITE_RANGE || !canBite){ return;}

        Log.e("ZombieDebug", "Bite!");

        character.reduceHealth(BITE_DAMAGE);

        // Flash effect
//        myGame.getEffectsSystem().signNewEffect(new FlashScreen(new Paint(R.color.red)));


        // Start the cooldown process
        canBite = false;
        myGame.timer.schedule(new BiteCooldownTask(this), (long) BITE_COOLDOWN_TIME);
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

// TODO make it more efficient and for all classes
class BiteCooldownTask extends TimerTask{

    private Zombie zombie;

    public BiteCooldownTask(Zombie zombie){
        this.zombie = zombie;
    }

    @Override
    public void run() {
        zombie.setCanBite(true);
    }
}
