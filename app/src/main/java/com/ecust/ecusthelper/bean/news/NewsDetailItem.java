package com.ecust.ecusthelper.bean.news;

import com.annimon.stream.Objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2016/6/19
 *
 * @author chenjj2048
 */
public class NewsDetailItem implements Serializable {
    private static final long serialVersionUID = 594209572619375L;

    private String catalog = "";            //版块名称
    private String title = "";              //新闻标题
    private String release_time = "";       //发表日期
    private String news_Source = "";        //稿件来源、来稿单位
    private String author = "";             //作者
    private String photo_author = "";       //摄影
    private String editor = "";             //编辑
    private String count_of_visit = "";     //访问量
    private List<ContentLine> mContentLineList;     //每一行内容（文字或图片）

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseTime() {
        return release_time;
    }

    public void setReleaseTime(String release_time) {
        this.release_time = release_time;
    }

    public String getNewsSource() {
        return news_Source;
    }

    public void setNewsSource(String news_Source) {
        this.news_Source = news_Source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhotoAuthor() {
        return photo_author;
    }

    public void setPhotoAuthor(String photo_author) {
        this.photo_author = photo_author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCountOfVisits() {
        return count_of_visit;
    }

    public void setCountOfVisits(String count_of_visit) {
        this.count_of_visit = count_of_visit;
    }

    public List<ContentLine> getContentLineList() {
        return mContentLineList;
    }

    public void setContentLineList(List<ContentLine> mContentLineList) {
        this.mContentLineList = mContentLineList;
    }

    @Override
    public String toString() {
        return "NewsDetailItem{" +
                "catalog='" + catalog + '\'' +
                ", title='" + title + '\'' +
                ", release_time='" + release_time + '\'' +
                ", news_Source='" + news_Source + '\'' +
                ", author='" + author + '\'' +
                ", photo_author='" + photo_author + '\'' +
                ", editor='" + editor + '\'' +
                ", count_of_visit='" + count_of_visit + '\'' +
                ", mContentLineList=" + mContentLineList +
                '}';
    }

    /**
     * 每一行的解析内容
     */
    public static class ContentLine implements Serializable {
        private static final long serialVersionUID = -2149402870754667710L;

        final boolean isPicture;
        final String content;
        final String pic_url;

        /**
         * @param string    字符串
         * @param isPicture 图片或文本
         */
        private ContentLine(String string, boolean isPicture) {
            Objects.requireNonNull(string);
            this.isPicture = isPicture;
            if (!isPicture) {
                content = string;
                pic_url = null;
            } else {
                pic_url = string;
                content = null;
            }
        }

        public static ContentLine ofText(String text) {
            return new ContentLine(text, false);
        }

        public static ContentLine ofPicture(String pictureUrl) {
            return new ContentLine(pictureUrl, true);
        }

        public boolean isPicture() {
            return isPicture;
        }

        public String getPictureUrl() {
            return Objects.requireNonNull(pic_url);
        }

        public String getText() {
            return Objects.requireNonNull(content);
        }
    }
}
