package com.example.TopDownShooter.classes.activities.gameActivities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Window;
import android.view.WindowManager;

import com.example.TopDownShooter.classes.games.SurvivalGame;

public class SurvivalGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Getting the window in order to modify it to full screen (hide status bar)
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(new SurvivalGame(this));


    }
}
