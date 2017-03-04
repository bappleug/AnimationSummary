package io.amosbake.animationsummary.wiget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;

/**
 * Author: mopel
 * Date : 2017/1/12
 */
public class WaveTextDrawHelper {
    public static final int DEFAULT_LENGTH = 500;
    public static final int DEFAULT_HEIGHT = 150;
    public static final int COLOR_GRADIENT_START = 0xff00d8ff;
    public static final int COLOR_GRADIENT_END = 0xff6600ff;
    /**
     * 文字,图像定位相关
     **/
    private int mWidth;
    private float centerPointX;
    /**
     * 绘制文字和图画的画笔
     */
    private Paint mScorePaint;
    private Paint mTitlePaint;
    private Paint mPercentMarkPaint;
    private Shader textPaintShader;//产生波浪效果的Shader
    /**
     * 待绘制文本
     */
    private String scoreText;
    private String titleText;
    private String measureText;//测量文本位置
    private int colorGradientStart = COLOR_GRADIENT_START;
    private int colorGradientEnd = COLOR_GRADIENT_END;
    private int colorScore = 0xBBFFFFFF;
    private int colorTitle = Color.RED;
    private int colorPercentMark = Color.GREEN;
    /**
     * 分数文本的绘制区域大小
     */
    private int textLength;
    private int textHeight;
    /**
     * 1位数 2位数 的测量结果
     **/
    private int SinglePositionX;
    private int TweenPositionX;
    private int TweenPositionY;
    /**
     * 4位数 的测量结果(极限情况)
     **/
    private int maxPositionX;
    private int maxPositionY;
    /**
     * 波浪移动 ->  不断右移
     **/
    private int stepValue;
    private int transitionX;
    /**
     * 波浪高度变幻
     **/
    private float scaleY;
    private Matrix mMatrix;
    /**
     * 底部文字的位置
     */
    private float titleMargin;
    private float titlePositionX;
    /**
     * 百分比符号的位置
     */
    private float percentMarkMargin;

    private float textOffset;//文字位置修正


    public WaveTextDrawHelper() {
        titleText = "Optimize";
        scaleY = 2.1f;
    }

    private Shader getShader() {
        if (this.textLength <= 0) {
            this.textLength = DEFAULT_LENGTH;
        }
        if (this.textHeight <= 0) {
            this.textHeight = DEFAULT_HEIGHT;
        }
        Xfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
//        Xfermode porterDuffXfermode2 = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
//        Xfermode porterDuffXfermode3 = new PorterDuffXfermode(PorterDuff.Mode.DARKEN);
        float scaleConst = (float) (6.283185307179586d / ((double) this.textLength));
        float waveStep = ((float) this.textHeight) * 0.05f;
        float heightOffset = ((float) this.textHeight) * 0.5f;
        /**画出波浪形状**/
        Bitmap waveBitmap = Bitmap.createBitmap(this.textLength, this.textHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(waveBitmap);
        Paint paint = new Paint();
        paint.setStrokeWidth(2.0f);
        paint.setAntiAlias(true);
        paint.setColor(colorScore);
        int pixelCount = this.textLength + 1;
        for (int i = 0; i < pixelCount; i++) {
            canvas.drawLine(i, (float) (Math.sin(i * scaleConst) * waveStep + heightOffset), i, 0f, paint);
        }
        /**画出下部渐变底色**/
        Bitmap linearGradientBitmapBelow = Bitmap.createBitmap(this.textLength, this.textHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(linearGradientBitmapBelow);
        Shader linearGradient = new LinearGradient(0f, 0f, this.textLength, 0f, new int[]{colorGradientStart, colorGradientEnd, colorGradientStart}, null, Shader.TileMode.REPEAT);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(linearGradient);
        canvas.drawRect(0f, 0f, (float) this.textLength, (float) this.textHeight, paint);
        /**叠加 形成带渐变色的波浪**/
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(waveBitmap, 0f, 0f, paint);


        waveBitmap.recycle();






        return new BitmapShader(linearGradientBitmapBelow, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    }


    public void reDraw(int viewWidth, Point centerPoint) {
        mWidth = viewWidth;
        centerPointX = centerPoint.x;
        mScorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScorePaint.setFlags(Paint.FILTER_BITMAP_FLAG);
        mScorePaint.setTextSize(viewWidth * 1.0f / 2.5f);
        mScorePaint.setStyle(Paint.Style.FILL);

        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setTextSize(viewWidth * 1.0f / 14f);
        mTitlePaint.setColor(colorTitle);

        mPercentMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPercentMarkPaint.setTextSize(viewWidth * 1.0f / 12f);
        mPercentMarkPaint.setColor(colorPercentMark);

        measureText = "0";
        Rect _rect = new Rect();
        mScorePaint.getTextBounds(measureText, 0, measureText.length(), _rect);
        textLength = _rect.right - _rect.left;
        SinglePositionX = centerPoint.x - textLength / 2;

        measureText = "00";
        mScorePaint.getTextBounds(measureText, 0, measureText.length(), _rect);
        textLength = _rect.right - _rect.left;
        TweenPositionX = centerPoint.x - textLength / 2;
        textHeight = _rect.bottom - _rect.top;
        TweenPositionY = centerPoint.y + textHeight / 2;

        measureText = "1111";
        mScorePaint.setTextSize(viewWidth * 1.0f / 3f);
        mScorePaint.getTextBounds(measureText, 0, measureText.length(), _rect);
        maxPositionX = centerPoint.x - ((_rect.right - _rect.left) / 2);
        maxPositionY = centerPoint.y + ((_rect.bottom - _rect.top) / 2);

        measureText = "100";
        stepValue = this.textLength / 100;
        mScorePaint.getTextBounds(measureText, 0, measureText.length(), _rect);

        measureText = titleText;
        mTitlePaint.getTextBounds(measureText, 0, measureText.length(), _rect);
        this.titlePositionX = centerPoint.x - ((_rect.right - _rect.left) / 2);

        this.mMatrix = new Matrix();
        textPaintShader = getShader();
        titleMargin = viewWidth * 1.0f / 7;
        percentMarkMargin = viewWidth * 1.0f / 40;
        this.textOffset = viewWidth / 40;
    }

    public void draw(Canvas canvas) {
        this.mMatrix.setScale(1.0f, scaleY, 0f, textHeight);
        this.mMatrix.postTranslate((float) this.transitionX, 0.0f);
        this.textPaintShader.setLocalMatrix(mMatrix);
        mScorePaint.setShader(textPaintShader);
        /**绘制分数文本**/
        canvas.save();
        if (this.scoreText.length() == 3) {
            this.mScorePaint.setTextSize(((float) this.mWidth) / 3.0f);
            canvas.translate((float) this.maxPositionX, (float) ((this.maxPositionY - this.textHeight) - this.textOffset));
        } else if (this.scoreText.length() == 2) {
            this.mScorePaint.setTextSize(((float) this.mWidth) / 2.5f);
            canvas.translate((float) (this.TweenPositionX - this.textOffset), (float) ((this.TweenPositionY - this.textHeight) - this.textOffset));
        } else {
            this.mScorePaint.setTextSize(((float) this.mWidth) / 2.5f);
            canvas.translate((float) (this.SinglePositionX - this.textOffset), (float) ((this.TweenPositionY - this.textHeight) - this.textOffset));
        }
        canvas.drawText(this.scoreText, 0.0f, (float) this.textHeight, this.mScorePaint);
        canvas.restore();
        /**绘制底部文本**/
        canvas.save();
        canvas.translate((float) this.titlePositionX, this.titleMargin + ((float) this.textHeight));
        canvas.drawText(this.titleText, 0.0f, (float) this.textHeight, this.mTitlePaint);
        canvas.restore();
        /**绘制百分比文本**/
        canvas.save();
        canvas.translate(((float) (this.textLength + this.TweenPositionX)) + this.percentMarkMargin, (float) this.textHeight);
        canvas.drawText("%", 0.0f, (float) this.textHeight, this.mPercentMarkPaint);
        canvas.restore();
        transitionX += stepValue;
        if (this.transitionX >= this.textLength + 1) {
            this.transitionX = 0;
        }
    }
    /**设置底部文本*/
    public void setTitleText(String titleText) {
        this.titleText = titleText;
        measureText = titleText;
        Rect _rect = new Rect();
        mTitlePaint.getTextBounds(measureText, 0, measureText.length(), _rect);
        this.titlePositionX = centerPointX - ((_rect.right - _rect.left) / 2);
    }
    /**设置分数*/
    public void setScore(int score) {
        scaleY = (2 * score * 1.0f / 100f) + 0.01f;
        scoreText = "" + score;
    }
}
