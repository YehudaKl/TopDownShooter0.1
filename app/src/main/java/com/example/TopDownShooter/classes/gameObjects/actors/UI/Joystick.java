package com.example.TopDownShooter.classes.gameObjects.actors.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;
import com.example.TopDownShooter.dataTypes.Position;

public class Joystick extends Actor {

    private double outerCircleCenterPositionX;
    private double outerCircleCenterPositionY;
    private double innerCircleCenterPositionX;
    private double innerCircleCenterPositionY;

    private int outerCircleRadius;
    private int innerCircleRadius;

    private Paint outerCirclePaint;
    private Paint innerCirclePaint;

    private boolean isPressed;

    private double actuatorX;
    private double actuatorY;

    public Joystick(Game myGame, Position position, int outerCircleRadius, int innerCircleRadius){
        super(myGame, position, 0);

        this.outerCircleCenterPositionX = position.getX();
        this.outerCircleCenterPositionY = position.getY();
        this.innerCircleCenterPositionX = position.getX();
        this.innerCircleCenterPositionY = position.getY();

        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        this.outerCirclePaint = new Paint();
        this.innerCirclePaint = new Paint();

        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        this.isPressed = false;

        this.actuatorX = 0.0;
        this.actuatorY = 0.0;





    }

    @Override
    public void draw(Canvas canvas){

        // Draw outer circle
        canvas.drawCircle((float)outerCircleCenterPositionX, (float)outerCircleCenterPositionY, outerCircleRadius, outerCirclePaint);

        // Draw inner circle
        canvas.drawCircle((float)innerCircleCenterPositionX, (float)innerCircleCenterPositionY, innerCircleRadius, innerCirclePaint);

    }

    public void update(){
        updateInnerCirclePosition();
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {

        // Calculating the distance from the point of touching to the center of the joystick
        double joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                        Math.pow(outerCircleCenterPositionY - touchPositionY, 2)

        );
        return joystickCenterToTouchDistance < outerCircleRadius;

    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed(){
        return isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY){

        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(
                Math.pow(deltaX, 2) +
                        Math.pow(deltaY, 2)
        );

        if(deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }
        else{
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;

        }

    }


    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX(){
        return actuatorX;
    }

    public double getActuatorY(){
        return actuatorY;
    }

    private void updateInnerCirclePosition(){
        innerCircleCenterPositionX = (int)(outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int)(outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

}
