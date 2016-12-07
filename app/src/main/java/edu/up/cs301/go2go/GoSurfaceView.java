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
    private int[][] goBoard;
    private int[][] proposal;
    private int prevX;
    private int prevY;
    public static double cWidth,cHeight,goBoardWidth,goBoardHeigth,goStartX,goStartY,goEndX,goEndY;
    public static int goPieceSize;
    public static GameMainActivity activity;
    private static android.graphics.Bitmap background,vegdahl,nuxoll,whiteBowl,blackBowl;
    private boolean myTurn = false;
    private boolean drawBlackBowl = false;
    private boolean drawWhiteBowl = false;
    private static boolean firstTime = true;
    private static boolean drawVegW=false,drawNuxW=false,drawVegB=false,drawNuxB=false;
    private int playerNum;

    public void setMyTurn(boolean myTurn, int player)
    {
        this.myTurn = myTurn;
        this.playerNum = player;
    }

    public void setPrevMove( int x, int y ){
        prevX = x;
        prevY = y;
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

    public static void setEasterEgg(String name1, String name2){
        //checks for setting nuxol and vegdahl head easter egg
        if(name1.contains("nux")||name1.contains("Nux")){
            GoSurfaceView.drawNuxB=true;
        }
        if(name1.contains("veg")||name1.contains("Veg"))
        {
            GoSurfaceView.drawVegB=true;
        }

        //checks for setting nuxol and vegdahl head easter egg
        if(name2.contains("nux")||name2.contains("Nux")){
            GoSurfaceView.drawNuxW=true;
        }
        if(name2.contains("veg")||name2.contains("Veg"))
        {
            GoSurfaceView.drawVegW=true;
        }
    }


    public void initImages(GameMainActivity ac)
    {
        double size = (double)goPieceSize;
        BitmapFactory.Options o = new BitmapFactory.Options();
        Resources resources = ac.getResources();
        background = BitmapFactory.decodeResource(resources, R.drawable.goodwood,o);
        background = Bitmap.createScaledBitmap(background,(int)(cWidth*.86),(int)(cHeight*.86),false);
        vegdahl = BitmapFactory.decodeResource(resources,R.drawable.vegdahl);
        vegdahl = Bitmap.createScaledBitmap(vegdahl,(int)(size*2),(int)(size*2),false);
        nuxoll = BitmapFactory.decodeResource(resources,R.drawable.nuxoll);
        nuxoll = Bitmap.createScaledBitmap(nuxoll,(int)(size*2),(int)(size*2),false);
        blackBowl = BitmapFactory.decodeResource(resources,R.drawable.blackbowl);
        blackBowl = Bitmap.createScaledBitmap(blackBowl,(int)(cWidth*.07),(int)(cHeight*.07),false);
        whiteBowl = BitmapFactory.decodeResource(resources,R.drawable.whitebowl);
        whiteBowl = Bitmap.createScaledBitmap(whiteBowl,(int)(cWidth*.07),(int)(cHeight*.07),false);



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

    public void setBoard(int[][] board)
    {
        goBoard = board;
    }

    public void setProposal(int[][] proposal){
        this.proposal=proposal;
    }

    public void onDraw(Canvas c)
    {

        if(firstTime)
        {
            firstTime = false;
            cWidth = c.getWidth();
            cHeight = c.getHeight();
            goStartX = cWidth*.1;
            goStartY = cHeight*.1;
            goEndX = cWidth*.9;
            goEndY = cHeight*.9;
            goBoardWidth = goEndX-goStartX;
            goBoardHeigth = goEndY-goStartY;
            goPieceSize = (int)(goBoardHeigth/GoGameState.boardSize/2);
            initImages(activity);
            this.invalidate();

        }
        super.onDraw(c);
        c.drawBitmap(background,(int)goStartX-50,(int)goStartY-50,null);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(10);

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
                switch (goBoard[i][k])
                {
                    case GoGameState.WHITE:
                        if(drawVegW)
                            c.drawBitmap(vegdahl,nextX-75,nextY-75,null);
                        else if(drawNuxW)
                            c.drawBitmap(nuxoll,nextX-75,nextY-75,null);
                        else
                        {
                            p.setColor(Color.WHITE);
                            c.drawCircle(nextX,nextY,goPieceSize,p);
                        }
                        break;
                    case GoGameState.BLACK:
                        if(drawVegB)
                            c.drawBitmap(vegdahl,nextX-75,nextY-75,null);
                        else if(drawNuxB)
                            c.drawBitmap(nuxoll,nextX-75,nextY-75,null);
                        else
                        {
                            p.setColor(Color.BLACK);
                            c.drawCircle(nextX,nextY,goPieceSize,p);
                        }
                        break;
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

        //draw previous move indicator
        if(prevX!=-1){
            p.setColor(Color.GRAY);
            c.drawCircle((float)(goBoardWidth*prevX/8 +goStartX),
                    (float)(goBoardHeigth*prevY/8+goStartY),goPieceSize/3,p);
        }

        
        p.setColor(0xFF0000FF);

        //draw turn indicator
        if(myTurn){
            if(playerNum==0)
                c.drawBitmap(blackBowl,(float)(0),(float)(cHeight*.93),null);
            else
                c.drawBitmap(whiteBowl,(float)(0),(float)(cHeight*.93),null);
        } else {
            if(playerNum==1)
                c.drawBitmap(blackBowl,(float)(cWidth*.93),(float)(cHeight*.93),null);
            else
                c.drawBitmap(whiteBowl,(float)(cWidth*.93),(float)(cHeight*.93),null);
        }
    }
}
