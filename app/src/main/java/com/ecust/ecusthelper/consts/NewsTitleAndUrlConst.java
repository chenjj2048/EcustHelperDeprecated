package com.ecust.ecusthelper.consts;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */

/**
 * 七个版块对应的标题及地址
 */
public class NewsTitleAndUrlConst {
    public static final String NEWS_HOME_URL = "http://news.ecust.edu.cn";
    private static final List<Pair<String, String>> mPages;

    static {
        mPages = new ArrayList<>();
        mPages.add(new Pair<>("校园要闻", "http://news.ecust.edu.cn/news?important=1"));
        mPages.add(new Pair<>("综合新闻", "http://news.ecust.edu.cn/news?category_id=7"));
        mPages.add(new Pair<>("招生就业", "http://news.ecust.edu.cn/news?category_id=65"));
        mPages.add(new Pair<>("合作交流", "http://news.ecust.edu.cn/news?category_id=38"));
        mPages.add(new Pair<>("深度报道", "http://news.ecust.edu.cn/news?category_id=60"));
        mPages.add(new Pair<>("图说华理", "http://news.ecust.edu.cn/news?category_id=68"));
        mPages.add(new Pair<>("媒体华理", "http://news.ecust.edu.cn/news?category_id=21"));
    }

    public static int getCatalogCount() {
        return mPages.size();
    }

    public static String getTitle(int position) {
        checkRange(position);
        return mPages.get(position).first;
    }

    public static String getUrl(int position) {
        checkRange(position);
        return mPages.get(position).second;
    }

    private static void checkRange(int position) {
        if (position < 0 || position >= mPages.size())
            throw new IllegalArgumentException("版块下标不正确，数组越界");
    }
}
