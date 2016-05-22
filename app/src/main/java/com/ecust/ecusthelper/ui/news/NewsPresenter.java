package com.ecust.ecusthelper.ui.news;

import android.support.v7.widget.RecyclerView;

import com.ecust.ecusthelper.adapter.NewsItemAdapter;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.data.NewsRepository;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.util.log.logUtil;

import java.util.List;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public class NewsPresenter implements NewsContract.Presenter {
    private final NewsItemAdapter mAdapter;
    private final NewsContract.View view;
    private final NewsRepository mNewsRepository;

    public NewsPresenter(NewsContract.View view) {
        this.view = view;
        this.mNewsRepository = new NewsRepository(view.getFragmentIndex());
        this.mAdapter = new NewsItemAdapter(view.getContext(), mNewsRepository.getList());
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
        logUtil.d(this, "获取最新数据 - " + view.getCurrentTitle());
        final int FIRST_PAGE = 1;
        mNewsRepository.getData(FIRST_PAGE, new Callback<List<NewsItem>>() {
            @Override
            public void onDataNotAvailable(int reason) {
                view.onDataNotAvailable(reason);
            }

            @Override
            public void onDataArrived(List<NewsItem> newsItems) {
                view.onDataArrived(true, newsItems);
            }

            @Override
            public void onException(Exception e) {
                view.onDataGetExecption(e);
            }
        });
    }

    /**
     * 下滑获取下一页
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
                view.onDataArrived(false, newsItems);
            }

            @Override
            public void onException(Exception e) {
                view.onDataGetExecption(e);
            }
        });
    }
}
