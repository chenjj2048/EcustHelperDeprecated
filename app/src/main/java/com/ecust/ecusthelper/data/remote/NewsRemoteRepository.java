package com.ecust.ecusthelper.data.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.bean.news.NewsPageParseResult;
import com.ecust.ecusthelper.consts.NewsFragmentTitleConst;
import com.ecust.ecusthelper.parser.NewsParser;
import com.ecust.ecusthelper.util.log.logUtil;
import com.ecust.ecusthelper.util.network.httpurlconnection.HttpUrlConnectionUtil;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2016/5/11
 *
 * @author chenjj2048
 */
public class NewsRemoteRepository {
    /**
     * 实际网页地址类似于如下格式：xxxx + "&page=" + 页数
     * http://news.ecust.edu.cn/news?important=1&page=2
     */
    private final String mUrlPrefix;
    /**
     * 通过这个值能找到对应的url前缀，对应不同新闻板块内容的url
     */
    private final int mFragmentIndex;

    /**
     * @param fragmentIndex 对应新闻版块的下标
     * @see NewsFragmentTitleConst
     */
    public NewsRemoteRepository(int fragmentIndex) {
        this.mFragmentIndex = fragmentIndex;
        this.mUrlPrefix = NewsFragmentTitleConst.getUrl(fragmentIndex) + "&page=";
    }

    public void getDataFromPageIndex(int page, @NonNull Callback callback) {
        if (page <= 0) {
            callback.onFailure(new IllegalArgumentException("页数至少大于等于1"));
            return;
        }

        logUtil.d(this, "开始获取远程数据 - 第" + page + "页");

        //开始获取数据
        Observable.just(page)
                .filter(i -> (i > 0))
                .map(i -> mUrlPrefix + i)
                .map(url -> (HttpUrlConnectionUtil.getString(url)))     //网络数据获取
                .filter(s -> (!TextUtils.isEmpty(s)))
                .map(s1 -> NewsParser.getInstance().apply(s1))          //解析返回结果到特定形式
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsPageParseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        logUtil.d(this, e.toString());
                        if (e instanceof Exception)
                            callback.onFailure((Exception) e);
                        else
                            callback.onFailure(new Exception(e));
                    }

                    @Override
                    public void onNext(NewsPageParseResult url) {
                        Objects.requireNonNull(url);
                        callback.onSuccess(page, url.toString());
                    }
                });
    }

    @Override
    public String toString() {
        return "远程数据来源 - " + NewsFragmentTitleConst.getTitle(mFragmentIndex)
                + " " + NewsFragmentTitleConst.getUrl(mFragmentIndex);
    }

    public interface Callback {
        void onSuccess(int pageIndex, String t);

        void onFailure(Exception e);
    }
}
