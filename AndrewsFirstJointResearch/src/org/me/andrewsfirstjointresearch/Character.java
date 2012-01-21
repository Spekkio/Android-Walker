/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.andrewsfirstjointresearch;

import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.Timer;
/**
 *
 * @author pelle
 */
public class Character {

    public enum spriteDirection {UP,DOWN,LEFT,RIGHT};
    public boolean goUp,goDown,goLeft,goRight;
    Drawable currentSprite;
    AnimationDrawable currentAnim;
    LinkedList<Drawable> upSprite, downSprite, leftSprite, rightSprite;

    AnimationDrawable upAnim, downAnim, leftAnim, rightAnim;

    Timer spriteAnimationTimer;
    int cx,cy,cw,ch;
    spriteDirection currentDirection;

    public int maxSprite;

    public class SpriteAnimation extends TimerTask
    {
        int spriteIndex;
        int step;

        public void run()
        {
            if(goUp)
            {
                currentSprite=upSprite.get(spriteIndex);
            }
            else if(goDown)
            {
                currentSprite=downSprite.get(spriteIndex);
            }
            else if(goLeft)
            {
                currentSprite=leftSprite.get(spriteIndex);
            }
            else if(goRight)
            {
                currentSprite=rightSprite.get(spriteIndex);
            }

            spriteIndex+=step;

            if(spriteIndex==maxSprite)
            {
                spriteIndex=0;
            }
        }
        
        public void setWalking(boolean b)
        {
            if(b)
            {
                step=1;
            }
            else
            {
                step=0;
                goDown=false;
                goUp=false;
                goLeft=false;
                goRight=false;
            }
        }

        public SpriteAnimation()
        {
            spriteIndex=0;
            step=0;
        }
    }

    public void setPos(int x, int y)
    {
        cx=x;
        cy=y;
    }

    public void setBounds()
    {
        currentSprite.setBounds(cx, cy, cx+cw, cy+ch);
    }

    public void setSize(int w, int h)
    {
        cw=w;
        ch=h;
    }

    public SpriteAnimation spriteAnimTask;

    public void draw(Canvas c)
    {
        synchronized(currentSprite)
        {
            currentSprite.draw(c);
        }
    }

    public void addSprite(spriteDirection direction, Drawable sprite)
    {
        switch(direction)
        {
            case UP:
                upSprite.add(sprite);
                break;
            case DOWN:
                downSprite.add(sprite);
                break;
            case LEFT:
                leftSprite.add(sprite);
                break;
            case RIGHT:
                rightSprite.add(sprite);
                break;
            default: break;
        }
    }
    
    public void updatePos()
    {
        if(goDown)
        {
            cy+=1;
        }
        if(goUp)
        {
            cy-=1;
        }
        if(goRight)
        {
            cx+=1;
        }
        if(goLeft)
        {
            cx-=1;
        }
    }

    public void decideDirection(int xpos, int ypos)
    {
                if(xpos>cx)
                {
                    goRight=true;
                    goLeft=false;
                }
                else if(xpos < cx)
                {
                    goLeft=true;
                    goRight=false;
                }
                if(ypos>cy)
                {
                    goUp=false;
                    goDown=true;
                }
                else if(ypos < cy)
                {
                    goUp=true;
                    goDown=false;
                }

                if(Math.abs(cy-ypos) > Math.abs(cy-xpos))
                {
                    goLeft=false;
                    goRight=false;
                }
                else {
                    goUp=false;
                    goDown=false;
                }
    }

    public void setDirection(spriteDirection c)
    {
        currentDirection=c;
    }

    public void setStartSprite(Drawable c)
    {
        currentSprite=c;
    }

    public Character()
    {
        upSprite = new LinkedList<Drawable>();
        downSprite = new LinkedList<Drawable>();
        leftSprite = new LinkedList<Drawable>();
        rightSprite = new LinkedList<Drawable>();

        /*setup the animation timer*/
        spriteAnimTask = new SpriteAnimation();
        spriteAnimationTimer = new Timer();
        spriteAnimationTimer.scheduleAtFixedRate(spriteAnimTask, (long)0, (long)150);
    }
}
