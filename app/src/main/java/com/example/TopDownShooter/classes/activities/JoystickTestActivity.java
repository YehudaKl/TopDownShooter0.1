package com.example.TopDownShooter.classes.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.UI.Joystick;
import com.example.TopDownShooter.classes.systems.TMXLoader.TMXLoader;
import com.example.TopDownShooter.classes.systems.TMXLoader.TileMapData;

public class JoystickTestActivity extends Activity {

    private Joystick joystick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView map  = new ImageView(this);
        TileMapData t = TMXLoader.readTMX("../../../../assets/map1.tmx", this);

        Bitmap mapBitmap = TMXLoader.createBitmap(t, this, 0, t.layers.size());
        if(mapBitmap != null){
            map.setImageBitmap(mapBitmap);
            setContentView(map);
        }
        else{
            setContentView(R.layout.joystick_test_activity);
        }


    }
    

}
