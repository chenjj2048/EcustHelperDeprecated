package com.ecust.ecusthelper.ui.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.adapter.NewsViewPagerAdapter;
import com.ecust.ecusthelper.base.BaseAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;

public class NewsActivity extends BaseAppCompatActivity {
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

//        Observable.just(0)
//                .delay(500, TimeUnit.MILLISECONDS)
//                .subscribe((Integer page) -> {
//                    selectFragment(page);
//                });
    }

    private void setupFragmentAdapter() {
        mAdapter = new NewsViewPagerAdapter(getSupportFragmentManager());
    }

    private void setupViewPager() {
        mViewPager.setAdapter(mAdapter);
//        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                selectFragment(position);
//            }
//        });
    }

    private void selectFragment(int position) {
//        final NewsFragment mFragment = (NewsFragment) mAdapter.getItem(position);

//        mFragment.onAttach(this);
//        mFragment.onCurrentFragmentSelected();
    }

    private void setupTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
