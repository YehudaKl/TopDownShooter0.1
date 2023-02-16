package com.example.TopDownShooter.classes.events.GameLoopEvents;

import com.example.TopDownShooter.classes.UI.Joystick;
import com.example.TopDownShooter.classes.UI.ShootButton;
import com.example.TopDownShooter.classes.systems.GameLoop;

/**
 * A trace for storing values and information about each update cycle in the game.
 * Each UI element that wants to update the values must the notify method for its type
 * Usually a game should use one permanent update trace and let other objects to edit its content
 */
public class UpdateTrace {
    private float deltaTime;
    private float joystickActuatorX;
    private float joystickActuatorY;
    private boolean isShoot;

    public float getDeltaTime() {
        return deltaTime;
    }

    public float getJoystickActuatorX() {
        return joystickActuatorX;
    }

    public float getJoystickActuatorY() {
        return joystickActuatorY;
    }

    public boolean getIsShoot(){
        return isShoot;
    }

   public void joystickNotify(Joystick joystick){
        joystickActuatorX = joystick.getActuatorX();
        joystickActuatorY = joystick.getActuatorY();
   }

    public void deltaTimeNotify(GameLoop gameLoop){
        this.deltaTime = gameLoop.getDeltaTime();
    }

    public void shootNotify(ShootButton shootButton){
        this.isShoot = shootButton.getIsShoot();
    }

   // Default constructor. uses the init method
   public UpdateTrace(){
        init();
   }

   public UpdateTrace(float deltaTime, float joystickActuatorX, float joystickActuatorY) {
        this.deltaTime = deltaTime;
        this.joystickActuatorX = joystickActuatorX;
        this.joystickActuatorY = joystickActuatorY;
    }

    // Method for erasing the content of the updateTrace and assigning 0 to all values
   public void init(){
        deltaTime = 0;
        joystickActuatorX = 0;
        joystickActuatorY = 0;
   }
}
