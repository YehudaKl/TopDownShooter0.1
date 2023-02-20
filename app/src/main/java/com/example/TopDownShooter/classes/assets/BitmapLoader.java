package com.example.TopDownShooter.classes.assets;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;

import androidx.annotation.DrawableRes;

/**
 * BitmapLoader is a helper class for actors for auto scaling and loading bitmaps
 * The BitmapLoader gets a resourceId in its constructor and android Resources
 */

public class BitmapLoader {

    private Bitmap originBitmap;
    private Bitmap loadedBitmap;
    private final int resourceId;
    private final Resources res;
    private float rotation;
    private float rotationOffset;

    public BitmapLoader(@DrawableRes int resourceId, Resources res, float rotationOffset){
        this.resourceId = resourceId;
        this.res = res;
        this.rotation = 0;
        this.rotation = rotationOffset;

    }

    public BitmapLoader(@DrawableRes int resourceId, Resources res){
        this(resourceId, res, 0);
    }

    // Scaling and changing the  bitmap
    public void scale(float xScale, float yScale){
        if(originBitmap == null){
            originBitmap = BitmapFactory.decodeResource(res, resourceId);
        }
        originBitmap = Bitmap.createScaledBitmap(originBitmap, (int)(originBitmap.getWidth() * xScale), (int)(originBitmap.getHeight() * yScale), true);
    }


    public Bitmap getBitmap(float rotation) {
        if(originBitmap == null){
            originBitmap = BitmapFactory.decodeResource(res, resourceId);
            loadedBitmap = originBitmap;

        }

        // Return a rotated bitmap if the rotation changed
        if(this.rotation != rotation){
            double degrees = Math.toDegrees(rotation) + rotationOffset;
            Matrix matrix = new Matrix();
            matrix.postRotate((float) degrees);
            loadedBitmap =  Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix, true);
            this.rotation = rotation;
        }

        return loadedBitmap;

    }

    // Method for forcing the asset to load the bitmap
    public void forceLoading(){
        if(originBitmap == null){
            originBitmap = BitmapFactory.decodeResource(res, resourceId);
        }
    }

    //Releases the data of the origin bitmap;
    public void release(){
        this.originBitmap = null;
        this.loadedBitmap = null;
    }

}
