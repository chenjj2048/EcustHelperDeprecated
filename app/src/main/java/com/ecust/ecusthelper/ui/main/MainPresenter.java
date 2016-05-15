package com.ecust.ecusthelper.ui.main;

import android.app.Activity;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.util.ui.DrawerUtil;
import com.ecust.ecusthelper.util.ui.SnackbarUtil;

/**
 * Created on 2016/4/23
 *
 * @author chenjj2048
 */
public class MainPresenter implements MainContract.Presenter {
    private static final int DEFAULT_EXIT_MESSAGE_DURATION = 1500;
    private final MainContract.View mMainView;
    private boolean drawerToggleLoaded = false;
    private long lastTryExitTime;

    public MainPresenter(MainContract.View view) {
        this.mMainView = view;
    }

    /**
     * 必须保证ContentFragement加载完后才调用
     * 避免fragment中toolbar获取时为null
     */
    @Override
    public void setupDrawerToggleAfterFragmentLoaded() {
        if (drawerToggleLoaded) return;
        drawerToggleLoaded = true;

        final Toolbar mToolbar = mMainView.getToolbar();
        final DrawerLayout mDrawerLayout = mMainView.getDrawerLayout();
        Objects.requireNonNull(mToolbar);
        DrawerToggle mDrawerToggle = new DrawerToggle(mMainView.getActivity(),
                mDrawerLayout, mToolbar,
                R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout mDrawerLayout = mMainView.getDrawerLayout();
        if (DrawerUtil.isDrawerOpen(mDrawerLayout))
            DrawerUtil.toggleDrawer(mDrawerLayout);
        else
            tryExit();
    }

    /**
     * 成功时退出APP，否则弹出退出提示
     */
    private void tryExit() {
        final long lastTimePassed = SystemClock.elapsedRealtime() - lastTryExitTime;
        if (lastTimePassed <= DEFAULT_EXIT_MESSAGE_DURATION)
            mMainView.exitApp();
        else
            SnackbarUtil.show(createNewSnackbar(), DEFAULT_EXIT_MESSAGE_DURATION);
        lastTryExitTime = SystemClock.elapsedRealtime();
    }

    private Snackbar createNewSnackbar() {
        final View view = mMainView.getActivity().getCurrentFocus();
        Objects.requireNonNull(view);
        return Snackbar.make(view, "再按一次退出程序", Snackbar.LENGTH_INDEFINITE)
                .setAction("退出", v -> mMainView.exitApp());
    }

    /**
     * ActionBarDrawerToggle
     * 开启、关闭功能
     */
    class DrawerToggle extends ActionBarDrawerToggle {
        public DrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                            @StringRes int openRes, @StringRes int closeRes) {
            super(activity, drawerLayout, toolbar, openRes, closeRes);
        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mMainView.getDrawerFragment().scrollNavigationViewToTop();
        }
    }
}
