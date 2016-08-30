package com.ecust.ecusthelper.bean.news;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
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

    /**
     * 标题
     */
    private final String title;
    /**
     * 时间
     */
    private final CompressedTime time;
    /**
     * 网络地址
     */
    private final CompressedUrl url;

    public NewsItem(String title, String time, String url) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(time);
        Objects.requireNonNull(url);
        this.title = title;
        this.time = new CompressedTime(time);
        this.url = new CompressedUrl(url);
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time.get();
    }

    public String getUrl() {
        return url.get();
    }

    @Override
    public int compareTo(@NonNull NewsItem another) {
        return this.time.compareTo(another.time);
    }

    /**
     * @return 几天前等字样
     */
    public String getRelativeTimeFromNow() {
        return time.getRelativeTimeFromNow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsItem item = (NewsItem) o;

        if (!title.equals(item.title)) return false;
        if (!time.equals(item.time)) return false;
        return url.equals(item.url);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

/**
 * =====================================
 * 以下为用到的两个类（用于减少占用空间，增加Compare的效率）
 * 1.存时间
 * 2.存地址
 * =====================================
 */

    /**
     * 存储时间的集合
     */
    static class CompressedTime implements Comparable<CompressedTime>, Serializable {
        @SuppressLint("SimpleDateFormat")
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        private static final RelativeDateFormat relativeDate = new RelativeDateFormat(dateFormat, false);

        private final String time;
        private final long timeValue;

        public CompressedTime(String time) {
            this.time = time;
            this.timeValue = parseTimeValue(time);
        }

        /**
         * 字符串解析为数字
         *
         * @param time 类似于 "2016-05-20"
         * @return 数字
         */
        private long parseTimeValue(String time) throws IllegalArgumentException {
            try {
                Date date = dateFormat.parse(time);
                return date.getTime();
            } catch (ParseException e) {
                logUtil.e(this, e.getMessage());
                throw new IllegalArgumentException("日期解析错误");
            }
        }

        public String get() {
            return time;
        }

        /**
         * @return 几天前字样
         */
        public String getRelativeTimeFromNow() {
            return relativeDate.parseDateAndTime(timeValue);
        }

        /**
         * 直接return (int)delta会产生错误，一部分数据转型后被截断，大小比较错误
         */
        @Override
        public int compareTo(@NonNull CompressedTime another) {
            long delta = this.timeValue - another.timeValue;
            return (int) Math.signum(delta);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CompressedTime that = (CompressedTime) o;

            return time.equals(that.time);

        }

        @Override
        public int hashCode() {
            return time.hashCode();
        }
    }

    /**
     * 压缩存储Url地址,节省空间
     */
    static class CompressedUrl implements Serializable {
        private static final String NEWS_URL_PREFFIX = "http://news.ecust.edu.cn/news/";
        private final String url;

        public CompressedUrl(String url) {
            this.url = url.contains(NEWS_URL_PREFFIX) ? url.replace(NEWS_URL_PREFFIX, "") : url;
        }

        public String get() {
            return url.contains("http://") ? url : NEWS_URL_PREFFIX + url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CompressedUrl that = (CompressedUrl) o;

            return url.equals(that.url);

        }

        @Override
        public int hashCode() {
            return url.hashCode();
        }
    }
}
