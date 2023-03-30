package com.example.TopDownShooter.classes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.systems.PremiumAuthenticator;

public class MainMenuActivity extends AppCompatActivity {

    private TextView helloPlayer;

    private Button singlePlayer;
    private Button multiPlayer;
    private Button premium;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        // Initializing the views
        helloPlayer = findViewById(R.id.txt_helloPlayer);
        singlePlayer = findViewById(R.id.btn_singlePlayer);
        multiPlayer = findViewById(R.id.btn_multiPlayer);
        premium = findViewById(R.id.btn_premium);


        singlePlayer.setOnClickListener(view -> navigateToSingleGame());
        premium.setOnClickListener(view -> navigateToPlayRoom());
        multiPlayer.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Missing content");
            builder.setMessage("The multiplayer content is ont available please download full version");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });



        // Setting the helloPlayer text to the player's name
        if(helloPlayer != null){

            helloPlayer.setText("Hello " + getIntent().getStringExtra(EnteringActivity.EXTRA_PLAYER_NAME));
        }


    }

    private void navigateToPlayRoom() {
        if(!isAuthenticatedForPlayroom()){return;}

        Intent intent = new Intent(getApplicationContext(), PremiumActivity.class);
        startActivity(intent);
    }

    private boolean isAuthenticatedForPlayroom(){
        return PremiumAuthenticator.authenticate(this);
    }



    private void navigateToSingleGame() {


        Intent intent = new Intent(getApplicationContext(),SingleGameActivity.class);
        startActivity(intent);


    }


}


