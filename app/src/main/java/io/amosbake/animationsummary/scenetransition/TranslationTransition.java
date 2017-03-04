package io.amosbake.animationsummary.scenetransition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionValues;
import com.transitionseverywhere.utils.FloatProperty;

/**
 * Created by Ray on 2017/1/9.
 */

public class TranslationTransition extends Transition {

    public static final int TRANSLATION_X = 0;
    public static final int TRANSLATION_Y = 1;

    private int translation;

    public TranslationTransition(int translation) {
        super();
        this.translation = translation;
    }

    private static final Property<View, Float> TRANSLATIONX = new FloatProperty<View>() {

        @Override
        public void setValue(View view, float value) {
            view.setTranslationX(value);
        }

        @Override
        public Float get(View view) {
            return view.getTranslationX();
        }
    };

    private static final Property<View, Float> TRANSLATIONY = new FloatProperty<View>() {

        @Override
        public void setValue(View view, float value) {
            view.setTranslationY(value);
        }

        @Override
        public Float get(View view) {
            return view.getTranslationY();
        }
    };

    /**
     * Internal name of property. Like a intent bundles
     */
    private static final String PROPNAME_TRANSLATION_X = "TranslationXTransition:translationX";
    private static final String PROPNAME_TRANSLATION_Y = "TranslationXTransition:translationY";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues){
        View view = transitionValues.view;
        transitionValues.values.put(
                TRANSLATION_X == translation ? PROPNAME_TRANSLATION_X : PROPNAME_TRANSLATION_Y,
                TRANSLATION_X == translation ? view.getTranslationX() : view.getTranslationY());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if(startValues != null && endValues != null){
            View view = endValues.view;
            float start = (float) startValues.values.get(TRANSLATION_X == translation ? PROPNAME_TRANSLATION_X : PROPNAME_TRANSLATION_Y);
            float end = (float) endValues.values.get(TRANSLATION_X == translation ? PROPNAME_TRANSLATION_X : PROPNAME_TRANSLATION_Y);
            if(start != end){
                if(TRANSLATION_X == translation){
                    view.setTranslationX(start);
                } else {
                    view.setTranslationY(start);
                }
                return ObjectAnimator.ofFloat(view, TRANSLATION_X == translation ? TRANSLATIONX : TRANSLATIONY, end);
            }
        }
        return null;
    }
}
