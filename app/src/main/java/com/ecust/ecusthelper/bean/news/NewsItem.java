package com.ecust.ecusthelper.bean.news;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.ecust.ecusthelper.util.RelativeDateFormat;
import com.ecust.ecusthelper.util.log.logUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public final class NewsItem implements Comparable<NewsItem>, Serializable {
    private static final long serialVersionUID = 12305938204985L;

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final RelativeDateFormat relativeDate = new RelativeDateFormat(dateFormat, false);
    private static final String NEWS_URL_PREFFIX = "http://news.ecust.edu.cn/news/";

    /**
     * 标题
     */
    private final String title;
    /**
     * 时间
     */
    private final String rawTimeString;
    /**
     * 网络地址
     */
    private final String shorturl;
    private final long timeValue;
    private final int hash;

    public NewsItem(String title, String time, String url) {
        this.timeValue = parseTimeValue(time);
        this.title = title;
        this.shorturl = createShortUrl(url);
        this.rawTimeString = time;
        this.hash = hashCode();
    }

    /**
     * 恢复长地址
     */
    @NonNull
    private String getShortUrl() {
        String url;
        if (!shorturl.contains("http://"))
            url = NEWS_URL_PREFFIX + shorturl;
        else
            url = shorturl;

        logUtil.v("shorturl", shorturl + " " + url);
        return url;
    }

    /**
     * 转换短地址，节省空间
     */
    @NonNull
    private String createShortUrl(String url) {
        if (url.contains(NEWS_URL_PREFFIX))
            url = url.replace(NEWS_URL_PREFFIX, "");
        return url;
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
        return getShortUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsItem item = (NewsItem) o;

        if (timeValue != item.timeValue) return false;
        if (!title.equals(item.title)) return false;
        if (!rawTimeString.equals(item.rawTimeString)) return false;
        return shorturl.equals(item.shorturl);

    }

    @Override
    public int hashCode() {
        if (hash != 0) return hash;
        int result = title.hashCode();
        result = 31 * result + rawTimeString.hashCode();
        result = 31 * result + shorturl.hashCode();
        result = 31 * result + (int) (timeValue ^ (timeValue >>> 32));
        return result;
    }

    @Override
    public int compareTo(@NonNull NewsItem another) {
        long delta = this.timeValue - another.timeValue;
        /**
         * 直接return (int)delta会产生错误，一部分数据转型后被截断，大小比较错误
         */
        if (delta > 0)
            return 1;
        else if (delta < 0)
            return -1;
        else
            return 0;
    }

    /**
     * @return 几天前等字样
     */
    public String getRelativeTime() {
        return relativeDate.parseDateAndTime(timeValue);
    }
}
