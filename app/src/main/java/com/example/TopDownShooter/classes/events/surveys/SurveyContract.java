package com.example.TopDownShooter.classes.events.surveys;

import com.example.TopDownShooter.classes.gameObjects.actors.Actor;

/**
 * An interface for specifying a SurveyContract
 */
public interface SurveyContract <T>  {

    boolean isComply(T obj);

}

