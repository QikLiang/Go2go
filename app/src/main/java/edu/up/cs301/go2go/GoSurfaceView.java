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

import edu.up.cs301.game.GameMainActivity;
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
    public static BitmapDrawable background;
    public static GameMainActivity activity;
    private static android.graphics.Bitmap map;
    private boolean myTurn = false;
    private boolean firstTime = true;

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

    public void initBackground(GameMainActivity ac)
    {
        BitmapFactory.Options o = new BitmapFactory.Options();
        Resources resources = ac.getResources();
        map = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher,o);
        map = Bitmap.createScaledBitmap(map,(int)cWidth*(5/4),(int)cHeight*(5/4),false);
        background = new BitmapDrawable(resources,map);

    }
    private void init(Context context)
    {
        activity = GoHumanPlayer.getActivity();
        setWillNotDraw(false);
        setBackgroundColor(Color.LTGRAY);

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

        if(firstTime)
        {
            firstTime = false;
            cWidth = c.getWidth();
            cHeight = c.getHeight();
            initBackground(activity);
            this.invalidate();
            return;
        }
        c.drawBitmap(map,0,0,null);
        super.onDraw(c);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(10);

        double goStartX = cWidth*.1;
        double goStartY = cHeight*.1;
        double goEndX = cWidth*.9;
        double goEndY = cHeight*.9;
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
