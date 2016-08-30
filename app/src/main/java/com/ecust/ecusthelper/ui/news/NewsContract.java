package com.ecust.ecusthelper.ui.news;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.ecust.ecusthelper.bean.news.NewsItem;

import java.util.List;

/**
 * Created on 2016/5/10
 *
 * @author chenjj2048
 */
public interface NewsContract {
    interface IView {
        Context getContext();

        String getCurrentTitle();

        int getFragmentIndex();

        void onDataNotAvailable(int reason);

        void onDataArrived(List<NewsItem> newsItems);

        void onDataGetException(Exception e);

        void stopSwipeRefreshing();

        void startNewsDetailActivity(NewsItem newsItem);
    }

    interface Presenter {
        RecyclerView.Adapter getAdapter();

        void getLatestData();

        void getMoreData();

        void hideFooterLoadingView();

        void pullToRefresh();

        void onFragmentVisible();
    }
}
