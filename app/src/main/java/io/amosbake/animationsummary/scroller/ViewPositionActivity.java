package io.amosbake.animationsummary.scroller;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;
import io.amosbake.animationsummary.wiget.SuperToast;

/**
 * Created by Ray on 2016/12/28.
 */

public class ViewPositionActivity extends AppCompatActivity{

    @BindView(R.id.loContainer)
    FrameLayout loContainer;
    TextView tvContent;
    @BindView(R.id.btnLPSwitch)
    Button btnLPSwitch;
    @BindView(R.id.btnLPPlusWidth)
    Button btnLPPlusWidth;
    @BindView(R.id.tvLPType)
    TextView tvLPType;
    @BindView(R.id.tvLPWidth)
    TextView tvLPWidth;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvX)
    TextView tvX;
    @BindView(R.id.tvScaleWidth)
    TextView tvScaleWidth;
    @BindView(R.id.tvMeasuredWidth)
    TextView tvMeasuredWidth;
    @BindView(R.id.tvParentPadding)
    TextView tvParentPadding;
    @BindView(R.id.tvScrollX)
    TextView tvScrollX;
    @BindView(R.id.tvMargin)
    TextView tvMargin;
    @BindView(R.id.tvTransX)
    TextView tvTransX;
    @BindView(R.id.tvWidth)
    TextView tvWidth;

    private FrameLayout.LayoutParams lp;

    int lpWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_position);
        ButterKnife.bind(this);
        tvContent = (TextView) loContainer.getChildAt(0);
        lp = (FrameLayout.LayoutParams) tvContent.getLayoutParams();
        lpWidth = dp2xp(100);
        loContainer = (FrameLayout) findViewById(R.id.loContainer);
        loContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updateLog();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            loContainer.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    updateLog();
                }
            });
        }
        loContainer.requestLayout();
    }

    void updateLog(){
        if(lp.width == FrameLayout.LayoutParams.MATCH_PARENT){
            btnLPSwitch.setText("MATCH_PARENT");
            tvLPType.setText("MATCH_PARENT");
            btnLPPlusWidth.setEnabled(false);
        } else if(lp.width == FrameLayout.LayoutParams.WRAP_CONTENT){
            btnLPSwitch.setText("WRAP_CONTENT");
            tvLPType.setText("WRAP_CONTENT");
            btnLPPlusWidth.setEnabled(false);
        } else {
            btnLPSwitch.setText("CUSTOM");
            tvLPType.setText("CUSTOM");
            btnLPPlusWidth.setEnabled(true);
        }
        tvX.setText("X = " + tvContent.getX());
        tvLeft.setText("Left = " + tvContent.getLeft());
        tvLPWidth.setText("LP Width = " + lp.width + ";");
        tvMargin.setText("Margin = " + lp.leftMargin);
        tvMeasuredWidth.setText("MeasuredWidth = " + tvContent.getMeasuredWidth());
        tvParentPadding.setText("Padding = " + loContainer.getPaddingLeft());
        tvScrollX.setText("Scroll X = " + loContainer.getScrollX());
        tvWidth.setText("Width = " + tvContent.getWidth());
        tvTransX.setText("Trans X = " + tvContent.getTranslationX());
        tvScaleWidth.setText("Scale Width = " + tvContent.getScaleX());
    }

    @OnClick(R.id.tvContent)
    void tvContentClick(){
        SuperToast.getInstance(this).show("clicked");
    }

    @OnClick(R.id.btnLPReset)
    void lpReset(){
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lpWidth = dp2xp(100);
        updateLog();

    }

    @OnClick(R.id.btnLPSwitch)
    void lpSwitch(){
        if(lp.width == ViewGroup.LayoutParams.WRAP_CONTENT){
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if(lp.width == ViewGroup.LayoutParams.MATCH_PARENT){
            lp.width = lpWidth;
        } else {
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        updateLog();
    }

    @OnClick(R.id.btnLPPlusWidth)
    void lpWidthPlus(){
        lp.width += dp2xp(50);
        lpWidth = lp.width;
        updateLog();
    }

    @OnClick(R.id.btnLeftReset)
    void xPosWidthReset(){
        callRequestLayout();
        updateLog();
    }

    @OnClick(R.id.btnOffsetLeftRight)
    void offsetLeftRight(){
        tvContent.offsetLeftAndRight(dp2xp(50));
        updateLog();
    }

    @OnClick(R.id.btnPostInvalidate)
    void postInvalidateLeft(){
        tvContent.postInvalidate(tvContent.getLeft() + dp2xp(50), 0, 0, 0);
        updateLog();
    }

    @OnClick(R.id.btnMeasure)
    void callMeasure(){
        tvContent.measure(0, 0);
        updateLog();
    }

    @OnClick(R.id.btnRequestLayout)
    void callRequestLayout(){
        tvContent.requestLayout();
        updateLog();
    }

    @OnClick(R.id.btnCallInvalidate)
    void callInvalidate(){
        tvContent.invalidate();
        updateLog();
    }

    @OnClick(R.id.btnScrollReset)
    void resetScroll(){
        loContainer.scrollTo(0, 0);
        tvContent.scrollTo(0, 0);
        updateLog();
    }

    @OnClick(R.id.btnScrollParentX10)
    void scrollParent(){
        loContainer.scrollBy(50, 0);
        updateLog();
    }

    @OnClick(R.id.btnScrollX10)
    void scroll(){
        tvContent.scrollBy(50, 0);
        updateLog();
    }

    @OnClick(R.id.btnTranslationReset)
    void resetTrans(){
        tvContent.setTranslationX(0);
        TranslateAnimation transAnimX = new TranslateAnimation(0, 0, 0, 0);
        transAnimX.setDuration(0);
        transAnimX.setFillAfter(true);
        tvContent.startAnimation(transAnimX);
        updateLog();
    }

    @OnClick(R.id.btnX)
    void x(){
        tvContent.setX(tvContent.getX() + 50);
        updateLog();
    }

    @OnClick(R.id.btnTranslationX)
    void transX(){
        tvContent.setTranslationX(tvContent.getTranslationX() + 50);
        updateLog();
    }

    @OnClick(R.id.btnTranslateAnimX)
    void transAnimX(){
        TranslateAnimation transAnimX = new TranslateAnimation(0, dp2xp(50), 0, 0);
        transAnimX.setDuration(1000);
        transAnimX.setFillAfter(true);
        tvContent.startAnimation(transAnimX);
        updateLog();
    }

    @OnClick(R.id.btnScaleReset)
    void scaleReset(){
        tvContent.setScaleX(1);
        updateLog();
    }

    @OnClick(R.id.btnScaleX12)
    void scaleX12(){
        tvContent.setScaleX(tvContent.getScaleX() * 1.2f);
        updateLog();
    }

    @OnClick(R.id.btnScaleX05)
    void scaleX05(){
        tvContent.setScaleX(tvContent.getScaleX() / 2f);
        updateLog();
    }

    @OnClick(R.id.btnPaddingMarginReset)
    void resetMarginPadding(){
        tvContent.setPadding(0, 0, 0, 0);
        lp.setMargins(0, 0, 0, 0);
        updateLog();
    }

    @OnClick(R.id.btnPadding)
    void addPadding(){
        tvContent.setPadding(tvContent.getPaddingLeft() + 50, 0, 0, 0);
        updateLog();
    }

    @OnClick(R.id.btnMargin)
    void addMargin(){
        lp.setMargins(lp.leftMargin + 50, 0, 0, 0);
        updateLog();
    }

    int dp2xp(float dp){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int) (dp * metrics.density);
    }
}
