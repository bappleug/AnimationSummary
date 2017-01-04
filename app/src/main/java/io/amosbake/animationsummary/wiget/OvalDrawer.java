package io.amosbake.animationsummary.wiget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.*;

/**
 * Created by Ray on 2017/1/4.
 */

public class OvalDrawer extends View {

    Path ovalPath;
    Paint ovalPaint;
    Paint circalPaint;
    PathMeasure pathMeasure;
    ValueAnimator animator;
    Matrix matrix;
    float[] position = new float[2];

    public OvalDrawer(Context context) {
        this(context, null);
    }

    public OvalDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OvalDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ovalPaint = new Paint();
        ovalPaint.setAntiAlias(true);
        ovalPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        ovalPaint.setStyle(Paint.Style.STROKE);
        ovalPaint.setStrokeWidth(5);
        ovalPaint.setColor(Color.BLACK);

        circalPaint = new Paint();
        circalPaint.setAntiAlias(true);
        circalPaint.setColor(Color.BLUE);
        circalPaint.setStyle(Paint.Style.FILL);

        ovalPath = new Path();
        ovalPath.addOval(new RectF(0, 0, 300, 200), Path.Direction.CW);
        ovalPath.setFillType(Path.FillType.WINDING);
        ovalPath.close();

        pathMeasure = new PathMeasure(ovalPath, false);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2 - 150, getHeight() / 2 - 100);
        canvas.drawPath(ovalPath, ovalPaint);
        canvas.drawCircle(position[0], position[1], 12, circalPaint);
        startAnim(5000);
    }

    public void rotate(float degree) {
        pathMeasure.getMatrix(pathMeasure.getLength(), matrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.setRotate(degree, 150, 100);
        ovalPath.transform(matrix);
        pathMeasure.setPath(ovalPath, false);
        invalidate();
    }

    public void startAnim(long duration){
        if(animator != null){
            return;
        }
        animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        animator.setDuration(duration);
        animator.setInterpolator(new OvalInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                pathMeasure.getPosTan(value, position, null);
                postInvalidate();
            }
        });
        animator.start();
    }


    private class OvalInterpolator implements android.view.animation.Interpolator {

        @Override
        public float getInterpolation(float input) {
            return (float) (2 * input + (Math.sin(4 * Math.PI * (input - 0.25f)) / 4 / Math.PI)) / 2;
        }
    }
}
