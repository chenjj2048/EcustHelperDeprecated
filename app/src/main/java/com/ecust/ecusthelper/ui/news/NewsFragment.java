package com.ecust.ecusthelper.ui.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.baseAndCommon.BaseMvpFragment;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.consts.NewsConst;
import com.ecust.ecusthelper.customview.DividerItemDecoration;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.util.SizeUtil;
import com.ecust.ecusthelper.util.log.logUtil;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;

/**
 * Created on 2016/4/16
 *
 * @author chenjj2048
 */
public class NewsFragment extends BaseMvpFragment<NewsContract.Presenter> implements NewsContract.IView {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

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
        //默认显示加载中页面
        logUtil.v(this, getCurrentTitle() + " - onCreateView");
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logUtil.v(this, getCurrentTitle() + " - onViewCreated");

        setupRecyclerView();
        setupSwipeRefreshLayout();
    }

    private void setupSwipeRefreshLayout() {
        final SwipeRefreshLayout view = mSwipeRefreshLayout;
        view.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light);
        view.setProgressViewOffset(false, 0, SizeUtil.dp2px(getContext(), 24));
        //下拉刷新
        view.setOnRefreshListener(() -> getPresenter().getLatestData());
    }

    private void setupRecyclerView() {
        final RecyclerView view = mRecyclerView;
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        final RecyclerView.Adapter mAdapter = getPresenter().getAdapter();

        view.setLayoutManager(mLayoutManager);
        view.setAdapter(mAdapter);
        view.setItemAnimator(new DefaultItemAnimator());
        view.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        //上拉加载
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
                //滑至底部
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() + 1 != mAdapter.getItemCount())
                    return;
                //尚且不足一个页面
                if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) return;
                // 加载更多
                getPresenter().getMoreData();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logUtil.v(this, getCurrentTitle() + " - onDetach");
    }

    /**
     * 由ViewPager初始化结束或滑动引起
     */
    public void onCurrentFragmentSelected() {
        getPresenter().getLatestData();
    }

    @Override
    public void stopSwipeRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
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
                Toast.makeText(getContext(), "已无更多数据", Toast.LENGTH_SHORT).show();
                break;
            case Callback.REASON_OTHERS:
                msg += "数据获取失败";
                break;
        }
        logUtil.d(this, msg);
    }

    @Override
    public void onDataArrived(List<NewsItem> newsItems) {
        final String msg = String.format(Locale.CHINA, "【%s】新获取数据 %d 条",
                getCurrentTitle(), newsItems.size());
        logUtil.d(this, msg);
    }

    @Override
    public void onDataGetException(Exception e) {
        logUtil.e(this, e.toString());
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String toString() {
        return super.toString() + " " + NewsConst.getTitle(mFragmentIndex);
    }
}
