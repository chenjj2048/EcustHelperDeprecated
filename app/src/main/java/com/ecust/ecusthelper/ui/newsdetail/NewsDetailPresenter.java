package com.ecust.ecusthelper.ui.newsdetail;

import android.text.TextUtils;

import com.ecust.ecusthelper.adapter.NewsDetailAdapter;
import com.ecust.ecusthelper.bean.news.NewsDetailItem;
import com.ecust.ecusthelper.data.parser.NewsDetailParser;
import com.ecust.ecusthelper.util.network.HttpUrlConnectionUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2016/6/20
 *
 * @author chenjj2048
 */
public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private final NewsDetailAdapter mAdapter;
    private final NewsDetailContract.IView mView;
    /**
     * 解析的地址
     */
    private final String mUrl;

    /**
     * 版块名称
     */
    private final String mCatalogName;

    /**
     * @param mView       view
     * @param catalogName 版块名称
     * @param url         解析的地址
     */
    public NewsDetailPresenter(NewsDetailContract.IView mView, String catalogName, String url) {
        this.mView = mView;
        this.mUrl = url;
        this.mCatalogName = catalogName;
        this.mAdapter = new NewsDetailAdapter(mView.getContext());
    }

    @Override
    public NewsDetailAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void fetchData() {
        Observable.just(mUrl)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(HttpUrlConnectionUtil::getString)
                .filter(s -> !TextUtils.isEmpty(s))
                .map((s) -> new NewsDetailParser(mCatalogName).apply(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetailItem>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(NewsDetailItem newsDetailItem) {
                        updateView(newsDetailItem);
                    }
                });
    }

    private void updateView(NewsDetailItem newsDetailItem) {
        mAdapter.setNewsDetailItem(newsDetailItem);
        mAdapter.notifyDataSetChanged();
    }
}
