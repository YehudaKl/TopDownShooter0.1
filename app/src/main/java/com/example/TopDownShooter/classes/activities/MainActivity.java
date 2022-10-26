package com.example.TopDownShooter.classes.activities;

import android.app.Activity;
import android.content.Intent;
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
public class MainActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_PLAYER_NAME = "com.example.TopDownShooter";

    private Button startGame;
    private EditText enterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        startGame = findViewById(R.id.btn_StartGame);
        enterName = findViewById(R.id.edtTxt_EnterName);

        startGame.setOnClickListener(this);




    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_StartGame:
                // Navigate to the mainMenu activity
                // If no name was specified the navigation will fail
                navigateToMainMenu();


                break;

            default:
                break;

        }
    }

    /**
     *  Function that checks if navigation to main menu is valid. if so navigation will take action.
     */
    private void navigateToMainMenu() {

        // Invalid player names
        String[] invalidPlayerNames = {
            "",
            "yourMother",
            "yourMama",
            "Ronit"

        };



        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);

        String playerName = enterName.getText().toString();

        // Check that the playerName is valid
        for(String name: invalidPlayerNames){
            if(playerName.equals(name)){
                Toast.makeText(this, "Please enter a valid player name", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        intent.putExtra(EXTRA_PLAYER_NAME, enterName.getText().toString());
        startActivity(intent);


    }
}
