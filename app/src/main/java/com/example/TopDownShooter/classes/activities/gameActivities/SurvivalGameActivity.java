package com.example.TopDownShooter.classes.activities.gameActivities;

import android.app.Activity;
import android.app.assist.AssistContent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.view.Window;
import android.view.WindowManager;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.UI.Joystick;
import com.example.TopDownShooter.classes.UI.ShootButton;
import com.example.TopDownShooter.classes.activities.EnteringActivity;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.classes.games.SurvivalGame;
import com.example.TopDownShooter.dataTypes.enums.GameState;

public class SurvivalGameActivity extends Activity implements Game.LoadingListener{
    MediaPlayer backgroundMusicPlayer;
    SurvivalGame game;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Getting the window in order to modify it to full screen (hide status bar)
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        backgroundMusicPlayer = MediaPlayer.create(this, R.raw.survival_game_backgound_music);
        backgroundMusicPlayer.setVolume(0.1f, 0.1f);
        setContentView(R.layout.survival_game_root);

        game = findViewById(R.id.survival_game);

        Joystick joystick = findViewById(R.id.controller_joystick);
        joystick.setMyGame(game.getObservableProvider());

        ShootButton shootButton = findViewById(R.id.controller_shootButton);
        shootButton.setMyGame(game.getObservableProvider());

        game.setLoadingListener(this);

        //backgroundMusicPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(game.getState() == GameState.RUNNING){
            game.pauseGame();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(game.getState() == GameState.PAUSED){
            game.resumeGame();
        }
    }

    @Override
    public void LoadingStarted() {

    }

    @Override
    public void LoadingEnded() {
        game.resumeGame();
    }
}
