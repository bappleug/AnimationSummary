package io.amosbake.animationsummary.layoutanimation_api1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/6/2.
 */

public class LayoutAnimationActivity extends AppCompatActivity {

    @BindView(R.id.loAnim)
    ConstraintLayout loAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        ButterKnife.bind(this);
        //Animate by code
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
        controller.setDelay(1f);
        loAnim.setLayoutAnimation(controller);
        loAnim.startLayoutAnimation();
    }

    @OnClick(R.id.btnReset)
    void showContent(){
        loAnim.setVisibility(loAnim.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

}
