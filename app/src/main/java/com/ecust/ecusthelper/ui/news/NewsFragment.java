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
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.consts.NewsConst;
import com.ecust.ecusthelper.customview.DividerItemDecoration;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.util.log.logUtil;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import cn.trinea.android.common.util.ToastUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

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
        final View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        logUtil.d(this, "onCreateView成功 - " + getCurrentTitle());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();

        //延迟加载
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((v) -> {
                    getPresenter().getLatestData();
                });
        logUtil.d(this, "onViewCreated成功 - " + getCurrentTitle());
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
    public String getCurrentTitle() {
        return NewsConst.getTitle(mFragmentIndex);
    }

    @Override
    public void onDataNotAvailable(int reason) {
        String msg = "【" + getCurrentTitle() + "】";
        switch (reason) {
            case Callback.REASON_DATA_STILL_VALID:
                msg += "数据未过期，请勿频繁刷新";
                break;
            case Callback.REASON_NO_MORE_DATA:
                msg += "已无更多数据";
                break;
            case Callback.REASON_OTHERS:
                msg += "数据获取失败";
                break;
        }
        ToastUtils.show(getContext(), msg);
        logUtil.d(this, msg);
    }

    @Override
    public void onDataArrived(boolean latestRefreshed, List<NewsItem> newsItems) {
        getPresenter().getAdapter().notifyDataSetChanged();

        final String msg = String.format(Locale.CHINA, "【%s】新获取数据 %d 条",
                getCurrentTitle(), newsItems.size());
        logUtil.d(this, msg);
        ToastUtils.show(getContext(), msg);
    }

    @Override
    public void onDataGetExecption(Exception e) {
        logUtil.e(this, "数据获取异常 - Cause By: " + e.getMessage());
        e.printStackTrace();
    }
}
