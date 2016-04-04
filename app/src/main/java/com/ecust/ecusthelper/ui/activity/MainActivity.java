package com.ecust.ecusthelper.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.ui.fragment.ContentFragment;
import com.ecust.ecusthelper.ui.fragment.DrawerFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //Todo:轮循、CordinderLayout、视差动画

    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    DrawerFragment mDrawerFragment;
    ContentFragment mContentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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
    public void onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawer();
            return;
        }
        Snackbar.make(this.getCurrentFocus(), "clos", Snackbar.LENGTH_LONG).show();
        super.onBackPressed();
    }

    private void init() {
        ButterKnife.bind(this);
        setupDrawerFragment();
        setupContentFragment();
        setupDrawerLayout();
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle("openeded");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerFragment.scrollToHead();
                setTitle("closeddd");
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
}
