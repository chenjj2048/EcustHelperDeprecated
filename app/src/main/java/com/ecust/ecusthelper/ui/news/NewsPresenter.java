package com.ecust.ecusthelper.ui.news;

import android.support.v7.widget.RecyclerView;

import com.ecust.ecusthelper.adapter.NewsTitleDescriptionAdapter;
import com.ecust.ecusthelper.data.external.NewsRepository;
import com.ecust.ecusthelper.bean.news.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public class NewsPresenter implements NewsContract.Presenter {
    private final List<NewsItem> mList;
    private final NewsTitleDescriptionAdapter mAdapter;
    private final NewsContract.View mView;
    private final NewsRepository mNewsRepository;

    public NewsPresenter(NewsContract.View view) {
        this.mList = new ArrayList<>();
        this.mView = view;
        this.mAdapter = new NewsTitleDescriptionAdapter(view.getContext(), mList);
        this.mNewsRepository = new NewsRepository(view.getFragmentIndex());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void getLatestData() {

    }

    @Override
    public void getMoreData() {

    }
}
