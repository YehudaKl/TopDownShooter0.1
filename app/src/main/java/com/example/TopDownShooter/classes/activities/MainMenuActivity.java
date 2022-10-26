package com.example.TopDownShooter.classes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TopDownShooter.R;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView helloPlayer;

    // TODO replace with a menu class or whatever
    private Button singlePlayer;
    private Button multiPlayer;
    private Button settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        // Initializing the views
        helloPlayer = findViewById(R.id.txt_helloPlayer);
        singlePlayer = findViewById(R.id.btn_singlePlayer);
        multiPlayer = findViewById(R.id.btn_multiPlayer);
        settings = findViewById(R.id.btn_multiPlayer);

        singlePlayer.setOnClickListener(this);
        // Setting the helloPlayer text to the player's name
        if(helloPlayer != null){

            helloPlayer.setText("Hello " + getIntent().getStringExtra(MainActivity.EXTRA_PLAYER_NAME));
        }


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_singlePlayer:
                navigateToSingleGame();
                break;
            default:
                Toast.makeText(this, "Could not detect the button", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToSingleGame() {


        Intent intent = new Intent(getApplicationContext(),SingleGameActivity.class);
        //TODO add extras to the intent
        startActivity(intent);

    }
}