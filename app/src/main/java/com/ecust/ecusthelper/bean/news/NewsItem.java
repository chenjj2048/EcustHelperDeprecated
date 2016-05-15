package com.ecust.ecusthelper.bean.news;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public final class NewsItem implements Comparable<NewsItem> {
    private final String title;
    private final String time;
    private final String url;

    public NewsItem(String title, String time, String url) {
        this.title = title;
        this.time = time;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    //Todo:
    @Override
    public int compareTo(NewsItem another) {
        return 0;
    }
}
