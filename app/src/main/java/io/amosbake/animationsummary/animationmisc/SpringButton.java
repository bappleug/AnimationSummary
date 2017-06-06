package io.amosbake.animationsummary.animationmisc;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by Ray on 2017/6/2.
 */

public class SpringButton extends AppCompatButton {

    private ObjectAnimator downAnimator;
    private SpringAnimation upAnimatorX;
    private SpringAnimation upAnimatorY;
    private SpringForce springForce;

    public SpringButton(Context context) {
        super(context);
        init();
    }

    public SpringButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        springForce = new SpringForce();
        springForce.setFinalPosition(1.0f);
        springForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        if (pressed) {
            actionDownAnimate();
        } else {
            actionUpAnimate();
        }
    }

    private void actionDownAnimate() {
        cancelAnimate();
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1, .6f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1, .6f);
        downAnimator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY);
        downAnimator.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        downAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        downAnimator.start();
    }

    private void actionUpAnimate() {
        cancelAnimate();

        upAnimatorX = new SpringAnimation(this, SpringAnimation.SCALE_X);
        upAnimatorY = new SpringAnimation(this, SpringAnimation.SCALE_Y);
        upAnimatorX.setSpring(springForce);
        upAnimatorY.setSpring(springForce);
        upAnimatorX.start();
        upAnimatorY.start();
    }

    private void cancelAnimate() {
        if (downAnimator != null) {
            downAnimator.cancel();
        }
        if (upAnimatorX != null) {
            upAnimatorX.cancel();
        }
        if (upAnimatorY != null) {
            upAnimatorY.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimate();
    }

    public void setStiffness(float stiffness) {
        springForce.setStiffness(stiffness);
    }

    public void setDampingRatio(float dampingRatio) {
        springForce.setDampingRatio(dampingRatio);
    }
}
