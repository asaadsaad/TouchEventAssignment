package com.saad.asaad.toucheventassignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by asaad on 12/7/2015.
 */
public class GameView extends SurfaceView
{
    private SurfaceHolder holder;

    private GameThread thread = null;
    private Bitmap ballGreen;
    private Bitmap ballRed;
    private Bitmap instructions;
    private boolean currentBall;
    private int scoreSuccess = 0;
    private int scoreFail = 0;
    private Paint scorePaint;
    private Random ballRandomizer;
    private float x1,x2;


    public GameView(Context context)
    {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                ballGreen = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
                ballRed = BitmapFactory.decodeResource(getResources(), R.drawable.ball_red);
                instructions = BitmapFactory.decodeResource(getResources(), R.drawable.instructions);
                ballRandomizer = new Random();

                scorePaint = new Paint();
                scorePaint.setTextSize(50.0f);
                scorePaint.setColor(Color.BLACK);
                makeThread();
                thread.setRunning(true);
                thread.start();

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }});
    }// end of constructor

    public void makeThread()
    {
        thread = new GameThread(this);

    }

    public void killThread()
    {
        boolean retry = true;
        thread.setRunning(false);
        while(retry)
        {
            try
            {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e){}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = e.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = e.getX();
                if (x1 > x2) {
                    if(currentBall==false) scoreSuccess++; else scoreFail++;
                }else {
                    if(currentBall==true) scoreSuccess++; else scoreFail++;
                }
                newBall();
                break;
        }


        return true;
    }

    public void newBall()
    {
        if(ballRandomizer.nextInt() %2 ==0) currentBall=true; else currentBall=false;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("Success: " + String.valueOf(scoreSuccess), 10.0f, 50.0f, scorePaint);
        canvas.drawText("Failures: " + String.valueOf(scoreFail), 10.0f, 150.0f, scorePaint);
        canvas.drawBitmap(instructions, 30.0f, (getHeight()) - 128, null);
        if(currentBall)
            canvas.drawBitmap(ballGreen, (getWidth() / 2) - 125, (getHeight() / 2) - 125, null);
        else
            canvas.drawBitmap(ballRed, (getWidth() / 2) - 125, (getHeight() / 2) - 125, null);
    }

    public void onDestroy()
    {
        ballGreen.recycle();
        ballRed.recycle();
        ballGreen=null;
        ballRed=null;
        System.gc();
    }
}
