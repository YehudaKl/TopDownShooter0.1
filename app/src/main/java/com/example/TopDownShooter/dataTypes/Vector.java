package com.example.TopDownShooter.dataTypes;

/**
 * A simple geometric vector class with direction length and position. Will be replaced with a 3rd
 * party library if needed
 */
public class Vector {

    private double coordinateX;
    private double coordinateY;

    public Vector(double coordinateX, double coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }


    public double getMagnitude(){
        return Math.sqrt(Math.pow(coordinateX, 2) + Math.pow(coordinateY, 2));
    }

    // Returns the direction on radians, for degrees transformation is needed!
    public double getDirection(){
        return Math.atan2(coordinateY, coordinateX);
    }


    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }
}
