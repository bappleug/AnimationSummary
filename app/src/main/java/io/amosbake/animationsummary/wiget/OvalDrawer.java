package io.amosbake.animationsummary.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ray on 2017/1/4.
 */

public class OvalDrawer extends View {

    Path ovalPath;
    Paint ovalPaint;

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

        ovalPath = new Path();
        ovalPath.addOval(new RectF(0, 0, 300, 200), Path.Direction.CW);
        ovalPath.setFillType(Path.FillType.WINDING);
        ovalPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2 - 150, getHeight() / 2 - 100);
        canvas.drawPath(ovalPath, ovalPaint);
    }

    public void rotate(float degree) {
        PathMeasure pathMeasure = new PathMeasure(ovalPath, false);
        Matrix matrix = new Matrix();
        pathMeasure.getMatrix(pathMeasure.getLength(), matrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.setRotate(degree, 150, 100);
        ovalPath.transform(matrix);
        invalidate();
    }
}
