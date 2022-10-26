package com.example.TopDownShooter.classes.systems.Effects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FlashScreen extends Effect{

    private Paint paint;

    public FlashScreen(Paint paint){
        super();
        this.paint = paint;
    }
    @Override
    public void drawEffect(Canvas canvas) {
        canvas.drawPaint(paint);
        endEffect();
    }
}
