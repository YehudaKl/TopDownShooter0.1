package com.example.TopDownShooter.classes.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private final float BASE_TOP_RATIO = 0.5f;

    private float baseX;
    private float baseY;
    private float topX;
    private float topY;
    private float baseRadius;
    private float topRadius;

    private float baseTopRatio;// The size ratio between the base and the top of the joystick


    public JoystickView(Context context) {
        super(context);

        this.baseTopRatio = BASE_TOP_RATIO;

        getHolder().addCallback(this);
        setOnTouchListener(this);


    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);

        this.baseTopRatio = BASE_TOP_RATIO;

        getHolder().addCallback(this);
        setOnTouchListener(this);

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        setUpDimensions();
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        // Getting drawn to the screen
        Canvas canvas = getHolder().lockCanvas();
        draw(canvas);
        getHolder().unlockCanvasAndPost(canvas);




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

        Paint paint = new Paint();

        //Draw base
        paint.setARGB(255, 50, 100, 50);
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

    // Method that returns the actuators in each direction in order to use the joy stick for any purpose
    public float getActuatorX(){
        float distanceX = topX - baseX;
        return distanceX/baseRadius;
    }

    public float getActuatorY(){
        float distanceY = topY - baseY;
        return distanceY/baseRadius;
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
        topRadius = baseRadius * baseTopRatio;


    }

    // Update the position of the top of the joystick according to a touch position and render the joystick
    private void adjustJoystickToTouchPosition(float positionX, float positionY){
        float distance = distance(baseX, baseY, positionX, positionY);
        float deltaX = positionX - baseX;
        float deltaY = positionY - baseY;


        if(distance > baseRadius){// position is out of bounds
            deltaX *= baseRadius/distance;
            deltaY *= baseRadius/distance;


            topX = deltaX + baseX;
            topY = deltaY + baseY;
        }
        else{
            topX = positionX;
            topY = positionY;
        }

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

    private float distance(float x1, float y1, float x2, float y2){
        return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private void parseAttributes(AttributeSet attrs) {



    }

}