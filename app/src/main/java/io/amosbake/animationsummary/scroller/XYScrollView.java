package io.amosbake.animationsummary.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by my on 2017/1/1 0001.
 */

public class XYScrollView extends FrameLayout {

    Scroller mScroller;
    VelocityTracker mVTracker;
    int mDownX;
    int mDownY;
    int mLastMoveX;
    int mLastMoveY;
    int mTouchSlop;

    public XYScrollView(Context context) {
        this(context, null);
    }

    public XYScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XYScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getRawX();
                mDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float diff = Math.max(Math.abs(mDownX - ev.getRawX()), Math.abs(mDownY - ev.getRawY()));
                if(diff > mTouchSlop){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVTracker.addMovement(event);
                int scrollX = (int) (event.getRawX() - mDownX);
                if(scrollX + getScrollX() < 0 ){

                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mVTracker.recycle();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }
}
