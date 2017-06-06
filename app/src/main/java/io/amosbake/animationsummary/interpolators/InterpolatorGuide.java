package io.amosbake.animationsummary.interpolators;

import android.support.animation.SpringAnimation;
import android.view.animation.Interpolator;

/**
 * Created by Ray on 2017/6/3.
 */

public class InterpolatorGuide {

    Interpolator mInterpolator;
    SpringAnimation animation;
    String name;
    String desc;

    public InterpolatorGuide(Interpolator interpolator) {
        mInterpolator = interpolator;
        name = mInterpolator.getClass().getSimpleName();
        desc = "";
    }

    public InterpolatorGuide(SpringAnimation animation) {
        this.animation = animation;
    }

    public InterpolatorGuide interpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        return this;
    }

    public InterpolatorGuide name(String name) {
        this.name = name;
        return this;
    }

    public InterpolatorGuide desc(String desc) {
        this.desc = desc;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
