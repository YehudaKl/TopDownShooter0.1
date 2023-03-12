package com.example.TopDownShooter.classes.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.UI.Joystick;

public class JoystickTestActivity extends Activity {

    private Joystick joystick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "App created", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(this, "App stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "App started", Toast.LENGTH_SHORT).show(); 
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "App Resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "App Destroyed", Toast.LENGTH_SHORT).show();
    }

}
