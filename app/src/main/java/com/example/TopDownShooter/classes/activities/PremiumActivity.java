package com.example.TopDownShooter.classes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.TopDownShooter.R;
import com.example.TopDownShooter.classes.services.MusicPlayer;

import java.util.ArrayList;

public class PremiumActivity extends AppCompatActivity{
    private static final String DEVELOPER_NUMBER = "0502525209";
    private static ArrayList<String> tipsList;

    private ListView tipsListView;

    static{
        tipsList = new ArrayList<>();
        tipsList.add("The best strategy to win the survival game is to always stay in motion");
        tipsList.add("Try not to waist your ammo by shooting double shots on weak enemies");
        tipsList.add("It's a good practice to use your left hand for the joystick");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        this.tipsListView = findViewById(R.id.lst_tips);
        this.tipsListView.setAdapter(new ArrayAdapter<>(this, R.layout.simplerow, tipsList));

        Intent serviceIntent = new Intent();
        serviceIntent.setClass(this, MusicPlayer.class);
        startService(serviceIntent);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.play_room_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.phone_call:
                phoneCall();
        }

        return super.onOptionsItemSelected(item);
    }

    private void phoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + DEVELOPER_NUMBER));
        startActivity(callIntent);
    }


}