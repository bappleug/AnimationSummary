package io.amosbake.animationsummary.paint;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.amosbake.animationsummary.R;
import io.amosbake.animationsummary.interpolators.DensityUtils;

/**
 * Created by Ray on 2017/8/8.
 */

public class PaintActivity extends AppCompatActivity implements PaintView.DrawHandler {

    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    @BindView(R.id.vPaint)
    PaintView vPaint;

    List<XfermodeContent> xfermodeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        ButterKnife.bind(this);
        initData();
        initSpinner();
    }

    private void initData() {
        xfermodeList = new ArrayList<>();
        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
            xfermodeList.add(new XfermodeContent(mode));
        }
        vPaint.setDrawHandler(this);
    }

    void initSpinner() {
        mSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, xfermodeList));
//        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                XfermodeContent xfermodeContent = xfermodeList.get(position);
                vPaint.setXferMode(xfermodeContent.xfermode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void drawSrc(Canvas canvas) {
        canvas.save();
        canvas.translate(DensityUtils.dip2px(this, 20), DensityUtils.dip2px(this, 20));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_star_rate_off);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.setColorFilter(0xffffff00, PorterDuff.Mode.SRC_ATOP);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public void drawDest(Canvas canvas) {
        canvas.drawColor(0xff333333);
    }
}
