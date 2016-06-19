package com.ecust.ecusthelper.data;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.bean.news.NewsPageParseResult;
import com.ecust.ecusthelper.consts.NewsConst;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.data.base.IRepository;
import com.ecust.ecusthelper.data.local.NewsLocalRepository;
import com.ecust.ecusthelper.data.remote.NewsRemoteRepository;
import com.ecust.ecusthelper.di.compent.DaggerNewsRepositoryCompent;
import com.ecust.ecusthelper.di.module.NewsRepositoryModule;
import com.ecust.ecusthelper.util.log.logUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created on 2016/5/11
 *
 * @author chenjj2048
 */
public final class NewsRepository implements IRepository<Integer, List<NewsItem>> {
    private final List<NewsItem> mList = new ArrayList<>();
    @Inject
    int mFragmentIndex;
    @Inject
    NewsLocalRepository mLocalRepository;
    @Inject
    NewsRemoteRepository mRemoteRepository;
    private boolean isRunning;
    /**
     * 网页中下一个要读取的页数，初始默认为1
     */
    private int nextPosition = 1;
    /**
     * 网页中能获取到数据的最大页数，初始默认为1
     */
    private int maxPosition = 1;

    /**
     * @param fragmentIndex 对应新闻版块的下标
     * @see NewsConst
     */
    public NewsRepository(int fragmentIndex) {
        DaggerNewsRepositoryCompent.builder()
                .newsRepositoryModule(new NewsRepositoryModule(fragmentIndex))
                .build()
                .inject(this);
        Objects.requireNonNull(mLocalRepository);
        Objects.requireNonNull(mRemoteRepository);
    }

    @NonNull
    @Override
    public String getRepositoryName() {
        return "新闻仓库 - " + NewsConst.getTitle(mFragmentIndex);
    }

    @Override
    public boolean isDataExpired() {
        return true;
    }

    /**
     * 获取下一页数据
     *
     * @param callback callback
     */
    public void getMoreData(Callback<List<NewsItem>> callback) {
        if (nextPosition > maxPosition) {
            //数据到达底部
            callback.onDataNotAvailable(Callback.REASON_NO_MORE_DATA);
            return;
        }
        getData(nextPosition, callback);
    }

    /**
     * 获取新闻消息条目
     *
     * @param requestPage 第几页
     * @param callback    callback
     */
    @Override
    public void getData(Integer requestPage, Callback<List<NewsItem>> callback) {
        final String TAG = getRepositoryName() + " 第 " + requestPage + " 页";
        logUtil.d(this, TAG + " - 准备获取数据");
        //设置运行状态
        if (isRunning) {
            logUtil.d(this, getRepositoryName() + " - 请勿重复运行");
            return;
        } else {
            isRunning = true;
        }

        //数据是否过期
        if (!mLocalRepository.isDataExpired()) {
            logUtil.d(this, TAG + " - 数据未过期仍有效");
            callback.onDataNotAvailable(Callback.REASON_DATA_STILL_VALID);
            isRunning = false;
            return;
        }

        logUtil.d(this, TAG + " - 开始获取远程数据");
        mRemoteRepository.getData(requestPage, new Callback<NewsPageParseResult>() {
            @Override
            public void onDataNotAvailable(int reason) {
                logUtil.d(this, TAG + " - 远程数据获取失败，开始获取本地数据");
                mLocalRepository.getData(null, new Callback<List<NewsItem>>() {
                    @Override
                    public void onDataNotAvailable(int reason) {
                        logUtil.d(this, TAG + " - 本地数据不可得");
                        callback.onDataNotAvailable(reason);
                        isRunning = false;
                    }

                    @Override
                    public void onDataArrived(List<NewsItem> newsItems) {
                        logUtil.d(this, TAG + " - 本地数据获取成功，共" + newsItems.size() + "条");
                        mergeDataAndSaveToLocal(newsItems);
                        callback.onDataArrived(mList);
                        isRunning = false;
                    }

                    @Override
                    public void onException(Exception e) {
                        logUtil.d(this, TAG + " - 本地数据获取异常 " + e.getMessage());
                        callback.onException(e);
                        isRunning = false;
                    }
                });
            }

            @Override
            public void onDataArrived(NewsPageParseResult result) {
                logUtil.d(this, TAG + " - 远程数据获取成功，共" + result.getItems().size() + "条");
                mergeDataAndSaveToLocal(result.getItems());
                refreshPosition(result.getCurrentPosition(), result.getTailPosition());
                callback.onDataArrived(mList);
                isRunning = false;
            }

            @Override
            public void onException(Exception e) {
                logUtil.d(this, TAG + " - 远程数据获取异常 " + e.getMessage());
                callback.onException(e);
                isRunning = false;
            }
        });
    }

    /**
     * 刷新当前已经获取到网页中第几页了
     * 以及刷新能获取网络数据的最大页数的位置
     *
     * @param currentPosition 当前位置，最小为1
     * @param tailPosition    最后的位置，如果超过这个值，获取不到有效的网络数据
     */
    private void refreshPosition(int currentPosition, int tailPosition) {
        int next = currentPosition + 1;
        if (next > this.nextPosition)
            this.nextPosition = next;

        if (tailPosition > maxPosition)
            maxPosition = tailPosition;
    }

    /**
     * 合并新数据，并保存至本地缓存
     *
     * @param newsItems 新获取的数据
     */
    private void mergeDataAndSaveToLocal(List<NewsItem> newsItems) {
        if (newsItems == null) return;
        for (NewsItem item : newsItems) {
            if (mList.contains(item)) continue;
            //合并数据
            mList.add(item);
            //缓存数据
            mLocalRepository.saveItem(item);
        }
        Collections.sort(mList, Collections.reverseOrder());
    }

    public List<NewsItem> getList() {
        return mList;
    }
}
