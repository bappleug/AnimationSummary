package io.amosbake.animationsummary.paint;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lexing.common.utils.DensityUtils;

/**
 * Created by Ray on 2017/8/8.
 */

public class PaintView extends View {

    Paint paintSrc = new Paint();
    Paint paintDest = new Paint();
    Paint paintMix = new Paint();
    Paint linePaint = new Paint();
    Canvas canvasSrc = new Canvas();
    Canvas canvasDst = new Canvas();
    Bitmap bitmapSrc;
    Bitmap bitmapDst;
    Matrix matrix;
    int srcHeight;
    int srcWidth;
    int dstHeight;
    int dstWidth;
    int mixHeight;
    int mixWidth;
    PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.ADD);

    public PaintView(Context context) {
        super(context);
        init();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        matrix = new Matrix();
        paintSrc.setColor(0xFF318BF4);
        paintDest.setColor(0xFFE70055);
        linePaint.setColor(0xFF999999);
        linePaint.setStrokeWidth(dip2px(2));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mixWidth = getWidth();
        mixHeight = getHeight() * 2 / 3;
        bitmapSrc = Bitmap.createBitmap(mixWidth / 2, getHeight() - mixHeight, Bitmap.Config.ARGB_8888);
        bitmapDst = Bitmap.createBitmap(getWidth() - mixWidth / 2, getHeight() - mixHeight, Bitmap.Config.ARGB_8888);
        canvasSrc.setBitmap(bitmapSrc);
        canvasDst.setBitmap(bitmapDst);
        matrix.setScale(2.0f, 2.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSrc(canvasSrc, paintSrc);
        drawDest(canvasDst, paintDest);

        canvas.drawBitmap(bitmapSrc, 0, 0, null);
        canvas.drawBitmap(bitmapDst, mixWidth / 2, 0, null);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.translate(0, bitmapSrc.getHeight());

        canvas.drawBitmap(bitmapSrc, matrix, paintSrc);
        paintDest.setXfermode(xfermode);
        canvas.drawBitmap(bitmapDst, matrix, paintDest);
        paintDest.setXfermode(null);

        canvas.restoreToCount(saved);

        canvas.drawLine(0, bitmapSrc.getHeight(), getWidth(), bitmapSrc.getHeight(), linePaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, bitmapSrc.getHeight(), linePaint);
    }

    private void drawSrc(Canvas canvas, Paint paint) {
        canvas.drawRect(dip2px(20), dip2px(60), dip2px(20 + 60), dip2px(60 + 60), paint);
    }

    private void drawDest(Canvas canvas, Paint paint) {
        canvas.drawCircle(dip2px(20 + 60), dip2px(60), dip2px(30), paint);
    }

    public void setXferMode(PorterDuffXfermode xfermode) {
        this.xfermode = xfermode;
        invalidate();
    }

    private float dip2px(int dip) {
        return DensityUtils.dip2px(getContext(), dip);
    }
}
