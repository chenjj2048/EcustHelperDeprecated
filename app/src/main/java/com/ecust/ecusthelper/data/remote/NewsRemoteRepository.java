package com.ecust.ecusthelper.data.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.bean.news.NewsPageParseResult;
import com.ecust.ecusthelper.consts.NewsConst;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.data.base.IRepository;
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
public class NewsRemoteRepository implements IRepository.IRemoteRepository<Integer, NewsPageParseResult> {
    /**
     * 实际网页地址类似于如下格式：xxxx + "&page=" + 页数
     * http://news.ecust.edu.cn/news?important=1&page=2
     */
    private final String mUrlPrefix;

    /**
     * @param fragmentIndex 对应新闻版块的下标
     * @see NewsConst
     */
    public NewsRemoteRepository(int fragmentIndex) {
        this.mUrlPrefix = NewsConst.getUrl(fragmentIndex) + "&page=";
    }

    @NonNull
    @Override
    public String getRepositoryName() {
        return "远程-新闻仓库";
    }

    /**
     * @param requestPage 请求的页数
     * @param callback    callback
     */
    @Override
    public void getData(Integer requestPage, Callback<NewsPageParseResult> callback) {
        Objects.requireNonNull(callback);
        if (requestPage <= 0) {
            callback.onException(new IllegalArgumentException("页数至少大于等于1"));
            return;
        }

        final String url = mUrlPrefix + requestPage;
        logUtil.d(this, getRepositoryName() + " - 第" + requestPage + "页 " + url);

        //开始获取数据
        Observable.just(url)
                .map(input -> (HttpUrlConnectionUtil.getString(input)))     //网络数据获取
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
                        callback.onException(new Exception(e));
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(NewsPageParseResult result) {
                        Objects.requireNonNull(result);
                        callback.onDataArrived(result);
                    }
                });
    }
}
