package com.example.TopDownShooter.classes.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.TopDownShooter.R;

/**
 * The MainActivity is the entry point to the application and the first activity that
 * the user is shown when the app is opened.
 */
public class EnteringActivity extends Activity {

    public static final String EXTRA_PLAYER_NAME = "com.example.TopDownShooter";

    private Button startGame;
    private EditText enterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.entering_activity);

        startGame = findViewById(R.id.btn_StartGame);
        enterName = findViewById(R.id.edtTxt_EnterName);
        enterName.setText(getLastPlayerName());

        startGame.setOnClickListener(view -> {
            navigateToMainMenu();
        });




    }


    /**
     *  Function that checks if navigation to main menu is valid. if so navigation will take action.
     */
    private void navigateToMainMenu() {

        // Invalid player names
        String[] invalidUserNames = {
            "",
            "yourMother",
            "yourMama",
            "Ronit",
            "Fuck you",

        };
        String userName = enterName.getText().toString();

        // Check that the playerName is valid
        for(String name: invalidUserNames){
            if(userName.equals(name)){
                Toast.makeText(this, "Please enter a valid player name", Toast.LENGTH_SHORT).show();
                return;
            }
        }



        // Saving into the shared preferences
        saveLastPlayerName(userName);
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);

        intent.putExtra(EXTRA_PLAYER_NAME, enterName.getText().toString());
        startActivity(intent);


    }

    private String getLastPlayerName(){
        return getPreferences(Context.MODE_PRIVATE).getString("LastUserName", "");
    }

    private void saveLastPlayerName(String name){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LastUserName", name);
        editor.apply();
    }

}
