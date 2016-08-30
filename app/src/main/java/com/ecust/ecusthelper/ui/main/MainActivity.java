package com.ecust.ecusthelper.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.base.BaseMvpActivity;
import com.ecust.ecusthelper.ui.news.NewsActivity;
import com.ecust.ecusthelper.util.ui.DrawerUtil;
import com.jaeger.library.StatusBarUtil;

//Todo:以后加轮循、CordinderLayout、视差动画、天气
public class MainActivity extends BaseMvpActivity<MainContract.Presenter> implements MainContract.View {
    DrawerFragment mDrawerFragment;
    ContentFragment mContentFragment;

    @NonNull
    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        switchToOtherActivity();
    }

    private void switchToOtherActivity() {
        startActivity(new Intent(this, NewsActivity.class));
    }

    private void init() {
        setDrawerFragment();
        setContentFragment();
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void setStatusBar() {
        final int mStatusBarColor = getResources().getColor(R.color.colorPrimary);
        final int DEFAULT_ALPHA = 50;
        StatusBarUtil.setColorForDrawerLayout(this, getDrawerLayout(), mStatusBarColor, DEFAULT_ALPHA);
    }

    @Override
    public void onBackPressed() {
        getPresenter().onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().setupDrawerToggleAfterFragmentLoaded();
    }

    @Override
    public void setDrawerFragment() {
        mDrawerFragment = new DrawerFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer, mDrawerFragment)
                .commit();
    }

    @Override
    public void setContentFragment() {
        mContentFragment = new ContentFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mContentFragment)
                .commit();
    }

    @Override
    public DrawerLayout getDrawerLayout() {
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Objects.requireNonNull(mDrawerLayout);
        return mDrawerLayout;
    }

    @Override
    public DrawerFragment getDrawerFragment() {
        return Objects.requireNonNull(mDrawerFragment);
    }

    @Override
    public Toolbar getToolbar() {
        return mContentFragment.getToolbar();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void exitApp() {
        super.onBackPressed();
    }

    @Override
    public void closeDrawer() {
        DrawerUtil.closeDrawer(getDrawerLayout());
    }
}
