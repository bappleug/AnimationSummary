package io.amosbake.animationsummary.constraintlayouttransition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/5/25.
 */

public class ConstraintTransitionActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView mImageView;
    @BindView(R.id.root)
    ConstraintLayout mConstraintLayout;
    ConstraintSet mConstraintSet1;
    ConstraintSet mConstraintSet2;
    boolean changed = false;

    @OnClick(R.id.button)
    void animate(){
        TransitionManager.beginDelayedTransition(mConstraintLayout);
        ConstraintSet newSet = changed ? mConstraintSet1 : mConstraintSet2;
        newSet.applyTo(mConstraintLayout);
        changed = !changed;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_constraint1);
        ButterKnife.bind(this);
        mConstraintSet1 = new ConstraintSet();
        mConstraintSet1.clone(mConstraintLayout);
        mConstraintSet2 = new ConstraintSet();
        mConstraintSet2.clone(this, R.layout.layout_constraint2);
    }
}
