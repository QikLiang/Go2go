package edu.up.cs301.go2go;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Jenkin Schibel on 11/14/2016.
 */

public class GoSurfaceView extends SurfaceView
{

    public static int[][] goBoard;
    public static double cWidth;
    public static double cHeight;

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

    private void init(Context context)
    {

        setWillNotDraw(false);
        setBackgroundColor(Color.YELLOW);
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

        /*for(int i=0; i < 9; i++)
        {
            for(int k=0; k < 9; k++)
            {
                goBoard[i][k] = GoGameState.WHITE;
            }
        }*/
        for(int i = 0; i < goBoard.length;i++)
        {
            for(int k = 0; k < goBoard[0].length; k++)
            {
                float nextX = (float)(goBoardWidth*i/8 +goStartX);
                float nextY = (float)(goBoardHeigth*k/8 +goStartY);
                if(goBoard[i][k] == GoGameState.WHITE)
                {
                    p.setColor(Color.WHITE);
                    p.setStyle(Paint.Style.FILL);
                    c.drawCircle(nextX,nextY,goPieceSize,p);
                }
                else if(goBoard[i][k] == GoGameState.BLACK)
                {
                    p.setColor(Color.BLACK);
                    p.setStyle(Paint.Style.FILL);
                    c.drawCircle(nextX,nextY,goPieceSize,p);
                }
                else
                {

                }


            }
        }
    }
}
