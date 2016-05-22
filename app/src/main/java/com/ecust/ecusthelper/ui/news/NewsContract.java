package com.ecust.ecusthelper.ui.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ecust.ecusthelper.bean.news.NewsItem;

import java.util.List;

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

        void onDataNotAvailable(int reason);

        /**
         * 数据获取成功
         *
         * @param latestRefreshed 刷新第一页，还是获取下一页
         * @param newsItems       数据结果
         */
        void onDataArrived(boolean latestRefreshed, List<NewsItem> newsItems);

        void onDataGetExecption(Exception e);

        /**
         * 获取当前版块标题
         *
         * @return 当前版块标题
         */
        String getCurrentTitle();
    }

    interface Presenter {
        RecyclerView.Adapter getAdapter();

        void getLatestData();

        void getMoreData();
    }
}
