package com.example.TopDownShooter.dataTypes;

/**
 * A simple geometric vector class with direction length and position. Will be replaced with a 3rd
 * party library if needed
 */
public class Vector {

    private float coordinateX;
    private float coordinateY;

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }



    public Vector(float coordinateX, float coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public Vector(Vector other){
        this.coordinateX = other.coordinateX;
        this.coordinateY = other.coordinateY;
    }


    public double getMagnitude(){
        return Math.sqrt(Math.pow(coordinateX, 2) + Math.pow(coordinateY, 2));
    }

    // Returns the direction on radians, for degrees transformation is needed!
    public float getDirection(){
        return (float)Math.atan2(coordinateY, coordinateX);
    }

    // Checks if both x and y coordinates are 0
    public boolean isEmpty(){
        return (coordinateX == 0 && coordinateY == 0);
    }


}
