package com.ecust.ecusthelper.ui.newsdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.base.BaseMvpActivity;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.util.log.logUtil;

import butterknife.Bind;

public class NewsDetailActivity extends BaseMvpActivity<NewsDetailContract.Presenter> implements NewsDetailContract.IView {
    public static final String INTENT_KEY_DATA = "数据内容";
    public static final String INTENT_KEY_CATALOG = "版块标题";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private String mCatalogName;

    /**
     * 由上一级Activity传来的数据
     */
    private NewsItem mNewsItem;

    @NonNull
    @Override
    protected NewsDetailContract.Presenter createPresenter() {
        return new NewsDetailPresenter(this, mCatalogName, mNewsItem.getUrl());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setupToolbar(mToolbar);

        initIntentData();
        setupRecyclerView();
        fetchData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loadFailueView() {

    }

    private void initIntentData() {
        mNewsItem = (NewsItem) getIntent().getSerializableExtra(INTENT_KEY_DATA);
        mCatalogName = getIntent().getStringExtra(INTENT_KEY_CATALOG);
        logUtil.d(this, mCatalogName + " " + mNewsItem.getTitle() + " " + mNewsItem.getUrl());
    }

    private void fetchData() {
        getPresenter().fetchData();
    }

    private void setupRecyclerView() {
        final RecyclerView view = mRecyclerView;
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        final RecyclerView.Adapter mAdapter = getPresenter().getAdapter();

        view.setLayoutManager(mLayoutManager);
        view.setAdapter(mAdapter);
    }


}
