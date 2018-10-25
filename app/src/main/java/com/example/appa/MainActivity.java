package com.example.appa;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.appa.adapter.ViewPagerAdapter;
import com.example.appa.fragment.HistoryFragment;
import com.example.appa.fragment.TestFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //add fragment
        mViewPagerAdapter.addFragment(new TestFragment(), getString(R.string.test));
        mViewPagerAdapter.addFragment(new HistoryFragment(), getString(R.string.history));

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
