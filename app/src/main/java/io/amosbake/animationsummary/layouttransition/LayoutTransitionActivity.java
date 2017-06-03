package io.amosbake.animationsummary.layouttransition;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;

import static io.amosbake.animationsummary.R.id.layoutTransitionGroup;

/**
 * Created by Ray on 2017/5/25.
 */

public class LayoutTransitionActivity extends AppCompatActivity {

    @BindView(layoutTransitionGroup)
    LinearLayout mViewGroup;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouttrans);
        ButterKnife.bind(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f);
        LayoutTransition transition = new LayoutTransition();
        transition.setAnimator(LayoutTransition.APPEARING, animator);
        mViewGroup.setLayoutTransition(transition);
    }

    @OnClick(R.id.add_btn)
    void addView(){
        i++;
        Button button = new Button(this);
        button.setText("button" + i);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        mViewGroup.addView(button, 0);
    }

    @OnClick(R.id.remove_btn)
    void removeView(){
        if (i > 0) {
            mViewGroup.removeViewAt(0);
        }
        i--;
    }
}
