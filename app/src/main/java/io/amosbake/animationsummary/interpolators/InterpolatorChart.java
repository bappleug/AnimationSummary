package io.amosbake.animationsummary.interpolators;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by Ray on 2017/4/27.
 */

public class InterpolatorChart extends View {

    protected static int PADDING_TOP_DP = 8;
    protected static int PADDING_OTHER_DP = 8;
    protected static int AXIS_COLOR = Color.BLACK;
    protected static int LINE_COLOR = Color.DKGRAY;
    protected static int TEXT_COLOR = Color.BLACK;
    protected static int Y_AXIS_BALL_COLOR = Color.BLUE;
    protected static int LINE_BALL_COLOR = Color.RED;
    protected static int Y_BALL_RADIUS_DP = 8;
    protected static int CURVE_BALL_RADIUS_DP = 1;
    protected static int LINE_BALL_RADIUS_DP = 2;
    protected static int AXIS_WIDTH_DP = 1;
    protected static int LINE_WIDTH_DP = 1;
    protected static int TEXT_SIZE_SP = 14;
    protected static final String Y_AXIS_TEXT = "Y-Axis";
    protected static final String X_AXIS_TEXT = "Time";
    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator();

    private Interpolator mInterpolator;

    protected Rect mAxisRect;
    protected Paint mAxisPaint;
    protected Paint mLinePaint;
    protected Paint mTextPaint;
    protected Paint mYBallPaint;
    protected Paint mLineBallPaint;
    protected int mWidth;
    protected int mHeight;
    protected int mChartHeight;
    protected int mChartWidth;
    protected int mYAxisTextWidth;
    protected int mTextHeight;
    protected int mPaddingOther;
    protected int mPaddingTop;
    protected float mValue = 0;
    protected float mTime = 0;
    protected boolean showStatic = true;
    protected boolean showSampleDots = false;
    protected boolean showAnimate = false;

    private ValueAnimator mAnimator;
    private int duration = 2000;
    private boolean repeat = true;
    private boolean showDots = true;

    public InterpolatorChart(Context context) {
        super(context);
        init();
    }

    public InterpolatorChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InterpolatorChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setInterpolator(Interpolator interpolator){
        this.mInterpolator = interpolator;
    }

    public void changeInterpolator(Interpolator interpolator){
        this.mInterpolator = interpolator;
        showAnimChart(duration, repeat, showDots);
        invalidate();
    }

    public void showStaticChart(){
        showStatic = true;
        showSampleDots = false;
        showAnimate = false;
        invalidate();
    }

    public void showStaticDots(){
        showStatic = true;
        showSampleDots = true;
        showAnimate = false;
        invalidate();
    }

    public void showAnimChart(){
        showAnimChart(2000, false, false);
    }

    public void showAnimateDots(){
        showAnimChart(2000, false, true);
    }

    public void showAnimChart(final int duration, boolean repeat, boolean showDots){
        if(mInterpolator == null){
            return;
        }
        this.duration = duration;
        this.repeat = repeat;
        this.showDots = showDots;
        if(mAnimator != null && mAnimator.isRunning()){
            mAnimator.cancel();
        }
        mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.setDuration(duration);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(repeat ? ValueAnimator.INFINITE : 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValue = (float) animation.getAnimatedValue();
                mTime = (float) ((double)(animation.getCurrentPlayTime() % duration) / duration);
//                Log.d("InterpolatorChart", "Time: " + mTime + ", value: " + animation.getAnimatedValue() + ", interpolation: " + mInterpolator.getInterpolation(mTime));
                invalidate();
            }
        });
        showSampleDots = showDots;
        showAnimate = true;
        showStatic = false;
        mAnimator.start();
    }

    private void init(){
        mInterpolator = DEFAULT_INTERPOLATOR;
        mAxisPaint = new Paint();
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStrokeCap(Paint.Cap.ROUND);
        mAxisPaint.setColor(AXIS_COLOR);
        mAxisPaint.setStrokeWidth(DensityUtils.dip2px(getContext(), AXIS_WIDTH_DP));
        mAxisPaint.setStyle(Paint.Style.STROKE);
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(LINE_COLOR);
        mLinePaint.setStrokeWidth(DensityUtils.dip2px(getContext(), LINE_WIDTH_DP));
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DensityUtils.dip2px(getContext(), TEXT_SIZE_SP));
        mTextPaint.setColor(TEXT_COLOR);
        mLineBallPaint = new Paint();
        mLineBallPaint.setAntiAlias(true);
        mLineBallPaint.setColor(LINE_BALL_COLOR);
        mYBallPaint = new Paint();
        mYBallPaint.setAntiAlias(true);
        mYBallPaint.setColor(Y_AXIS_BALL_COLOR);
        mPaddingOther = DensityUtils.dip2px(getContext(), PADDING_OTHER_DP);
        mPaddingTop = DensityUtils.dip2px(getContext(), PADDING_TOP_DP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mYAxisTextWidth = DensityUtils.dip2px(getContext(), Y_AXIS_TEXT.length() / 2 * TEXT_SIZE_SP + 8);
        mTextHeight = DensityUtils.dip2px(getContext(), TEXT_SIZE_SP + 8);
        mChartHeight = mHeight - mPaddingTop - mPaddingOther - mTextHeight;
        mChartWidth = mWidth - mPaddingOther * 2 - mYAxisTextWidth;
        mAxisRect = new Rect(
                mYAxisTextWidth + mPaddingOther,
                mPaddingTop,
                mWidth - mPaddingOther,
                mHeight - mTextHeight - mPaddingOther);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mWidth == 0 || mHeight == 0){
            return;
        }
        drawAxis(canvas);
        drawText(canvas);
        if(showStatic){
            drawCurve(canvas);
        } else if(showAnimate) {
            drawCurveAndBalls(canvas);
        }
    }

    private void drawAxis(Canvas canvas){
        canvas.drawRect(mAxisRect, mAxisPaint);
    }

    private void drawText(Canvas canvas){
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(Y_AXIS_TEXT, mPaddingOther, mAxisRect.height() / 2 + mPaddingTop, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(X_AXIS_TEXT, (mAxisRect.right + mAxisRect.left) / 2, mHeight - mPaddingOther, mTextPaint);
    }

    private void drawCurve(Canvas canvas) {
        if(mInterpolator == null){
            return;
        }
        float stepX1 = mAxisRect.left;
        float stepY1 = mAxisRect.bottom;
        for(int i = 1; i <= 100; i++){
            float value = mInterpolator.getInterpolation(i / 100.0f);
            float stepX2 =  mAxisRect.left + mChartWidth / 100.0f * i;
            float stepY2 =  mAxisRect.bottom - mChartHeight * value;
            if(showSampleDots){
                canvas.drawCircle(stepX2, stepY2, DensityUtils.dip2px(getContext(), CURVE_BALL_RADIUS_DP), mLineBallPaint);
            } else {
                canvas.drawLine(stepX1, stepY1, stepX2, stepY2, mLinePaint);
            }
            stepX1 = stepX2;
            stepY1 = stepY2;
        }
    }

    private void drawCurveAndBalls(Canvas canvas) {
        float stepX1 = mAxisRect.left;
        float stepY1 = mAxisRect.bottom;
        for(int i = 1; i <= 100 * mTime; i++){
            float value = mInterpolator.getInterpolation(i / 100.0f);
            float stepX2 =  mAxisRect.left + mChartWidth / 100.0f * i;
            float stepY2 =  mAxisRect.bottom - mChartHeight * value;
            if(showSampleDots){
                canvas.drawCircle(stepX2, stepY2, DensityUtils.dip2px(getContext(), CURVE_BALL_RADIUS_DP), mLineBallPaint);
            } else {
                canvas.drawLine(stepX1, stepY1, stepX2, stepY2, mLinePaint);
            }
            stepX1 = stepX2;
            stepY1 = stepY2;
        }
        canvas.drawCircle(mAxisRect.left, mAxisRect.bottom - mValue * mChartHeight, DensityUtils.dip2px(getContext(), Y_BALL_RADIUS_DP), mYBallPaint);
        canvas.drawCircle(mAxisRect.left + mChartWidth * mTime, mAxisRect.bottom - mValue * mChartHeight, DensityUtils.dip2px(getContext(), LINE_BALL_RADIUS_DP), mLineBallPaint);
    }

    public void stop() {
        if(mAnimator != null){
            mAnimator.cancel();
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
