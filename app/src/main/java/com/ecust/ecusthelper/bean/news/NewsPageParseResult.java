package com.ecust.ecusthelper.bean.news;

import java.util.List;

/**
 * Created on 2016/5/12
 *
 * @author chenjj2048
 */

/**
 * 一个页面中所包含的信息内容
 * 如：http://news.ecust.edu.cn/news?category_id=7
 */
public class NewsPageParseResult {
    private int tailPosition;
    private int currentPosition;
    private String catalogTitle;
    private List<NewsItem> items;

    public String getCatalogTitle() {
        return catalogTitle;
    }

    public void setCatalogTitle(String catalogTitle) {
        this.catalogTitle = catalogTitle;
    }

    public int getTailPosition() {
        return tailPosition;
    }

    public void setTailPosition(int tailPosition) {
        this.tailPosition = tailPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public List<NewsItem> getItems() {
        return items;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "NewsPageParseResult{" +
                "tailPosition=" + tailPosition +
                ", currentPosition=" + currentPosition +
                ", catalogTitle='" + catalogTitle + '\'' +
                ", items=" + items +
                '}';
    }
}
