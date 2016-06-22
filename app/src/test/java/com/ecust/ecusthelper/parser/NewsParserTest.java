package com.ecust.ecusthelper.parser;

import com.ecust.ecusthelper.bean.news.NewsPageParseResult;
import com.ecust.ecusthelper.data.parser.NewsParser;
import com.ecust.ecusthelper.util.network.HttpUrlConnectionUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created on 2016/5/13
 *
 * @author chenjj2048
 */
@RunWith(Parameterized.class)
public class NewsParserTest {
    String url;
    String expect;
    String htmlResult;

    public NewsParserTest(String expect, String url) {
        this.url = url;
        this.expect = expect;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        //这里NewsFragmentTitleConst.getXXX()怎么是空的！！！！！！！！！！！！！！！！！！！！！！！！1
        List<Object[]> mList = new ArrayList();
//        for (int i = 0; i < NewsConst.getCatalogCount(); i++) {
//            String url = NewsConst.getUrl(i);
//            String title = NewsConst.getTitle(i);
//            Object[] item = new Object[]{url, title};
//            mList.add(item);
//        }
        mList.add(new Object[]{"校园要闻", "http://news.ecust.edu.cn/news?important=1"});
        mList.add(new Object[]{"综合新闻", "http://news.ecust.edu.cn/news?category_id=7"});
        mList.add(new Object[]{"招生就业", "http://news.ecust.edu.cn/news?category_id=65"});
        mList.add(new Object[]{"合作交流", "http://news.ecust.edu.cn/news?category_id=38"});
        mList.add(new Object[]{"深度报道", "http://news.ecust.edu.cn/news?category_id=60"});
        mList.add(new Object[]{"图说华理", "http://news.ecust.edu.cn/news?category_id=68"});
        mList.add(new Object[]{"媒体华理", "http://news.ecust.edu.cn/news?category_id=21"});
        mList.add(new Object[]{"通知公告", "http://news.ecust.edu.cn/notifies"});
        mList.add(new Object[]{"学术讲座", "http://news.ecust.edu.cn/reports"});
        return mList;
    }

    @Before
    public void getHtmlSource() {
        System.out.println("1、开始请求地址 " + url);
        htmlResult = HttpUrlConnectionUtil.getString(url);
        System.out.println("2、网络数据返回成功，共 " + htmlResult.length() + " 字节");
    }

    @Test
    public void testApply() throws Exception {
        System.out.println("3、开始执行解析");

        final NewsPageParseResult result = NewsParser.getInstance().apply(htmlResult);
        System.out.println(result);
        System.out.println(String.format(Locale.CHINA, "%s - 第 %d/%d 页 - 当前页共 %d 条",
                result.getCatalogTitle(),
                result.getCurrentPosition(),
                result.getTailPosition(),
                result.getItems().size()));

        assertNotNull(result.getCatalogTitle());
        assertTrue(result.getItems().size() > 0);
        assertTrue(result.getTailPosition() > 0);
        assertTrue(result.getCurrentPosition() > 0);
        assertEquals(expect, result.getCatalogTitle());
    }
}