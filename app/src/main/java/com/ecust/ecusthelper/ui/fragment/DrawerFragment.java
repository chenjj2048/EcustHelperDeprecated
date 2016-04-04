package com.ecust.ecusthelper.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.ui.activity.MainActivity;
import com.ecust.ecusthelper.util.design.pattern.ResponsibleOfChain;
import com.ecust.ecusthelper.util.logUtils.logUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 彩笔怪盗基德 on 2016/3/22.
 * https://github.com/chenjj2048
 */
public class DrawerFragment extends Fragment {
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_drawer, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        MenuHandlerCollection handlerGroup = new MenuHandlerCollection();
        mNavigationView.setNavigationItemSelectedListener((menuItem) -> {
            boolean hasHandler = handlerGroup.handlerRequest(menuItem);
            if (!hasHandler)
                logUtil.w(this, "没有这个Menu对应的点击处理事件 - " + menuItem);
            ((MainActivity) getActivity()).closeDrawer();
            return hasHandler;
        });
    }

    public void scrollToHead() {
        ((NavigationMenuView) mNavigationView.getChildAt(0)).scrollToPosition(0);
    }

    /**
     * 菜单点击的处理
     */
    private class MenuHandlerCollection {
        ResponsibleOfChain<MenuItem> handler;

        public MenuHandlerCollection() {
            handler = new SchoolInformationMenulHandler();
            handler.setSuccessor(new LibraryMenuHandler())
                    .setSuccessor(new SchoolLifeMenuHandler())
                    .setSuccessor(new SchoolIntroduceMenuHandler())
                    .setSuccessor(new AppMenuHandler());
        }

        public boolean handlerRequest(MenuItem menuItem) {
            return handler.handlerRequest(menuItem);
        }
    }

    /**
     * 校园资讯
     */
    private class SchoolInformationMenulHandler extends ResponsibleOfChain<MenuItem> {
        @Override
        protected boolean handlerItem(MenuItem data) {
            switch (data.getItemId()) {
                case R.id.menu_information_news:
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
    private class LibraryMenuHandler extends ResponsibleOfChain<MenuItem> {
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
    private class SchoolLifeMenuHandler extends ResponsibleOfChain<MenuItem> {
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
     * 学校介绍
     */
    private class SchoolIntroduceMenuHandler extends ResponsibleOfChain<MenuItem> {
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
    private class AppMenuHandler extends ResponsibleOfChain<MenuItem> {
        @Override
        protected boolean handlerItem(MenuItem data) {
            switch (data.getItemId()) {
                case R.id.menu_app_settings:
                case R.id.menu_app_about:
                    Snackbar.make(getView(), "APP" + data.getTitle(), Snackbar.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    }
}
