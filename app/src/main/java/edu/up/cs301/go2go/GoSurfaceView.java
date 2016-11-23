package edu.up.cs301.go2go;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceView;

import edu.up.cs301.game.R;

/**
 * Created by Jenkin Schibel on 11/14/2016.
 */

public class GoSurfaceView extends SurfaceView
{
    private static int[][] goBoard;
    private static int[][] proposal;
    public static double cWidth;
    public static double cHeight;
    public static Drawable background;

    private boolean myTurn = false;

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public GoSurfaceView(Context context)
    {
        super(context);
        init(context);
    }
    public GoSurfaceView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        init(context);
    }
    public GoSurfaceView(Context context, AttributeSet attrs,int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
        init(context);
    }

    public static void initBackground(Activity ac)
    {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = true;
        Resources resources = ac.getResources();
        Bitmap map;
        map = BitmapFactory.decodeResource(resources, R.mipmap.good_wood,o);
        background = new BitmapDrawable(resources,map);

    }
    private void init(Context context)
    {

        setWillNotDraw(false);
        setBackgroundColor(Color.YELLOW);
//        setBackground(background);
        goBoard = new int[9][9];


    }

    public void getBoard(int[][] newBoard)
    {
        goBoard = newBoard;
    }

    public static void setBoard(int[][] board)
    {
        goBoard = board;
    }

    public static void setProposal(int[][] proposal){
        GoSurfaceView.proposal=proposal;
    }

    public void onDraw(Canvas c)
    {
        super.onDraw(c);

        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(10);

        cWidth = c.getWidth();
        cHeight = c.getHeight();
        double goStartX = cWidth*.055555;
        double goStartY = cHeight*.055555;
        double goEndX = cWidth*.95;
        double goEndY = cHeight*.95;
        double goBoardWidth = goEndX-goStartX;
        double goBoardHeigth = goEndY-goStartY;
        int goPieceSize = (int)(goBoardHeigth/GoGameState.boardSize/2);


        //DRAW BOARD

        for(float i=0; i<=8; i++)//draws horizontal lines
        {
            float nextLine = (float)(goBoardHeigth*(i/8));
            c.drawLine((float)goStartX,(float)goStartY+nextLine,(float)goEndX,(float)goStartY+nextLine,p);
        }
        for(float i=0; i<=8; i++)//draws vertical lines
        {
            float nextLine = (float)(goBoardWidth*(i/8));
            c.drawLine((float)goStartX+nextLine,(float)goStartY,(float)goStartX+nextLine,(float)goEndY,p);
        }


        for(int i = 0; i < goBoard.length;i++)
        {
            for(int k = 0; k < goBoard[0].length; k++)
            {
                float nextX = (float)(goBoardWidth*i/8 +goStartX);
                float nextY = (float)(goBoardHeigth*k/8 +goStartY);
                p.setStyle(Paint.Style.FILL);
                switch (goBoard[i][k]){
                    case GoGameState.WHITE:
                        p.setColor(Color.WHITE);
                        c.drawCircle(nextX,nextY,goPieceSize,p);
                        break;
                    case GoGameState.BLACK:
                        p.setColor(Color.BLACK);
                        c.drawCircle(nextX,nextY,goPieceSize,p);
                }

                if(proposal==null){
                    continue;
                }
                if (proposal[i][k]>0){
                    p.setColor(Color.BLACK);
                    c.drawCircle(nextX,nextY,goPieceSize/3,p);
                }else if (proposal[i][k]<0) {
                    p.setColor(Color.WHITE);
                    c.drawCircle(nextX, nextY, goPieceSize / 3, p);
                }
            }
        }

        p.setColor(0xFF0000FF);
        //draw turn indicator
        if(myTurn){
            c.drawOval(new RectF(0f, 1545f, 470f, 1645f), p);
        } else {
            c.drawOval(new RectF(1083f, 1545f, 1537f, 1645f), p);
        }
    }
}
