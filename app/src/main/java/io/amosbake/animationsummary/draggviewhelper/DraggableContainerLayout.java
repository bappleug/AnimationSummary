package io.amosbake.animationsummary.draggviewhelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by Ray on 2016/12/27.
 */

public class DraggableContainerLayout extends FlexboxLayout {

    private ViewDragHelper mDragger;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text8;
    private TextView text9;

    private Point mAutoBackOriginPos = new Point();

    public DraggableContainerLayout(Context context) {
        this(context, null);
    }

    public DraggableContainerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggableContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragger = ViewDragHelper.
            /**
             * 第一个参数是父Layout本身，
             * 第二个参数是敏感度，一般就用1.0f表示使用系统的默认值，
             * 最后一个参数是回调
             */
            create(this, .001f, new ViewDragHelper.Callback() {
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    return child != text3;
                }

                /**
                 * 限制子View的横向移动位置，left和dx表示的是子View希望的移动到的位置，而返回的是实际允许移动到的位置
                 * child 子View
                 * left 子View的新左边缘位置
                 * dx 子View的
                 */
                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    if(child == text1){
                        final int leftBound = getPaddingLeft();
                        final int rightBound = getWidth() - child.getWidth() - leftBound;
                        return Math.min(Math.max(left, leftBound), rightBound);
                    }
                    return left;
                }

                /**
                 * 限制子View纵向移动，同横向
                 */
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

                /**
                 * 按照你想指定的顺序决定哪个子View优先被捕捉。
                 * 每次尝试捕捉子View时这个方法都会按index从getChildCount() - 1到0的顺序被循环访问。
                 * 先被返回index会被先处理，处于该位置的子View将有优先被捕捉的权利。
                 * 所以如果需要更改View的捕捉优先级，则要将传进来的index映射成你想要的index。
                 * <br/>例如：
                 * <br/>含有4个子View时，index会按3、2、1、0的顺序传入，如果按如下映射就可以让实际index为0的子View被优先捕捉：
                 * <br/>3->0
                 * <br/>2->3
                 * <br/>1->2
                 * <br/>0->1
                 * @param index 子View的实际index
                 * @return 重新排序后的index
                 */
                @Override
                public int getOrderedChildIndex(int index) {
                    int[] orderedIndex = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1};
                    return orderedIndex[index];
                }

                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    //mAutoBackView手指释放时可以自动回去
                    if (releasedChild == text4) {
                        mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                        postInvalidate();
                        postInvalidateOnAnimation();
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
        text5 = (TextView) getChildAt(4);
        text6 = (TextView) getChildAt(5);
        text7 = (TextView) getChildAt(6);
        text8 = (TextView) getChildAt(7);
        text9 = (TextView) getChildAt(8);

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
