package com.ecust.ecusthelper.ui.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.base.BaseMvpFragment;
import com.ecust.ecusthelper.consts.NewsFragmentTitleConst;
import com.ecust.ecusthelper.customview.DividerItemDecoration;

import butterknife.Bind;

/**
 * Created on 2016/4/16
 *
 * @author chenjj2048
 */
public class NewsFragment extends BaseMvpFragment<NewsContract.Presenter> implements NewsContract.View {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private int mFragmentIndex;

    public static NewsFragment newInstance(int position) {
        NewsFragment mFragment = new NewsFragment();
        mFragment.mFragmentIndex = position;
        return mFragment;
    }

    @NonNull
    @Override
    protected NewsContract.Presenter createPresenter() {
        return new NewsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        final RecyclerView.Adapter mAdapter = getPresenter().getAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public int getFragmentIndex() {
        return mFragmentIndex;
    }

    @Override
    public String getHomePageUrl() {
        return NewsFragmentTitleConst.getUrl(mFragmentIndex);
    }


}
