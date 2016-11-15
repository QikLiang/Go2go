package edu.up.cs301.go2go;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by dukeo_000 on 11/14/2016.
 */

public class GoSurfaceView extends SurfaceView
{
    public GoSurfaceView(Context context)
    {
        super(context);
        init(context);
    }
    public GoSurfaceView(Context context, AttributeSet attrs,int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        setWillNotDraw(false);
        setBackgroundColor(Color.YELLOW);
    }

    public void onDraw(Canvas c)
    {

        super.onDraw(c);
        float width = c.getWidth();
        float height = c.getHeight();
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(15);

        for(int i = 1; i < 9; i++){c.drawLine(width+100,height+(i*100),width-100,height+10+(i*100),p);}
        for(int i = 1; i < 9; i++){c.drawLine(width+(i*100),height+100,width+10+(i*100),height-100,p);}
    }
}
