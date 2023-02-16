package com.example.TopDownShooter.classes.systems;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.TopDownShooter.classes.games.Game;

public class GameLoop extends Thread{

    public static final double MAX_UPS = 60;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double averageUPS;
    private double averageFPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {

        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public double getAverageUPS(){
        return averageUPS;
    }

    public double getAverageFPS(){
        return averageFPS;
    }

    public float getDeltaTime(){
        // If a single second has not passed there is no delta time,
        // so return a synthetic delta time by using the MAX_UPS
        if(averageUPS == 0){
            return 1/(float)MAX_UPS;
        }
        return 1/(float)averageUPS;
    }

    public void startLoop() {
        isRunning = true;
        start();

    }

    @Override
    public void run(){
        super.run();

        // Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Game Loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning){
            // Try to update and render the game
            try{
                canvas = surfaceHolder.lockCanvas();

                synchronized (surfaceHolder){
                    game.update();
                    updateCount++;

                    game.draw(canvas);
                }


            }catch(IllegalArgumentException e){
                e.printStackTrace();
            }finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }




            // Pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0){
                try{
                    sleep(sleepTime);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            // Skip frames to keep up with target UPS
            while(sleepTime < 0 && updateCount < MAX_UPS - 1){
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() -elapsedTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }
            // Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >=1000){
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount  / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }



        }
    }


}

