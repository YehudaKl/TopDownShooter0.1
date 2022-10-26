package com.example.TopDownShooter.classes.assets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
/**
 * ActorAsset is a class which is responsible for drawing the actor on the canvas.
 * The ActorAsset sets the origin of the drawable to the center
 */
import com.example.TopDownShooter.classes.gameObjects.actors.Actor;
import com.example.TopDownShooter.classes.games.Game;

public class ActorAsset{

    private Game game;
    private Actor actor;
    private Bitmap SourceBitmap;


    // Constant width and height for the drawable


    public ActorAsset(Game game, Actor actor, Bitmap SourceBitmap){
        this.game = game;
        this.actor = actor;
        this.SourceBitmap = SourceBitmap;


    }

    public void draw(@NonNull Canvas canvas) {

        final int WIDTH;
        final int HEIGHT;

        Bitmap bitmap = scaledBitmap(SourceBitmap);

        BitmapDrawable drawable = new BitmapDrawable(game.getResources(),bitmap);

        WIDTH = drawable.getIntrinsicWidth();
        HEIGHT = drawable.getIntrinsicHeight();

        // Draw from the center
        int boundX = (int)(actor.getPosition().getX() - WIDTH/2);
        int boundY = (int)(actor.getPosition().getY() - HEIGHT/2);


        drawable.setBounds(boundX, boundY, WIDTH + boundX, HEIGHT + boundY);



        drawable.draw(canvas);


    }

    // Returns a ready-to-draw bit map after all transformation and rotate

    private Bitmap scaledBitmap(Bitmap bitmap){
        // Convert from radians to degrees and subtracts 90.
        float degrees = (float) (actor.getDirection() * (180/Math.PI));

        return rotateBitmap(bitmap, degrees);
    }

    private Bitmap rotateBitmap(Bitmap bitmap, float degrees){
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

}
