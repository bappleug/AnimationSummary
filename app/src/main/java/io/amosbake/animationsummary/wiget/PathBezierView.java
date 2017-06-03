package io.amosbake.animationsummary.wiget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import io.amosbake.animationsummary.utils.QuadraticBezierEvaluator;

/**
 * Author: mopel
 * Date : 2016/12/23
 */
public class PathBezierView extends View implements View.OnClickListener {
    private int startPointX;
    private int startPointY;

    private int endPointX;
    private int endPointY;

    private int flagPointX;
    private int flagPointY;

    private int movePointX;
    private int movePointY;

    private Path mPath;
    private Paint mpathPaint;
    private Paint mPointPaint;
    public PathBezierView(Context context) {
        this(context,null);
    }

    public PathBezierView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mpathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpathPaint.setColor(Color.BLUE);
        mpathPaint.setStyle(Paint.Style.STROKE);
        mpathPaint.setStrokeWidth(4);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(Color.RED);
        mPointPaint.setStyle(Paint.Style.FILL);
        this.setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPointX = w/10;
        startPointY = w/10;

        endPointX = w*9/10;
        endPointY = h*9/10;

        movePointX = startPointX;
        movePointY = startPointY;

        flagPointX = endPointX;
        flagPointY = startPointY;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(startPointX,startPointY);
        mPath.quadTo(flagPointX,flagPointY,endPointX,endPointY);
        canvas.drawPath(mPath, mpathPaint);
        canvas.drawCircle(startPointX,startPointY,5,mPointPaint);
        canvas.drawCircle(endPointX,endPointY,5,mPointPaint);
        canvas.drawCircle(movePointX,movePointY,5,mPointPaint);
    }

    @Override
    public void onClick(View v) {
        QuadraticBezierEvaluator _evaluator = new QuadraticBezierEvaluator(new PointF(flagPointX,flagPointY));
        ValueAnimator _valueAnimator = ValueAnimator.ofObject(_evaluator,new PointF(startPointX,startPointY),new PointF(endPointX,endPointY));
        _valueAnimator.setInterpolator(new AccelerateInterpolator());
        _valueAnimator.setDuration(3000);
        _valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF _animatedValue = (PointF) animation.getAnimatedValue();
                movePointX = (int) _animatedValue.x;
                movePointY = (int) _animatedValue.y;
                postInvalidate();
            }
        });
        _valueAnimator.start();
    }
}
