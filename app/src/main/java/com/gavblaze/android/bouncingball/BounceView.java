package com.gavblaze.android.bouncingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class BounceView extends View {
    private int mCx;
    private int mCy;

    private Paint mCirclePaint;

    private Handler mHandler;
    private Runnable mRunnable;
    private static final double GRAVITY = 3;
    private static final int RADIUS = 50;
    private static final int BALL_DIAM = 100;
    private static final double BOUNCE_REDUCE = 0.9;

    private double xVelocity = 8;
    private double yVelocity = 4;


    public BounceView(Context context) {
        super(context);
        init();
    }

    public BounceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BounceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                invalidate();      // cause onDraw() to be called
            }
        };


        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.YELLOW);
        mCirclePaint.setStyle(Paint.Style.FILL);

        //inital position of ball
        mCx = RADIUS;
        mCy = 100;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCx < getWidth() + RADIUS) { // are we at the end of the screen?
            dropBall();                  //if not drop ball and bounce at the bottom
            bounce();
            mCx += xVelocity;            // x velocity remains constant
        }

        canvas.drawCircle(mCx, mCy, RADIUS, mCirclePaint);
        mHandler.postDelayed(mRunnable, 25);
    }


    public void dropBall() {
        yVelocity += GRAVITY; //as the ball falls it gets faster and faster as the velocity value increments by gravity each time
        mCy += yVelocity;
    }

    public void bounce() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

        if (mCy > getHeight() - RADIUS) { //has the ball hit the floor?
            mCirclePaint.setColor(color);  //change color on bounce
            yVelocity = -yVelocity * BOUNCE_REDUCE;    //change the velocity direction and keep 90% of velocity each time
            mCy += yVelocity;
        }
    }
}
