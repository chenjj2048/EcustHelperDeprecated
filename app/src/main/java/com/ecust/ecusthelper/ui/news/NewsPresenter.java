package com.ecust.ecusthelper.ui.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ecust.ecusthelper.adapter.NewsItemAdapter;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.data.NewsRepository;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.di.compent.DaggerNewsPresenterCompent;
import com.ecust.ecusthelper.di.module.NewsPresenterModule;
import com.ecust.ecusthelper.util.log.logUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public class NewsPresenter implements NewsContract.Presenter {
    @Inject
    NewsItemAdapter mAdapter;
    @Inject
    NewsContract.IView view;
    @Inject
    NewsRepository mNewsRepository;

    public NewsPresenter(NewsContract.IView view) {
        DaggerNewsPresenterCompent.builder()
                .newsPresenterModule(new NewsPresenterModule(view))
                .build()
                .inject(this);
        mAdapter.setOnItemClickListener((View v, NewsItem newsItem, int pos) -> {
            //跳转至新Activity
            view.startNewsDetailActivity(newsItem);
        });
        logUtil.d(this, "Presenter创建成功 - " + view.getCurrentTitle());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    /**
     * 上拉获取最新数据
     */
    @Override
    public void getLatestData() {
        logUtil.d(this, "开始获取最新数据 - " + view.getCurrentTitle());

        final int FIRST_PAGE = 1;
        mNewsRepository.getData(FIRST_PAGE, new Callback<List<NewsItem>>() {
            @Override
            public void onDataNotAvailable(int reason) {
                stopSwipeRefreshing();
                view.onDataNotAvailable(reason);
            }

            @Override
            public void onDataArrived(List<NewsItem> newsItems) {
                stopSwipeRefreshing();
                mAdapter.notifyDataSetChanged();
                view.onDataArrived(newsItems);
            }

            @Override
            public void onException(Exception e) {
                stopSwipeRefreshing();
                view.onDataGetException(e);
            }
        });
    }

    /**
     * 获取下一页数据
     */
    @Override
    public void getMoreData() {
        logUtil.d(this, "获取更多数据 - " + view.getCurrentTitle());
        mNewsRepository.getMoreData(new Callback<List<NewsItem>>() {
            @Override
            public void onDataNotAvailable(int reason) {
                view.onDataNotAvailable(reason);
            }

            @Override
            public void onDataArrived(List<NewsItem> newsItems) {
                mAdapter.notifyDataSetChanged();
                view.onDataArrived(newsItems);
            }

            @Override
            public void onException(Exception e) {
                view.onDataGetException(e);
            }
        });
    }

    /**
     * 隐藏底部正在加载
     */
    @Override
    public void hideFooterLoadingView() {
        mAdapter.hideFooterLoaing();
    }

    /**
     * 停止下拉刷新的圈圈旋转
     */
    private void stopSwipeRefreshing() {
        view.stopSwipeRefreshing();
    }

    @Override
    public void pullToRefresh() {
        getLatestData();
        if (mAdapter.getItemCount() <= NewsItemAdapter.COUNT_OF_FOOTER) {
            view.stopSwipeRefreshing();
        }
    }

    @Override
    public void onFragmentVisible() {
        getLatestData();
    }
}
