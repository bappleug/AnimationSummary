package io.amosbake.animationsummary.coordinatorLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/1/3.
 */

public class CoordinatorActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout loTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


        loTab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
    }

    private class PageAdapter extends FragmentPagerAdapter{

        PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            RecyclerFragment fragment = new RecyclerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "page" + (position + 1);
        }
    }
}
