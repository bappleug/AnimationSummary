package io.amosbake.animationsummary.animationmisc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;
import io.amosbake.animationsummary.interpolators.InterpolatorGuide;

/**
 * Created by Ray on 2017/6/1.
 */

public class AnimationMiscActivity extends AppCompatActivity {

    @BindView(R.id.vValueAnimator11)
    View mView11;
    @BindView(R.id.vValueAnimator12)
    View mView12;
    @BindView(R.id.vValueAnimator2)
    View mView2;
    @BindView(R.id.vValueAnimator3)
    TextView mView3;
    @BindView(R.id.vObjectAnimator1)
    DotView mView4;
    @BindView(R.id.vKeyFrame1)
    DotView mView5;
    @BindView(R.id.vSpringAnimation)
    SpringButton vSpringAnimation;
    @BindView(R.id.spStiffness)
    Spinner spStiffness;
    @BindView(R.id.spDampingRatio)
    Spinner spDampingRatio;
    boolean anim1 = true;
    ValueAnimator animator2;
    AnimatorSet animator3;
    ObjectAnimator animator4;
    ObjectAnimator animator5;
    private SpringAnimation springAnimationX;
    private SpringAnimation springAnimationY;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_misc);
        ButterKnife.bind(this);
        initAnimation();
    }

    void initAnimation() {
        final SPItem[] stiffnessArray = new SPItem[]{
                new SPItem<>(SpringForce.STIFFNESS_VERY_LOW, "STIFFNESS_VERY_LOW"),
                new SPItem<>(SpringForce.STIFFNESS_LOW, "STIFFNESS_LOW"),
                new SPItem<>(SpringForce.STIFFNESS_MEDIUM, "STIFFNESS_MEDIUM"),
                new SPItem<>(SpringForce.STIFFNESS_HIGH, "STIFFNESS_HIGH")};
        final SPItem[] dampingRatioArray = new SPItem[]{
                new SPItem<>(SpringForce.DAMPING_RATIO_NO_BOUNCY, "DAMPING_RATIO_NO_BOUNCY"),
                new SPItem<>(SpringForce.DAMPING_RATIO_LOW_BOUNCY, "DAMPING_RATIO_LOW_BOUNCY"),
                new SPItem<>(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY, "DAMPING_RATIO_MEDIUM_BOUNCY"),
                new SPItem<>(SpringForce.DAMPING_RATIO_HIGH_BOUNCY, "DAMPING_RATIO_HIGH_BOUNCY")};
        spStiffness.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                stiffnessArray));
        spDampingRatio.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                dampingRatioArray));
        spStiffness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SPItem<Float> stiffness = stiffnessArray[position];
                vSpringAnimation.setStiffness(stiffness.value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDampingRatio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SPItem<Float> dampingRatio = dampingRatioArray[position];
                vSpringAnimation.setDampingRatio(dampingRatio.value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick(R.id.btnValueAnimator1)
    void anim1() {
        if (anim1) {
            mView11.animate().alpha(0).scaleX(1.2f).scaleY(1.2f).setDuration(500).setInterpolator(new OvershootInterpolator()).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    anim1 = !anim1;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            }).start();
            ViewCompat.animate(mView12).alpha(0).scaleX(1.2f).scaleY(1.2f).setDuration(500).setInterpolator(new OvershootInterpolator()).start();
        } else {
            mView11.setScaleX(1);
            mView11.setScaleY(1);
            mView11.setAlpha(1);
            ViewCompat.setAlpha(mView12, 1.0f);
            ViewCompat.setScaleX(mView12, 1);
            ViewCompat.setScaleY(mView12, 1);
            anim1 = !anim1;
        }
    }

    @OnClick(R.id.btnValueAnimator2)
    void anim2() {
        if (animator2 != null) {
            animator2.cancel();
        }
        animator2 = ValueAnimator.ofFloat(1f, 0f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mView2.setAlpha((Float) animation.getAnimatedValue());
                mView2.setTranslationX((Float) animation.getAnimatedValue() * -mView2.getWidth());
            }
        });
        animator2.start();
    }

    @OnClick(R.id.btnValueAnimator3)
    void anim3() {
        if (animator3 != null) {
            animator3.cancel();
        }
        animator3 = new AnimatorSet();
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), 0xffff0000, 0xff0000ff);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mView3.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        ValueAnimator textAnimator = ValueAnimator.ofObject(new TypeEvaluator<Character>() {
            @Override
            public Character evaluate(float fraction, Character startValue, Character endValue) {
                char char1 = startValue;
                char char2 = endValue;
                return (char) (char1 + (char2 - char1) * fraction);
            }
        }, 'a', 'z');
        textAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mView3.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        textAnimator.setRepeatCount(ValueAnimator.INFINITE);
        textAnimator.setRepeatMode(ValueAnimator.RESTART);
        animator3.playTogether(colorAnimator, textAnimator);
        animator3.setDuration(500);
        animator3.setInterpolator(new AccelerateDecelerateInterpolator());
        animator3.start();
    }

    @TargetApi(21)
    @OnClick(R.id.btnObjectAnimator1)
    void anim4() {
        if (animator4 != null) {
            animator4.cancel();
        }
        PropertyValuesHolder radiusHolder = PropertyValuesHolder.ofInt("radius", mView4.getWidth() / 2, 0);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofObject(new Property<DotView, Integer>(Integer.class, "color") {
            @Override
            public Integer get(DotView object) {
                try {
                    Field colorField = object.getClass().getDeclaredField("mPaint");
                    colorField.setAccessible(true);
                    Paint paint = (Paint) colorField.get(object);
                    return paint.getColor();
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            }

            @Override
            public void set(DotView object, Integer value) {
                try {
                    Field colorField = object.getClass().getDeclaredField("mPaint");
                    colorField.setAccessible(true);
                    Paint paint = (Paint) colorField.get(object);
                    paint.setColor(value);
                    object.invalidate();
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            }
        }, new ArgbEvaluator(), 0x60ff0000, 0xff0000ff);
        animator4 = ObjectAnimator.ofPropertyValuesHolder(mView4, radiusHolder, colorHolder);
        animator4.setRepeatCount(ValueAnimator.INFINITE);
        animator4.setRepeatMode(ValueAnimator.REVERSE);
        animator4.setDuration(500);
        animator4.setInterpolator(new AccelerateDecelerateInterpolator());
        animator4.start();
    }

    @OnClick(R.id.btnKeyFrame1)
    void animateKeyFrame() {
        if (animator5 != null) {
            animator5.cancel();
        }
        Keyframe frame1 = Keyframe.ofInt(0, 0);
        Keyframe frame2 = Keyframe.ofInt(0.125f, mView5.getWidth() / 2);
        frame2.setInterpolator(new AccelerateInterpolator());
        Keyframe frame3 = Keyframe.ofInt(0.25f, 0);
        frame3.setInterpolator(new DecelerateInterpolator());
        Keyframe frame4 = Keyframe.ofInt(0.375f, mView5.getWidth() / 2);
        frame4.setInterpolator(new AccelerateInterpolator());
        Keyframe frame5 = Keyframe.ofInt(0.5f, 0);
        frame5.setInterpolator(new DecelerateInterpolator());
        Keyframe frame6 = Keyframe.ofInt(1, mView5.getWidth() * 2);
        frame6.setInterpolator(new AccelerateInterpolator());
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("radius", frame1, frame2, frame3, frame4, frame5, frame6);
        animator5 = ObjectAnimator.ofPropertyValuesHolder(mView5, holder);
        animator5.setInterpolator(new LinearInterpolator());
        animator5.setDuration(4000);
        animator5.start();
    }

    private static class SPItem<T> {
        T value;
        String name;

        public SPItem(T value, String name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
