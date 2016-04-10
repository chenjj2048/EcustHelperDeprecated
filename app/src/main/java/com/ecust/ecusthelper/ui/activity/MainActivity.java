package com.ecust.ecusthelper.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.ui.fragment.ContentFragment;
import com.ecust.ecusthelper.ui.fragment.DrawerFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MainActivity extends BaseAppCompatActivity {

    //Todo:以后加轮循、CordinderLayout、视差动画

    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    DrawerFragment mDrawerFragment;
    ContentFragment mContentFragment;

    private ExitUtil mExitUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDrawerFragment();
        setupContentFragment();
        setupDrawerLayout();
        setTitle("Demo");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            toggleDrawer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setStatusBar() {
//        有问题的写法1:
//        StatusBarUtil.setTranslucentForDrawerLayout(this, mDrawerLayout);

//        写法2，灰色的啊，怎么回事:
        int mStatusBarColor = getResources().getColor(android.R.color.holo_green_light);
        StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayout, mStatusBarColor, 100);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            if (mExitUtil == null)
                mExitUtil = new ExitUtil();
            mExitUtil.tryExit();
        }
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerFragment.navigationScrollToTop();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setupDrawerFragment() {
        mDrawerFragment = new DrawerFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.drawer, mDrawerFragment)
                .commit();
    }

    private void setupContentFragment() {
        mContentFragment = new ContentFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content, mContentFragment)
                .commit();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void toggleDrawer() {
        if (isDrawerOpen())
            closeDrawer();
        else
            openDrawer();
    }

    /**
     * 短时间内两次调用，方可退出程序，否则显示提示消息
     */
    private final class ExitUtil {
        private static final long EXIT_IN_DELTA_TIME = 1500;
        /**
         * Snackbar未显示时置为null,显示时为非null
         */
        private Snackbar mSnackbar;

        public void tryExit() {
            if (mSnackbar == null) {
                showSnackBar(EXIT_IN_DELTA_TIME);
            } else {
                exitApp();
            }
        }

        private void showSnackBar(long duration) {
            mSnackbar = getNewSnackbar();
            mSnackbar.show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mSnackbar != null) {
                        mSnackbar.dismiss();
                        mSnackbar = null;
                    }
                }
            }, duration);
        }

        private Snackbar getNewSnackbar() {
            if (getCurrentFocus() != null)
                return Snackbar.make(getCurrentFocus(), "再按一次退出程序", Snackbar.LENGTH_INDEFINITE)
                        .setAction("退出", v -> tryExit());
            else
                throw new NullPointerException("Snackbar第一个参数为空");
        }

        private void exitApp() {
            mSnackbar = null;
            MainActivity.super.onBackPressed();
        }
    }
}
