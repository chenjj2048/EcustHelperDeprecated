package com.ecust.ecusthelper.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.base.BaseFragment;
import com.ecust.ecusthelper.ui.about.AboutActivity;
import com.ecust.ecusthelper.ui.news.NewsActivity;
import com.ecust.ecusthelper.util.design.pattern.ResponsibleOfChain;

import butterknife.Bind;

/**
 * Created by 彩笔怪盗基德 on 2016/3/22.
 * https://github.com/chenjj2048
 */
public class DrawerFragment extends BaseFragment {
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    MainContract.View mMainView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mMainView = (MainContract.View) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupNavigationListener();
    }

    private void setupNavigationListener() {
        mNavigationView.setNavigationItemSelectedListener((menuItem) -> {
            mMainView.closeDrawer();
            return new MenuHandler().handler.handlerRequest(menuItem);
        });
    }

    public void scrollNavigationViewToTop() {
        if (mNavigationView != null && mNavigationView.getChildAt(0) != null)
            ((NavigationMenuView) mNavigationView.getChildAt(0)).scrollToPosition(0);
    }

    void startNewActivityByClass(Class activityClass) {
        startActivity(new Intent(getContext(), activityClass));
    }

    /**
     * 菜单点击的处理
     */
    private class MenuHandler {
        ResponsibleOfChain<MenuItem> handler;

        public MenuHandler() {
            handler = new SchoolInformationMenulHandler();
            handler.setSuccessor(new LibraryMenuHandler())
                    .setSuccessor(new SchoolLifeMenuHandler())
                    .setSuccessor(new SchoolIntroduceMenuHandler())
                    .setSuccessor(new AppMenuHandler());
        }

        /**
         * 校园资讯
         */
        public class SchoolInformationMenulHandler extends ResponsibleOfChain<MenuItem> {
            @Override
            protected boolean handlerItem(MenuItem data) {
                //Todo:        data.getGroupId();
                switch (data.getItemId()) {
                    case R.id.menu_information_news:
                        startNewActivityByClass(NewsActivity.class);
                        return true;
                    case R.id.menu_information_lecture:
                    case R.id.menu_information_notification:
                        Snackbar.make(getView(), "校园资讯" + data.getTitle(), Snackbar.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        }

        /**
         * 图书馆
         */
        public class LibraryMenuHandler extends ResponsibleOfChain<MenuItem> {
            @Override
            protected boolean handlerItem(MenuItem data) {
                switch (data.getItemId()) {
                    case R.id.menu_library_introduce:
                    case R.id.menu_library_book_query:
                    case R.id.menu_library_seats_query:
                        Snackbar.make(getView(), "图书馆" + data.getTitle(), Snackbar.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        }

        /**
         * 校园生活
         */
        public class SchoolLifeMenuHandler extends ResponsibleOfChain<MenuItem> {
            @Override
            protected boolean handlerItem(MenuItem data) {
                switch (data.getItemId()) {
                    case R.id.menu_schoollife_campuscard:
                    case R.id.menu_schoollife_weibo:
                        Snackbar.make(getView(), "学校生活" + data.getTitle(), Snackbar.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        }

        /**
         * 学校信息
         */
        public class SchoolIntroduceMenuHandler extends ResponsibleOfChain<MenuItem> {
            @Override
            protected boolean handlerItem(MenuItem data) {
                switch (data.getItemId()) {
                    case R.id.menu_school_introduce:
                    case R.id.menu_school_scenery:
                        Snackbar.make(getView(), "学校介绍" + data.getTitle(), Snackbar.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        }

        /**
         * APP设置
         */
        public class AppMenuHandler extends ResponsibleOfChain<MenuItem> {
            @Override
            protected boolean handlerItem(MenuItem data) {
                switch (data.getItemId()) {
                    case R.id.menu_app_settings:
                        //ToDo: Matherial Dialogue License
                        return true;
                    case R.id.menu_app_about:
                        startNewActivityByClass(AboutActivity.class);
                        return true;
                }
                return false;
            }
        }
    }


}
