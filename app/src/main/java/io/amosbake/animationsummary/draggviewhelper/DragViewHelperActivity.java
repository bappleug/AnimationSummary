package io.amosbake.animationsummary.draggviewhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2016/12/27.
 */

public class DragViewHelperActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view_helper);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnText6)
    public void clickBtnText6(){
        Toast.makeText(this, "btn6 clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnText7)
    public void clickBtnText7(){
        findViewById(R.id.loDragger).requestLayout();
    }
}
