package com.example.TopDownShooter.dataTypes;

public class Position {

    private float x;
    private float y;

    public Position(float x, float y){
        this.x = x;
        this.y = y;
    }

    // Deep copy constructor
    public Position(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
