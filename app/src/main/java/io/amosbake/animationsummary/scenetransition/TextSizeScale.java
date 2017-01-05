package io.amosbake.animationsummary.scenetransition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionValues;
import com.transitionseverywhere.utils.FloatProperty;

/**
 * Created by Ray on 2017/1/5.
 */

public class TextSizeScale extends Transition {

    private static final Property<TextView, Float> TEXTSIZE = new FloatProperty<TextView>() {

        @Override
        public void setValue(TextView textView, float value) {
            textView.setTextSize(value);
        }

        @Override
        public Float get(TextView textView) {
            return textView.getTextSize();
        }
    };

    /**
     * Internal name of property. Like a intent bundles
     */
    private static final String PROPNAME_TEXTSIZE = "TextsizeTransition:textsize";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues){
        if(transitionValues.view instanceof TextView){
            TextView textView = (TextView) transitionValues.view;
            transitionValues.values.put(PROPNAME_TEXTSIZE, textView.getTextSize());
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if(startValues != null && endValues != null && endValues.view instanceof TextView){
            TextView textView = (TextView) endValues.view;
            float start = (float) startValues.values.get(PROPNAME_TEXTSIZE);
            float end = (float) endValues.values.get(PROPNAME_TEXTSIZE);
            if(start != end){
                textView.setTextSize(start);
                return ObjectAnimator.ofFloat(textView, TEXTSIZE, end);
            }
        }
        return null;
    }
}
