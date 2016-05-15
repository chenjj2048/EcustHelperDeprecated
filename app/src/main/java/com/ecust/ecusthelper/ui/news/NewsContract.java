package com.ecust.ecusthelper.ui.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created on 2016/5/10
 *
 * @author chenjj2048
 */
public interface NewsContract {
    interface View {
        Context getContext();

        /**
         * @return 碎片对应的下标
         */
        int getFragmentIndex();

        /**
         * @return 对应的新闻网页首页
         */
        String getHomePageUrl();
    }

    interface Presenter {
        RecyclerView.Adapter getAdapter();
        void getLatestData();

        void getMoreData();
    }
}
