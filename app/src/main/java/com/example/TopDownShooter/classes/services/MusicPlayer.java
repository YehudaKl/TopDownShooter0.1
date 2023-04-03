package com.example.TopDownShooter.classes.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.TopDownShooter.R;

public class MusicPlayer extends Service {

    private MediaPlayer mediaPlayer;

    public MusicPlayer() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.stary_night);
        mediaPlayer.setVolume(0.1f, 0.1f);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> stopSelf());
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}