package com.ecust.ecusthelper.data.external;

import android.support.annotation.NonNull;

import com.ecust.ecusthelper.consts.NewsFragmentTitleConst;
import com.ecust.ecusthelper.data.local.NewsLocalRepository;
import com.ecust.ecusthelper.data.remote.NewsRemoteRepository;
import com.ecust.ecusthelper.bean.news.NewsItem;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2016/5/11
 *
 * @author chenjj2048
 */
public class NewsRepository {
    private final ExecutorService mExecutor;
    private final NewsLocalRepository mLocalRepository;
    private final NewsRemoteRepository mRemoteRepository;

    /**
     * @param fragmentIndex 对应新闻版块的下标
     * @see NewsFragmentTitleConst
     */
    public NewsRepository(int fragmentIndex) {
        mExecutor = Executors.newSingleThreadExecutor();
        mLocalRepository = new NewsLocalRepository();
        mRemoteRepository = new NewsRemoteRepository(fragmentIndex);
    }

    /**
     * 获取新闻消息条目
     *
     * @param pageIndex 第几页
     * @param callBack  callback
     */
 public    void get(int pageIndex, @NonNull Callback callBack) {

        //1、数据仍有效时立即退出
        if (isDataValid()) {
            callBack.onDataStillValid();
            return;
        }

        //2、获取远程数据
        mRemoteRepository.getDataFromPageIndex(pageIndex, new NewsRemoteRepository.Callback() {
            @Override
            public void onSuccess(int pageIndex, String data) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        //远程取数据-》1）成功，缓存，返回；
        // 2）失败，读本地数据，返回
    }

    /**
     * 判读数据是否有效
     *
     * @return false—数据过期  true—数据仍然后效
     */
    private boolean isDataValid() {
        return false;
    }

    public interface Callback {
        //不需更新数据
        default void onDataStillValid() {
        }

        void onDataNotAvailable();

        void onDataArrive(int pageIndex, Set<NewsItem> data);
    }
}
