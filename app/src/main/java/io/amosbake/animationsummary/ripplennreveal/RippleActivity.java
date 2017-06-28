package io.amosbake.animationsummary.ripplennreveal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import butterknife.BindView;
import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/5/13.
 */

public class RippleActivity extends AppCompatActivity {

    @BindView(R.id.rlRipple1)
    RelativeLayout relativeLayout1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

    }
}
