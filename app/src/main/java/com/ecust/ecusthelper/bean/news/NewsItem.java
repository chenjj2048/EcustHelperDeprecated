package com.ecust.ecusthelper.bean.news;

import android.support.annotation.NonNull;

import com.ecust.ecusthelper.util.log.logUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public final class NewsItem implements Comparable<NewsItem> {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String title;
    private final String rawTimeString;
    private final String url;
    private final long timeValue;
    private final int hash;

    public NewsItem(String title, String time, String url) {
        this.timeValue = parseTimeValue(time);
        this.title = title;
        this.url = url;
        this.rawTimeString = time;
        this.hash = hashCode();
    }

    /**
     * 字符串解析为数字
     *
     * @param time 类似于 "2016-05-20"
     * @return 数字
     */
    public long parseTimeValue(String time) {
        try {
            Date date = dateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            logUtil.e(this, e.getMessage());
            throw new IllegalArgumentException("日期解析错误");
        }
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return rawTimeString;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsItem newsItem = (NewsItem) o;

        if (timeValue != newsItem.timeValue) return false;
        if (!title.equals(newsItem.title)) return false;
        if (!rawTimeString.equals(newsItem.rawTimeString)) return false;
        return url.equals(newsItem.url);

    }

    @Override
    public int hashCode() {
        if (hash != 0) return hash;

        int result = title.hashCode();
        result = 31 * result + rawTimeString.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + (int) (timeValue ^ (timeValue >>> 32));
        return result;
    }

    @Override
    public int compareTo(@NonNull NewsItem another) {
        return (int) (this.timeValue - another.timeValue);
    }
}
