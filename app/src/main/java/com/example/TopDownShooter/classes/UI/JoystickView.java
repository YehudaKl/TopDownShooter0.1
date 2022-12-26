package com.example.TopDownShooter.classes.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private final short BASE_TOP_RATIO = 8;

    private float baseX;
    private float baseY;
    private float topX;
    private float topY;
    private float baseRadius;
    private float topRadius;

    private float baseTopRatio;// The size ratio between the base and the top of the joystick


    public JoystickView(Context context) {
        super(context);

        getHolder().addCallback(this);
        setOnTouchListener(this);


    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
        setOnTouchListener(this);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        setUpDimensions();
        // Getting drawn to the screen
        updateJoystick();



    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Draw base
        Paint paint = new Paint();
        paint.setARGB(255, 50, 50, 50);
        canvas.drawCircle(baseX, baseY, baseRadius, paint);

        // Draw top
        paint.setARGB(255, 0, 0, 255);
        canvas.drawCircle(topX, topY, topRadius, paint);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_UP:
                resetJoystick();
                break;
            default:
                adjustJoystickToTouchPosition(motionEvent.getX(), motionEvent.getY());
                break;
        }
        return true;
    }

    // Calculate the initial position for the base and the radius of the size of the joystick
    private void setUpDimensions(){
        baseX = getWidth()/2;
        baseY = getHeight()/2;
        topX = baseX;
        topY = baseY;

        // Make sure that the joystick will not go outbound
        float radius = Math.min(getWidth(), getHeight())/2;
        baseRadius = radius;
        topRadius = radius * baseTopRatio;


    }

    // Update the position of the top of the joystick according to a touch position and render the joystick
    private void adjustJoystickToTouchPosition(float positionX, float positionY){
        topX = positionX;
        topY = positionY;

        updateJoystick();
    }

    // Return the joystick top to the middle and redraw the joystick and render the joystick
    private void resetJoystick(){
        topX = baseX;
        topY = baseY;

        updateJoystick();

    }

    // Re-render the joystick again according to the values of topX and topY
    private boolean updateJoystick(){
        Canvas canvas = getHolder().lockCanvas();

        if(canvas == null){ return false;}

        draw(canvas);
        getHolder().unlockCanvasAndPost(canvas);

        return true;


    }

}