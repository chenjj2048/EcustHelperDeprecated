package com.ecust.ecusthelper.data;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.bean.news.NewsPageParseResult;
import com.ecust.ecusthelper.consts.NewsConst;
import com.ecust.ecusthelper.data.base.BaseLocalRemoteRepository;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.data.local.NewsLocalRepository;
import com.ecust.ecusthelper.data.remote.NewsRemoteRepository;
import com.ecust.ecusthelper.util.log.logUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2016/5/11
 *
 * @author chenjj2048
 */
public class NewsRepository extends BaseLocalRemoteRepository<Integer, List<NewsItem>,
        NewsLocalRepository, NewsRemoteRepository> {
    private final int mFragmentIndex;
    private final List<NewsItem> mList;

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
        mFragmentIndex = fragmentIndex;
        mList = new ArrayList<>();
    }

    @Override
    protected NewsRemoteRepository createRemoteRepository() {
        return new NewsRemoteRepository(mFragmentIndex);
    }

    @Override
    protected NewsLocalRepository createLocalRepository() {
        return new NewsLocalRepository();
    }

    @NonNull
    @Override
    public String getRepositoryName() {
        return "新闻仓库 - " + NewsConst.getTitle(mFragmentIndex);
    }

    /**
     * 获取下一页数据
     *
     * @param callback callback
     */
    public void getMoreData(Callback<List<NewsItem>> callback) {
        if (nextPosition > maxPosition) {
            //数据到达底部
            callback.onReachEnd();
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
        //设置运行状态
        if (isRunning) {
            logUtil.d(this, getRepositoryName() + " - 请勿重复运行");
            return;
        } else {
            isRunning = true;
        }

        //数据是否过期
        if (!getLocalRepository().isDataExpired()) {
            callback.onDataNotAvailable();
            isRunning = false;
            return;
        }

        //远程库里拉数据
        getRemoteRepository().getData(requestPage, new Callback<NewsPageParseResult>() {
            @Override
            public void onDataNotAvailable() {
                //远程数据获取失败，看本地有没有数据
                getLocalRepository().getData(null, new Callback<List<NewsItem>>() {
                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onDataArrived(List<NewsItem> newsItems) {
                        mergeDataAndSaveToLocal(newsItems);
                        callback.onDataArrived(mList);
                    }

                    @Override
                    public void onException(Exception e) {
                        callback.onException(e);
                        callback.onDataNotAvailable();
                    }
                });
                isRunning = false;
            }

            @Override
            public void onDataArrived(NewsPageParseResult result) {
                //远程数据获取成功
                Objects.requireNonNull(result);
                mergeDataAndSaveToLocal(result.getItems());
                refreshPosition(result.getCurrentPosition(), result.getTailPosition());
                callback.onDataArrived(mList);
                isRunning = false;
            }

            @Override
            public void onException(Exception e) {
                callback.onException(e);
                callback.onDataNotAvailable();
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
            getLocalRepository().saveItem(item);
        }
        Collections.sort(mList);
    }

    public List<NewsItem> getList() {
        return mList;
    }
}