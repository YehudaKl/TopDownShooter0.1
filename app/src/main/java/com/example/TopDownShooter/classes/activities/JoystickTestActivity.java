package com.example.TopDownShooter.classes.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.UI.JoystickView;

public class JoystickTestActivity extends Activity {

    private JoystickView joystick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);
        joystick = new JoystickView(this);
        layout.addView(joystick);
        setContentView(layout);



    }

}
