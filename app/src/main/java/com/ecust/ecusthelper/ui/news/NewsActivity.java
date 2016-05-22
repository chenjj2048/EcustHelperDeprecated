package com.ecust.ecusthelper.ui.news;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.adapter.NewsViewPagerAdapter;
import com.ecust.ecusthelper.base.BaseAppCompatActivity;
import com.ecust.ecusthelper.util.log.logUtil;

import butterknife.Bind;

public class NewsActivity extends BaseAppCompatActivity {

    @Bind(R.id.appbarlayout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    private NewsViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_catalog);
        setupToolbar(mToolbar);
        setupFragmentAdapter();
        setupViewPager();
        setupTabLayout();
    }

    private void setupFragmentAdapter() {
        mAdapter = new NewsViewPagerAdapter(getSupportFragmentManager());
    }

    private void setupViewPager() {
        mViewPager.setAdapter(mAdapter);
    }

    private void setupTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
