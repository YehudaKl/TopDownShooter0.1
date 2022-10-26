package com.example.TopDownShooter.classes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.activities.gameActivities.SurvivalGameActivity;
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.SurvivalGame;
import com.example.TopDownShooter.dataTypes.enums.GameMode;

/**
 * The goal of the SingleGameActivity is to be the main lobby for all single game related games.
 * The user chooses what game mode to play, what character to choose, upgrades... ect
 */
public class SingleGameActivity extends Activity implements View.OnClickListener{

    private Button survival;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_game_activity);

        survival = findViewById(R.id.btn_survival);
        survival.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_survival:
                startGame(GameMode.SURVIVAL);
                break;

            default:
                break;

        }
    }

    // Starts a game according to the game mode provided
    private void startGame(GameMode mode){
        switch(mode){
            case SURVIVAL:
                Intent intent = new Intent(getApplicationContext(), SurvivalGameActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
