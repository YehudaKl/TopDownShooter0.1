package com.example.TopDownShooter.classes.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.TopDownShooter.classes.events.GameLoopEvents.OnPreUpdate;
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
    public static final float DEFAULT_RELOAD_TIME = 500;

    private Game myGame;
    private boolean isReload;
    private float reloadTime;
    private boolean isShoot;

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

    public boolean getIsShoot(){
        return isShoot;
    }

    public void setMyGame(Game myGame){
        this.myGame = myGame;
        myGame.getOnPreUpdateObservable().subscribe(this::onPreUpdate);
    }

    public ShootButton(Context context) {
        super(context);
        init();
    }

    public ShootButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        super.onTouchEvent(motionEvent);

        if(!isReload){
            isShoot = true;
            reload();
        }

        return true;
    }



    public void onPreUpdate(OnPreUpdate onPreUpdate){
        onPreUpdate.getUpdateTrace().shootNotify(this);
        isShoot = false;
    }

    private void init(){
        reloadTime = DEFAULT_RELOAD_TIME;
        isReload = false;
        isShoot = false;
    }

    private void reload(){
        if(isReload){return;}

        isReload = true;

        myGame.getTimer().schedule(new TimerTask() { public void run() { isReload  = false; }},(long)reloadTime);
    }




}
