package io.amosbake.animationsummary.draggviewhelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
    private Button textBtn6;

    /**
     * 用continueSetting()方法绘制归位动画时，如果其它View被捕捉，动画会停止。
     * 所以用此变量标记text4的归位动画正在执行，此时禁止动画时其它view被捕捉。
     */
    private boolean positionHolding = false;

    private Point mAutoBackOriginPos = new Point();

    public DraggableContainerLayout(Context context) {
        this(context, null);
    }

    public DraggableContainerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggableContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 第一个参数是父Layout本身，
         * 第二个参数是敏感度，一般就用1.0f表示使用系统的默认值，
         * 最后一个参数是回调
         */
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * 尝试捕捉View，返回true表示此View可以被捕捉到。
             * @param child 子View
             * @param pointerId 触碰指针
             * @return 是否可以被捕捉
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if(positionHolding){    //text4的归位动画正在执行
                    return child == text4;
                }
                return child != text3;
            }

            /**
             * 限制子View的横向移动位置，left和dx表示的是子View希望的移动到的位置，而返回的是实际允许移动到的位置
             * @param child 子View
             * @param left 子View的新左边缘位置
             * @param dx 子View的
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == text1) {
                    final int leftBound = getPaddingLeft();
                    final int rightBound = getWidth() - child.getWidth() - leftBound - getPaddingRight();
                    return Math.min(Math.max(left, leftBound), rightBound);
                }
                return left;
            }

            /**
             * 限制子View纵向移动，同横向
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (child == text1) {
                    final int topBound = getPaddingTop();
                    final int bottomBound = getHeight() - child.getHeight() - topBound;
                    return Math.min(Math.max(top, topBound), bottomBound);
                }
                return top;
            }

            /**
             * 如果子View本身会消耗触碰事件（例如Button），则需要此方法和下面方法返回大于0的值来让它可以被拖拽。
             * 默认状态（此方法返回0）时是不能被拖拽的。
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                if (child == textBtn6) {
                    return 1;
                }
                return super.getViewHorizontalDragRange(child);
            }

            /**
             * 同getViewHorizontalDragRange(View child)
             */
            @Override
            public int getViewVerticalDragRange(View child) {
                if (child == textBtn6) {
                    return 0;
                }
                return super.getViewVerticalDragRange(child);
            }

            /**
             * 用此方法可以指定一个顺序，决定哪个子View优先被捕捉。
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
                int[] orderedIndex = {0, 2, 3, 4, 5, 6, 7, 8, 1};
                return orderedIndex[index];
            }

            /**
             * 在控件被释放时触发
             * @param releasedChild 释放的View
             * @param xvel 释放时的x速度
             * @param yvel 释放时的y速度
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //text4手指释放时可以自动归位。
                //在释放时调用dragger.settleCapturedViewAt()指定目的坐标，接着调用invalidate()。
                //覆写computeScroll()方法，调用dragger.conintueSettling(true)，接着调用invalidate()。
                if (releasedChild == text4) {
                    if (mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y)) {
                        positionHolding = true;
                        invalidate();
                    }
                }
            }

            /**
             * 此方法可以用来过滤部分边缘滑动。<br/>
             * 过滤的类型为：平行于边缘的滑动分量大于垂直于边缘的滑动分量两倍时的滑动
             * @param edgeFlags 边缘类型
             * @return 返回true表示过滤
             */
            @Override
            public boolean onEdgeLock(int edgeFlags) {
                if(edgeFlags == ViewDragHelper.EDGE_LEFT){
                    return false;
                }
                return super.onEdgeLock(edgeFlags);
            }

            /**
             * 边缘拖动开始回调，可以在内部来抓取子View
             * @param edgeFlags 边界类型，可以为ViewDragHelper.EDGE_TOP/EDGE_LEFT/EDGE_BOTTOM/EDGE_RIGHT
             * @param pointerId 触碰指针
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                    mDragger.captureChildView(text5, pointerId);
            }

            /**
             * 当某个边缘触碰监听
             */
            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            /**
             * 子View抓取监听
             */
            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            /**
             * 子View状态改变监听
             */
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            /**
             * 子view位置改变监听
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }
        });
        //开启顶部边缘触碰检测
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP | ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        } else {
            positionHolding = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        text1 = (TextView) getChildAt(0);
        text2 = (TextView) getChildAt(1);
        text3 = (TextView) getChildAt(2);
        text4 = (TextView) getChildAt(3);
        text5 = (TextView) getChildAt(4);
        textBtn6 = (Button) getChildAt(5);

        mAutoBackOriginPos.x = text4.getLeft();
        mAutoBackOriginPos.y = text4.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }
}
