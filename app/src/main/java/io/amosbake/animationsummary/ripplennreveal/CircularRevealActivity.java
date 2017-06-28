package io.amosbake.animationsummary.ripplennreveal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/6/28.
 */

public class CircularRevealActivity extends AppCompatActivity{

    @BindView(R.id.lo_reveal)
    LinearLayout loReveal;
    @BindView(R.id.fabExpand)
    FloatingActionButton fabExpand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_reveal);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.fabExpand)
    private void expendView() {
        fabExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
