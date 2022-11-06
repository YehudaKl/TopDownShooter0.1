package com.example.TopDownShooter.classes.gameObjects;

/**
 * GameObject is a generic class for all objects that take part in the game
 * or affect the game.
 */

public abstract class GameObject {

    /**
     * @param isValid determines the validation of the object in the context of its game.
     *                not valid actor would be deleted from the game at the update.
     *                In order to set the validation of an object to be false the method Invalidate() must be called from a sub class.
     *                Note!. It is important to delete any references to this object before the method is called in order to prevent crashes
     */
    private boolean isValid;

    public GameObject(){
        this.isValid = true;
    }


    public boolean getIsValid(){
        return isValid;
    }
    // Make the actor invalid
    protected void Invalidate(){
        this.isValid = false;
    }
}
