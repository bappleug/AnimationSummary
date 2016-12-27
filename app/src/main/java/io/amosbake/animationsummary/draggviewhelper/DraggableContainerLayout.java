package io.amosbake.animationsummary.draggviewhelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ray on 2016/12/27.
 */

public class DraggableContainerLayout extends LinearLayout {

    private ViewDragHelper mDragger;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;

    private Point mAutoBackOriginPos = new Point();

    public DraggableContainerLayout(Context context) {
        this(context, null);
    }

    public DraggableContainerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggableContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragger = ViewDragHelper.create(this, .001f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if(child == text1){
                    final int leftBound = getPaddingLeft();
                    final int rightBound = getWidth() - child.getWidth() - leftBound;
                    return Math.min(Math.max(left, leftBound), rightBound);
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if(child == text1) {
                    final int topBound = getPaddingTop();
                    final int bottomBound = getHeight() - child.getHeight() - topBound;
                    return Math.min(Math.max(top, topBound), bottomBound);
                }
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                if(child == text2){
                    return getWidth() / 2;
                }
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                if(child == text2){
                    return getHeight() / 2;
                }
                return super.getViewVerticalDragRange(child);
            }

            @Override
            public int getOrderedChildIndex(int index) {
                int topIndex = indexOfChild(text4);
                int indexBottom = indexOfChild(text1);
                return ((index == topIndex) ? indexBottom : index);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild == text3) {
                    mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        text1 = (TextView) getChildAt(0);
        text2 = (TextView) getChildAt(1);
        text3 = (TextView) getChildAt(2);
        text4 = (TextView) getChildAt(3);

        mAutoBackOriginPos.x = text3.getLeft();
        mAutoBackOriginPos.y = text3.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDragger.processTouchEvent(event);
        return true;
    }
}
