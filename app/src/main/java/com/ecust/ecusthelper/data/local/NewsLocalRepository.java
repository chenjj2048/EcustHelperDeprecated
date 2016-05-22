package com.ecust.ecusthelper.data.local;

import android.support.annotation.NonNull;

import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.data.base.Callback;
import com.ecust.ecusthelper.data.base.IRepository;

import java.util.List;

/**
 * Created on 2016/5/11
 *
 * @author chenjj2048
 */
//Todo:以后来写本地缓存
public class NewsLocalRepository implements IRepository.ILocalRepository<Void, List<NewsItem>> {
    @NonNull
    @Override
    public String getRepositoryName() {
        return "本地 - 新闻缓存仓库";
    }

    /**
     * 获取本地全部的数据条目
     *
     * @param callback callback
     */
    @Override
    public void getData(Void aVoid, Callback<List<NewsItem>> callback) {
        callback.onDataNotAvailable(Callback.REASON_OTHERS);
    }

    @Override
    public boolean isDataExpired() {
        return true;
    }

    public void saveItem(NewsItem item) {

    }
}
