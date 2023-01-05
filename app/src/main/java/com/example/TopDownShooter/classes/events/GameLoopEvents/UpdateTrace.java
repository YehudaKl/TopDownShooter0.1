package com.example.TopDownShooter.classes.events.GameLoopEvents;

/**
 * A trace for storing values and information about each update cycle in the game.
 */
public class UpdateTrace {
    private float deltaTime;
    private float joystickActuatorX;
    private float joystickActuatorY;

    public float getDeltaTime() {
        return deltaTime;
    }

    public float getJoystickActuatorX() {
        return joystickActuatorX;
    }

    public float getJoystickActuatorY() {
        return joystickActuatorY;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void setJoystickActuatorX(float joystickActuatorX) {
        this.joystickActuatorX = joystickActuatorX;
    }

    public void setJoystickActuatorY(float joystickActuatorY) {
        this.joystickActuatorY = joystickActuatorY;
    }

    public UpdateTrace(float deltaTime, float joystickActuatorX, float joystickActuatorY) {
        this.deltaTime = deltaTime;
        this.joystickActuatorX = joystickActuatorX;
        this.joystickActuatorY = joystickActuatorY;
    }
}
