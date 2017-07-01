package io.amosbake.animationsummary.ripplennreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

import com.lexing.common.utils.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/6/28.
 */

public class CircularRevealActivity extends AppCompatActivity{

    @BindView(R.id.lo_coordinator)
    CoordinatorLayout loRoot;
    @BindView(R.id.lo_reveal)
    LinearLayout loReveal;
    @BindView(R.id.fabExtend)
    FloatingActionButton fabExpand;

    boolean extended = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_reveal);
        ButterKnife.bind(this);

    }

    @TargetApi(21)
    @OnClick(R.id.fabExtend)
    public void expendView() {
        int fabSizePx = DensityUtils.dip2px(this, 56);
        int viewCenterX = loReveal.getWidth()
                - ((CoordinatorLayout.LayoutParams)(fabExpand.getLayoutParams())).rightMargin
                - fabSizePx / 2;
        int viewCenterY = loReveal.getHeight()
                - ((CoordinatorLayout.LayoutParams)(fabExpand.getLayoutParams())).bottomMargin
                - fabSizePx / 2;
        float finalRadius = (float)Math.hypot(loReveal.getHeight(), loReveal.getWidth());
        final Animator revealAnim = ViewAnimationUtils.createCircularReveal(
                loReveal,
                viewCenterX,
                viewCenterY,
                fabSizePx,
                finalRadius);
        revealAnim.setDuration(3750);
        revealAnim.setInterpolator(new FastOutSlowInInterpolator());
        revealAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                loReveal.setVisibility(View.VISIBLE);
                fabExpand.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                extended = true;
            }
        });
        revealAnim.start();
    }

    @TargetApi(21)
    @OnClick(R.id.btnBack)
    public void shrinkView(){
        int fabSizePx = DensityUtils.dip2px(this, 56);
        int viewCenterX = loReveal.getWidth()
                - ((CoordinatorLayout.LayoutParams)(fabExpand.getLayoutParams())).rightMargin
                - fabSizePx / 2;
        int viewCenterY = loReveal.getHeight()
                - ((CoordinatorLayout.LayoutParams)(fabExpand.getLayoutParams())).bottomMargin
                - fabSizePx / 2;
        float finalRadius = (float)Math.hypot(loReveal.getHeight(), loReveal.getWidth());
        final Animator revealAnim = ViewAnimationUtils.createCircularReveal(
                loReveal,
                viewCenterX,
                viewCenterY,
                finalRadius,
                fabSizePx);
        revealAnim.setDuration(2250);
        revealAnim.setInterpolator(PathInterpolatorCompat.create(0.4f, 0, 0.6f, 1));
        revealAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                loReveal.setVisibility(View.INVISIBLE);
                fabExpand.setVisibility(View.VISIBLE);
                extended = false;
            }
        });
        revealAnim.start();
    }

}