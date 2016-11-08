package edu.up.cs301.go2go;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import edu.up.cs301.animation.Animator;

/**
 * Created by Jenkin Schibel on 11/7/2016.
 */

public class GoAnimator implements Animator {
    public int interval() {
        return 30;
    }

    public void tick(Canvas c)
    {
        int width = c.getWidth(); int height = c.getHeight();
        Paint p = new Paint();
        p.setStrokeWidth(20);
        p.setColor(Color.BLACK);
    }






    public int backgroundColor() {return Color.rgb(255,194,10);}

    public boolean doPause() {return false;}

    public boolean doQuit() {return false;}

    public void onTouch(MotionEvent event)
    {
        if(event.getAction == MotionEvent.ACTION_DOWN)
        {
            int x = event.getX();
            int y = event.getY();
            
            //TODO: possibly implementing make move stuff here
        }
    }
}
