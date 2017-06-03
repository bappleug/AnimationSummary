package io.amosbake.animationsummary.touchdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import io.amosbake.animationsummary.R;


/**
 * Created by Ray on 2017/3/8.
 */

public class TouchActivity extends Activity {

    ViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_demo);
        vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                Button button = new Button(TouchActivity.this);
                button.setText("button" + position);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) container.getLayoutParams();
                lp.gravity = Gravity.CENTER;
                button.setLayoutParams(lp);
                container.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TouchActivity.this, "button" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return button;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }


}
