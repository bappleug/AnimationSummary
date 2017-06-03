package io.amosbake.animationsummary.interpolators;

import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.amosbake.animationsummary.R;

public class InterpolatorActivity extends AppCompatActivity {

    @BindView(R.id.interpolatorChart)
    InterpolatorChart vChart;
    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDesc)
    TextView tvDesc;

    ArrayList<InterpolatorGuide> mInterpolators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);
        ButterKnife.bind(this);
        initInterpolators();
        initSpinner();
        vChart.setInterpolator(new AccelerateInterpolator());
//        vChart.setInterpolator(new CubicBezierInterpolator(0.58f, 0.8f, 0.74f, 0.8f));
//        vChart.setInterpolator(new AnticipateOvershootInterpolator());
        vChart.showAnimChart(1000, true, false);
//        vChart.showStaticChart();
    }

    private void initInterpolators() {
        mInterpolators = new ArrayList<>();
        mInterpolators.add(new InterpolatorGuide(new FastOutSlowInInterpolator())
                .name("ease in out - Standard curve")
                .desc("The standard curve (also referred to as “ease in out”) is the most common easing curve. Elements quickly accelerate and slowly decelerate between on-screen locations. It applies to growing and shrinking material, among other property changes."));
        mInterpolators.add(new InterpolatorGuide(new LinearOutSlowInInterpolator())
                .name("ease out - Deceleration curve")
                .desc("Using the deceleration curve (also referred to as “ease out”) elements enter the screen at full velocity and slowly decelerate to a resting point.\n" +
                        "\n" +
                        "During deceleration, elements may scale up either in size (to 100%) or opacity (to 100%). In some cases, when elements enter the screen at 0% opacity, they may slightly shrink from a larger size upon entry."));
        mInterpolators.add(new InterpolatorGuide(new FastOutSlowInInterpolator())
                .name("ease in - Acceleration curve")
                .desc("Using the acceleration curve (also referred to as “ease in”) elements leave the screen at full velocity. They do not decelerate when off-screen.\n" +
                        "\n" +
                        "They accelerate at the beginning of the animation and may scale down in either size (to 0%) or opacity (to 0%). In some cases, when elements leave the screen at 0% opacity, they may also slightly scale up or down in size."));
        mInterpolators.add(new InterpolatorGuide(PathInterpolatorCompat.create(0.4f, 0, 0.6f, 1))
                .name("ease in out - Sharp curve")
                .desc("Using the sharp curve (also referred to as “ease in out”) elements quickly accelerate and decelerate. It is used by exiting elements that may return to the screen at any time.\n" +
                        "\n" +
                        "Elements may quickly accelerate from a starting point on-screen, then quickly decelerate in a symmetrical curve to a resting point immediately off-screen. The deceleration is faster than the standard curve since it doesn't follow an exact path to the off-screen point. Elements may return from that point at any time.\n" +
                        "\n" +
                        "All these curves can be simplified as Cubic Bezier curve. So we can use PathInterpolatorCompat to create interpolator matching any curve we need"));
        mInterpolators.add(new InterpolatorGuide(new AccelerateInterpolator()));
        mInterpolators.add(new InterpolatorGuide(new DecelerateInterpolator()));
        mInterpolators.add(new InterpolatorGuide(new AccelerateDecelerateInterpolator()));
        mInterpolators.add(new InterpolatorGuide(new BounceInterpolator()));
        mInterpolators.add(new InterpolatorGuide(new AnticipateInterpolator()));
    }

    void initSpinner(){
        mSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mInterpolators));
//        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InterpolatorGuide guide = mInterpolators.get(position);
                vChart.changeInterpolator(guide.mInterpolator);
                tvName.setText(guide.name);
                tvDesc.setText(guide.desc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vChart.stop();
    }
}
