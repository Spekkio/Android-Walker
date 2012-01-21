/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.andrewsfirstjointresearch;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 *
 * @author pelle
 */
public class Background extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    public TextView textView;
//public Thread BackgroundThread;

    class BackgroundThread extends Thread
    {
        private Resources res;
        private Handler tHandler;
        private Drawable mog;
        private Timer walkUpdate;
        private walkerTimer walkUpdateTask;
        private Character cMog, cRobot;
        private Drawable mogw1;
        private Drawable mogw2;
        private Drawable mogw3;
        private Drawable mogwu1;
        private Drawable mogwu2;
        private Drawable mogwu3;
        private Drawable mogwr1;
        private Drawable mogwr2;
        private Drawable mogwr3;
        private Drawable mogwl1;
        private Drawable mogwl2;
        private Drawable mogwl3;

        private int mogX = 100;
        private int mogY = 100;
        private int mogW = 33;
        private int mogH = 45;
        private double mogMx=0.0,mogMy=0.0;
        private SurfaceHolder mHolder;
        private boolean goingDown, goRight, goLeft, goUp;
        private boolean running;

        class walkerTimer extends TimerTask
        {
            private int mogw=0;
            private int a;
            public void run()
            {

                if(goingDown)
                {
                    switch(mogw)
                    {
                        case 0: mog=mogwu1; break;
                        case 1: mog=mogwu2; break;
                        case 2: mog=mogwu3; break;
                        case 3: mog=mogwu2; break;
                        default: mog=mogwu2; break;
                    }
                }
                 else if(goUp)
                {
                    switch(mogw)
                    {
                        case 0: mog=mogw1; break;
                        case 1: mog=mogw2; break;
                        case 2: mog=mogw3; break;
                        case 3: mog=mogw2; break;
                        default: mog=mogw2; break;
                    }
                }
                 else if(goRight)
                {
                    switch(mogw)
                    {
                        case 0: mog=mogwr1; break;
                        case 1: mog=mogwr2; break;
                        case 2: mog=mogwr3; break;
                        case 3: mog=mogwr2; break;
                        default: mog=mogwr2; break;
                    }
                }
                 else if(goLeft)
                {
                    switch(mogw)
                    {
                        case 0: mog=mogwl1; break;
                        case 1: mog=mogwl2; break;
                        case 2: mog=mogwl3; break;
                        case 3: mog=mogwl2; break;
                        default: mog=mogwl2; break;
                    }
                }



                mogw+=a;
                if(mogw==4)
                {
                    mogw=0;
                }
            }
            
            public walkerTimer()
            {
                a=0;
            }

            public void pause()
            {
                a=0;
                //mog=mogw2;
            }

            public void walk()
            {
                a=1;
            }
        }

        public void run()
        {
            while(running)
            {
            Canvas c = null;
            try{
                synchronized (mHolder) {
                c = mHolder.lockCanvas(null);
                c.drawRGB(42, 138, 27);
                //c.drawRect((float)mogX, (float)mogX, (float)(mogX+mogW), (float)(mogY+mogH), Paint);

                cMog.setBounds();
                cMog.draw(c);

                cRobot.setBounds();
                cRobot.draw(c);

                mog.setBounds(mogX, mogY, mogX+mogW, mogY+mogH);
                mog.draw(c);

                updatePos();
                cMog.updatePos();
                cRobot.updatePos();
                }
            }
            finally {
                if(c != null)
                    mHolder.unlockCanvasAndPost(c);
            }
            }
        }

        private void updatePos()
        {
            if(goingDown)
            {
                mogY-=1;
            }
            if(goUp)
            {
                mogY+=1;
            }
            if(goRight)
            {
                mogX+=1;
            }
            if(goLeft)
            {
                mogX-=1;
            }
        }

        public boolean doMotion(MotionEvent event)
        {
            int xpos = Double.valueOf(event.getX()).intValue();
            int ypos = Double.valueOf(event.getY()).intValue();
            String sx = Integer.valueOf(xpos).toString();
            String sy = Integer.valueOf(ypos).toString();


                Bundle b = new Bundle();
                Message msg = new Message();
                b.putString("text",sx + ":" + sy);

                msg.setData(b);
                tHandler.sendMessage(msg);

            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                walkUpdateTask.walk();
                cMog.spriteAnimTask.setWalking(true);
                cRobot.spriteAnimTask.setWalking(true);
            }
            if((event.getAction() == MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_MOVE))
            {
                //mogMx = ((mogY)-(ypos))/((mogX)-(xpos));
                //mogMx = ((mogY)-(ypos))/((mogX)-(xpos));

                cMog.decideDirection(xpos, ypos);
                cRobot.decideDirection(xpos, ypos);
                
                if(xpos>mogX)
                {
                    goRight=true;
                    goLeft=false;
                }
                else if(xpos < mogX)
                {
                    goLeft=true;
                    goRight=false;
                }
                if(ypos>mogY)
                {
                    goUp=true;
                    goingDown=false;
                }
                else if(ypos < mogY)
                {
                    goUp=false;
                    goingDown=true;
                }

                if(Math.abs(mogY-ypos) > Math.abs(mogX-xpos))
                {
                    goLeft=false;
                    goRight=false;
                }
                else {
                    goUp=false;
                    goingDown=false;
                }
            }
            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                walkUpdateTask.pause();
                cMog.spriteAnimTask.setWalking(false);
                cRobot.spriteAnimTask.setWalking(false);
                goLeft=false;
                goRight=false;
                goingDown=false;
                goUp=false;
            }

            return true;
        }

        public boolean doKey(int keyCode, KeyEvent event)
        {
            synchronized(mHolder)
            {
                
                Bundle b = new Bundle();
                Message msg = new Message();
                b.putString("text",Integer.valueOf(keyCode).toString() + Integer.valueOf(event.getAction()).toString());

                msg.setData(b);
                tHandler.sendMessage(msg);

                 
                if(event.getAction()==event.ACTION_DOWN)
                {
                    if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
                    {
                        goingDown=true;
                        return true;
                    }
                }
                if(event.getAction()==event.ACTION_UP)
                {
                    if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
                    {
                        goingDown=false;
                        return true;
                    }
                }
            }
            return false;
        }

        public void setRunning(boolean b)
        {
            running = b;
        }

        public void finilize()
        {
            //walkUpdate.cancel();
        }

        public BackgroundThread(SurfaceHolder surfaceHolder, Context context, Handler handler)
        {
            res = context.getResources();
            tHandler = handler;
            mogw1 = res.getDrawable(R.drawable.mogw1);
            mogw2 = res.getDrawable(R.drawable.mogw2);
            mogw3 = res.getDrawable(R.drawable.mogw3);
            mogwu1 = res.getDrawable(R.drawable.mogwu1);
            mogwu2 = res.getDrawable(R.drawable.mogwu2);
            mogwu3 = res.getDrawable(R.drawable.mogwu3);
            mogwl1 = res.getDrawable(R.drawable.mogwl1);
            mogwl2 = res.getDrawable(R.drawable.mogwl2);
            mogwl3 = res.getDrawable(R.drawable.mogwl3);
            mogwr1 = res.getDrawable(R.drawable.mogwr1);
            mogwr2 = res.getDrawable(R.drawable.mogwr2);
            mogwr3 = res.getDrawable(R.drawable.mogwr3);
            mog=mogw1;

            cMog = new Character();
            cRobot = new Character();

            cMog.addSprite(Character.spriteDirection.DOWN,mogw1);
            cMog.addSprite(Character.spriteDirection.DOWN,mogw2);
            cMog.addSprite(Character.spriteDirection.DOWN,mogw3);
            cMog.addSprite(Character.spriteDirection.DOWN,mogw2);
            cMog.addSprite(Character.spriteDirection.UP,mogwu1);
            cMog.addSprite(Character.spriteDirection.UP,mogwu2);
            cMog.addSprite(Character.spriteDirection.UP,mogwu3);
            cMog.addSprite(Character.spriteDirection.UP,mogwu2);
            cMog.addSprite(Character.spriteDirection.LEFT,mogwl1);
            cMog.addSprite(Character.spriteDirection.LEFT,mogwl2);
            cMog.addSprite(Character.spriteDirection.LEFT,mogwl3);
            cMog.addSprite(Character.spriteDirection.LEFT,mogwl2);
            cMog.addSprite(Character.spriteDirection.RIGHT,mogwr1);
            cMog.addSprite(Character.spriteDirection.RIGHT,mogwr2);
            cMog.addSprite(Character.spriteDirection.RIGHT,mogwr3);
            cMog.addSprite(Character.spriteDirection.RIGHT,mogwr2);
            cMog.maxSprite=4;
            cMog.setPos(150,300);
            cMog.setSize(33, 45);
            cMog.setStartSprite(mogw1);

            cRobot.addSprite(Character.spriteDirection.DOWN, res.getDrawable(R.drawable.robotwd1));
            cRobot.addSprite(Character.spriteDirection.DOWN, res.getDrawable(R.drawable.robotwd2));
            cRobot.addSprite(Character.spriteDirection.DOWN, res.getDrawable(R.drawable.robotwd3));
            cRobot.addSprite(Character.spriteDirection.DOWN, res.getDrawable(R.drawable.robotwd4));
            cRobot.addSprite(Character.spriteDirection.DOWN, res.getDrawable(R.drawable.robotwd5));
            cRobot.addSprite(Character.spriteDirection.DOWN, res.getDrawable(R.drawable.robotwd6));
            cRobot.addSprite(Character.spriteDirection.UP, res.getDrawable(R.drawable.robotwu1));
            cRobot.addSprite(Character.spriteDirection.UP, res.getDrawable(R.drawable.robotwu2));
            cRobot.addSprite(Character.spriteDirection.UP, res.getDrawable(R.drawable.robotwu3));
            cRobot.addSprite(Character.spriteDirection.UP, res.getDrawable(R.drawable.robotwu4));
            cRobot.addSprite(Character.spriteDirection.UP, res.getDrawable(R.drawable.robotwu5));
            cRobot.addSprite(Character.spriteDirection.UP, res.getDrawable(R.drawable.robotwu6));
            cRobot.addSprite(Character.spriteDirection.LEFT, res.getDrawable(R.drawable.robotwl1));
            cRobot.addSprite(Character.spriteDirection.LEFT, res.getDrawable(R.drawable.robotwl2));
            cRobot.addSprite(Character.spriteDirection.LEFT, res.getDrawable(R.drawable.robotwl3));
            cRobot.addSprite(Character.spriteDirection.LEFT, res.getDrawable(R.drawable.robotwl4));
            cRobot.addSprite(Character.spriteDirection.LEFT, res.getDrawable(R.drawable.robotwl5));
            cRobot.addSprite(Character.spriteDirection.LEFT, res.getDrawable(R.drawable.robotwl6));
            cRobot.addSprite(Character.spriteDirection.RIGHT, res.getDrawable(R.drawable.robotwr1));
            cRobot.addSprite(Character.spriteDirection.RIGHT, res.getDrawable(R.drawable.robotwr2));
            cRobot.addSprite(Character.spriteDirection.RIGHT, res.getDrawable(R.drawable.robotwr3));
            cRobot.addSprite(Character.spriteDirection.RIGHT, res.getDrawable(R.drawable.robotwr4));
            cRobot.addSprite(Character.spriteDirection.RIGHT, res.getDrawable(R.drawable.robotwr5));
            cRobot.addSprite(Character.spriteDirection.RIGHT, res.getDrawable(R.drawable.robotwr6));
            cRobot.maxSprite=6;
            cRobot.setPos(250, 250);
            cRobot.setSize(56, 64);
            cRobot.setStartSprite(res.getDrawable(R.drawable.robotwd1));

            mHolder = surfaceHolder;
            running = true;

            walkUpdateTask = new walkerTimer();
            walkUpdate = new Timer();
            walkUpdate.scheduleAtFixedRate(walkUpdateTask, (long)0, (long)150);
        }
    }

    BackgroundThread bThread;


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //return thread.doKeyUp(keyCode, msg);
        return bThread.doKey(keyCode,event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //textView.setText(Integer.valueOf(keyCode).toString());
        return bThread.doKey(keyCode,event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event)
    {
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event)
    {
        return bThread.doKey(keyCode,event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        return bThread.doMotion(event);
    }

    public void surfaceDestroyed(android.view.SurfaceHolder holder)
    {
        bThread.setRunning(false);
        bThread.walkUpdate.cancel();
        bThread.stop();
        bThread.suspend();
    }

    public void surfaceChanged(android.view.SurfaceHolder holder,int format,int width,int height)
    {
        
    }

    public void surfaceCreated(android.view.SurfaceHolder holder)
    {
        bThread.start();
    }

    public void setText(TextView txt)
    {
        textView = txt;
    }



    public Background(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        

        holder = this.getHolder();
        holder.addCallback(this);
        
        Resources res = context.getResources();
        bThread = new BackgroundThread(holder,context,new Handler()
        {
            @Override
            public void handleMessage(Message m)
            {
                textView.setVisibility(View.VISIBLE);
                textView.setText(m.getData().getString("text"));

                //textView.setText("test");
            }

        });
        
        this.setFocusable(true);
        
    }
 
 
}
