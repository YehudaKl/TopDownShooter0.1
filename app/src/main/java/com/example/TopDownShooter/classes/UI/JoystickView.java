package com.example.TopDownShooter.classes.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.TopDownShooter.R;

public class JoystickView extends View{

    private static final float TOP_BASE_RATIO = 0.5f;
    private static final int defaultSize = 50;
    private int size;// The edge of the "box" that encapsulates the joystick. Equivalent to the min of view's width and height
    private float baseX;
    private float baseY;
    private float topX;
    private float topY;
    private float baseRadius;
    private float topRadius;
    private Paint basePaint;
    private Paint topPaint;

    private float topBaseRatio;// The size ratio between the base and the top of the joystick


    public JoystickView(Context context) {
        super(context);
        initJoystickView();
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initJoystickView();

    }

    @Override
    public void onFinishInflate(){
        super.onFinishInflate();

    }


    @Override
    public void onDraw(Canvas canvas) {

       // Draw base
        canvas.drawCircle(baseX, baseY, baseRadius, basePaint);

      // Draw paint         
        canvas.drawCircle(topX, topY, topRadius, topPaint);

    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){

        // Setting the size to be the shortest from the width and height spec, by convention
        size = Math.min(measure(widthMeasureSpec), measure(heightMeasureSpec));
        setMeasuredDimension(size, size);
        setUpDimensions();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        size = Math.min(measure(width), measure(height));
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
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

    private void initJoystickView(){
        this.topBaseRatio = TOP_BASE_RATIO;

        basePaint = new Paint();
        basePaint.setColor(Color.RED);

        topPaint = new Paint();
        topPaint.setColor(Color.GRAY);
    }

    // Method for generating a fixed size from the width/height specification
    private int measure(int measureSpec){

        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            // Return a default size of 50 if no bounds are specified.
            return defaultSize;
        }
        else {

            return specSize;
        }

    }

    // Calculate the initial position for the base and the radius of the size of the joystick
    private void setUpDimensions(){
        baseX = size/2;
        baseY = size/2;

        topX = baseX;
        topY = baseY;

        // Make sure that the joystick will not go outbound
        // The formula for the calculation of the radius was found using algebra on paper
        // (1) topRadius + baseRadius == size/2
        // (2) topRadius/baseRadius == topBaseRatio

        topRadius = ((size * topBaseRatio)/(2 + 2 *topBaseRatio));
        baseRadius = size/2 - topRadius;

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
        invalidate();
    }

    // Return the joystick top to the middle and redraw the joystick and render the joystick
    private void resetJoystick(){
        topX = baseX;
        topY = baseY;
        invalidate();
    }

    // Re-render the joystick again according to the values of topX and topY


    private float distance(float x1, float y1, float x2, float y2){
        return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private void parseAttributes(AttributeSet attrs) {

    }

}