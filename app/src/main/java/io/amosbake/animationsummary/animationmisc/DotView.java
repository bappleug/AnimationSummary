package io.amosbake.animationsummary.animationmisc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ray on 2017/6/2.
 */

public class DotView extends View {

    private int radius;
    private Paint mPaint;

    public DotView(Context context) {
        super(context);
        init();
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = Math.min(getWidth() / 2, getHeight() / 2);
    }

    public void setRadius(int radius){
        this.radius = radius;
        invalidate();
    }
}
