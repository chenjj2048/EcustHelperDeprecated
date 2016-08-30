package com.ecust.ecusthelper.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.util.ToolbarUtil;
import com.ecust.ecusthelper.util.log.logUtil;
import com.jaeger.library.StatusBarUtil;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created on 2016/4/5
 *
 * @author chenjj2048
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    private static WeakReference<AppCompatActivity> weakReference = new WeakReference<>(null);
    private boolean updateStatusbarNextTime = true;

    @Nullable
    public static AppCompatActivity getCurrentActivity() {
        return weakReference.get();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weakReference = new WeakReference<>(this);
        logUtil.d(this, "onCreate成功");
    }

    public void setupToolbar(Toolbar toolbar) {
        ToolbarUtil.setupToolbar(this, toolbar);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        logUtil.d(this, "setContentView成功");
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        logUtil.d(this, "setContentView成功");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (updateStatusbarNextTime) {
            updateStatusbarNextTime = false;
            setStatusBar();
        }
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    @Override
    public android.app.FragmentManager getFragmentManager() {
        String msg = "请使用V4包的Fragment,避免FragmentStatePagerAdapter和FragmentPagerAdapter不可用";
        throw new UnsupportedOperationException(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        logUtil.d(this, "Activity onDestroy成功");
    }

    @SuppressWarnings("deprecation")
    protected void setStatusBar() {
        final int DEFAULT_ALPHA = 50;
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), DEFAULT_ALPHA);
    }

    @Override
    @CallSuper
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
