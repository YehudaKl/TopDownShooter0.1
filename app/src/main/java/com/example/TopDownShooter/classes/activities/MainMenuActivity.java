package com.example.TopDownShooter.classes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TopDownShooter.R;

public class MainMenuActivity extends AppCompatActivity {

    private TextView helloPlayer;

    // TODO replace with a menu class or whatever
    private Button singlePlayer;
    private Button multiPlayer;
    private Button settings;
    private Button joystickTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        // Initializing the views
        helloPlayer = findViewById(R.id.txt_helloPlayer);
        singlePlayer = findViewById(R.id.btn_singlePlayer);
        multiPlayer = findViewById(R.id.btn_multiPlayer);
        settings = findViewById(R.id.btn_multiPlayer);
        joystickTest = findViewById(R.id.btn_joystickTest);

        singlePlayer.setOnClickListener(view -> navigateToSingleGame());

        joystickTest.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), JoystickTestActivity.class);
            startActivity(intent);
        });



        // Setting the helloPlayer text to the player's name
        if(helloPlayer != null){

            helloPlayer.setText("Hello " + getIntent().getStringExtra(MainActivity.EXTRA_PLAYER_NAME));
        }


    }



    private void navigateToSingleGame() {


        Intent intent = new Intent(getApplicationContext(),SingleGameActivity.class);
        //TODO add extras to the intent
        startActivity(intent);


    }


}


