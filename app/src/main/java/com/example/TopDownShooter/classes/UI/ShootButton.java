package com.example.TopDownShooter.classes.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.TopDownShooter.classes.events.UIEvents.OnShoot;
import com.example.TopDownShooter.classes.games.Game;

import java.util.TimerTask;

import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Button that triggers an onShoot event to a game whenever it is pressed.
 * The ShootButton is in charge of providing the reload mechanism which means that the joystick must be provided by the
 * reload time of the user's character/pawn
 */
public class ShootButton extends AppCompatImageButton {
    public static final float DEFAULT_RELOAD_TIME = 10;

    private Game myGame;
    private boolean isReload;
    private float reloadTime;

    public void setIsReload(boolean reload) {
        isReload = reload;
    }


    public boolean isReload() {
        return isReload;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    public float getReloadTime(){
        return reloadTime;
    }

    public void setMyGame(Game myGame){
        this.myGame = myGame;
    }

    public ShootButton(Context context) {
        super(context);
    }

    public ShootButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        super.onTouchEvent(motionEvent);

        shoot();

        return true;
    }

    private void initialButton(){
        reloadTime = DEFAULT_RELOAD_TIME;
        isReload = false;
    }

    //TODO adam not synchronize
    private void shoot(){
        if(myGame == null || isReload == true ){return;}

        myGame.getOnShootObservable().onNext(new OnShoot(myGame));

        reload();
    }

    // TODO adam do it lambda
    private void reload(){

        if(isReload == true){return;}

        isReload = true;

        myGame.getTimer().schedule(new TimerTask() { public void run() { isReload  = false; }},(long)reloadTime);
    }




}
