package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by 김새미루 on 2016-07-20.
 */
public class HorizontalViewPager extends ViewPager {


    private float mLastMotionX;
    private float mLastMotionY;

    private boolean enabled;

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /*
        final float x = ev.getRawX();
        final float y = ev.getRawY();

        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float xDiff = Math.abs(x - mLastMotionX);
                final float yDiff = Math.abs(y - mLastMotionY);
                if (xDiff > yDiff) { // Swiping left and right
                    this.enabled = true;
                    return this.enabled;
                } else if (yDiff > xDiff) { //Swiping up and down
                    this.enabled = false;
                    return  this.enabled;
                }
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            }
        }
        return true;
        */

        // Set both flags to false in case user lifted finger in the parent view pager

        try {
            if (this.enabled) {
                return super.onTouchEvent(ev);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.e("INFO", exceptionAsString);
        }
        return false;

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public void setHorizontalEnabled() {
        this.enabled = true;
    }

    public void setHorizontalDisabled() {
        this.enabled = false;
    }



    /*
    private GestureDetector xScrollDetector;

    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
        xScrollDetector = new GestureDetector(getContext(), new XScrollDetector());

    }

    class XScrollDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceX) > Math.abs(distanceY);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(xScrollDetector.onTouchEvent(ev)){
            super.onInterceptTouchEvent(ev);
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
    */


}
