package com.ecust.ecusthelper.ui.main;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

/**
 * Created on 2016/5/9
 *
 * @author chenjj2048
 */
public interface MainContract {
    interface View {
        DrawerLayout getDrawerLayout();

        DrawerFragment getDrawerFragment();

        void setDrawerFragment();

        void setContentFragment();

        Toolbar getToolbar();

        Activity getActivity();

        void exitApp();

        void closeDrawer();
    }

    interface Presenter {
        void setupDrawerToggleAfterFragmentLoaded();

        void onBackPressed();
    }
}
