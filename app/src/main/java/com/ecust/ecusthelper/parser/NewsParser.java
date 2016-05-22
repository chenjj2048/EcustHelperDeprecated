package com.ecust.ecusthelper.parser;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.annimon.stream.function.Function;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.bean.news.NewsPageParseResult;
import com.ecust.ecusthelper.consts.NewsConst;
import com.ecust.ecusthelper.util.log.logUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/5/12
 *
 * @author chenjj2048
 */
public class NewsParser implements Function<String, NewsPageParseResult> {
    private static NewsParser mInstance;

    private NewsParser() {
    }

    /**
     * 非严格的单例
     *
     * @return instance
     */
    public static NewsParser getInstance() {
        if (mInstance == null)
            mInstance = new NewsParser();
        return mInstance;
    }

    /**
     * 解析网页内容
     *
     * @param value 网页源代码，如http://news.ecust.edu.cn/news?category_id=7
     * @return 解析结果
     * @throws RuntimeException
     */
    @NonNull
    @Override
    public NewsPageParseResult apply(String value) {
        Objects.requireNonNull(value);
        logUtil.d(this, "开始解析网页新闻内容 共收到 " + value.length() + " 字符");

        final NewsPageParseResult mResult = new NewsPageParseResult();
        Document doc = Jsoup.parse(value);
        Element left = doc.getElementsByClass("left").first();

        //1、提取分类标题
        final String catalogTitle = left.getElementsByClass("left_title").text().trim();
        mResult.setCatalogTitle(catalogTitle);

        //2、提取新闻条目
        Element content = left.getElementsByClass("content").first();
        Objects.requireNonNull(content);

        Elements collection_li = content.select("ul").first().select("li");
        List<NewsItem> mList = new ArrayList<>(20);
        for (Element li : collection_li) {
            final String title = li.select("span").last().text();
            final String time = li.getElementsByClass("time").first().text();
            final String url = uniformUrl(NewsConst.NEWS_HOME_URL + li.select("a").attr("href"));

            final NewsItem item = new NewsItem(title, time, url);
            mList.add(item);
        }
        mResult.setItems(mList);

        //3、解析当前所在页数
        //4、解析末页的页数
        Element pagination = content.getElementsByClass("pagination").first();
        Objects.requireNonNull(pagination);
        Elements lis = pagination.select("li");
        for (Element li : lis) {
            final String className = li.className();
            if (className.contains("active")) {
                final int currentPage = Integer.parseInt(li.text());
                mResult.setCurrentPosition(currentPage);
            } else if (className.contains("last")) {
                final String lastPageString = li.select("a").first().attr("href");
                final int lastPage = extractPage(lastPageString);
                mResult.setTailPosition(lastPage);
            }
        }
        return mResult;
    }

    /**
     * @param input 类似于"/news?category_id=7&page=760"
     * @return 这个例子里是760
     */
    private int extractPage(String input) {
        String string = input.substring(input.lastIndexOf("=") + 1);
        return Integer.parseInt(string);
    }

    /**
     * 将http://news.ecust.edu.cn/news/35445?important=&category_id=7
     * 统一转为http://news.ecust.edu.cn/news/35445
     * .
     * 地址内容均能正常访问
     */
    private String uniformUrl(String url) {
        Objects.requireNonNull(url);

        int i = url.indexOf("?");
        if (i >= 0)
            url = url.substring(0, i);
        return url;
    }
}
