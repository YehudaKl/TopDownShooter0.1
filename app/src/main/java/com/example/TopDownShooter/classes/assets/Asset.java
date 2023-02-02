package com.example.TopDownShooter.classes.assets;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

/**
 * Asset is a simple singleton getter class for getting lazy loaded bitmap from a resource
 * The first time the getDraw is called the bitmap is loaded
 */

public class Asset {

    private Bitmap bitmap;
    private int resourceId;
    private Resources res;

    public Asset(@DrawableRes int resourceId, Resources res){
        this.resourceId = resourceId;
        this.res = res;
    }

    public Bitmap getBitmap() {
        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(res, resourceId);
        }
        return Bitmap.createBitmap(bitmap);
    }

    // Method for forcing the asset to load the bitmap
    public void forceLoading(){
        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(res, resourceId);
        }
    }

}
