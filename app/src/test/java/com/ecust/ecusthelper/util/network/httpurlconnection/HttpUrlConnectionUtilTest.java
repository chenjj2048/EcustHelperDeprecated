package com.ecust.ecusthelper.util.network.httpurlconnection;

import android.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static junit.framework.Assert.assertTrue;

/**
 * Created on 2016/5/13
 *
 * @author chenjj2048
 */
@RunWith(Parameterized.class)
public class HttpUrlConnectionUtilTest {
    private static int i;
    String input;
    String expect;

    public HttpUrlConnectionUtilTest(String expect, String input) {
        this.expect = expect;
        this.input = input;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"百度一下，你就知道", "http://www.baidu.com"},
                {"校园要闻", "http://news.ecust.edu.cn/news?important=1"},
                {"【精彩新生季】华理，我来了！", "http://news.ecust.edu.cn/news/35392?important=&category_id=68"},
                {"华东理工大学", "http://news.ecust.edu.cn/news/32328"}
        });
    }

    @Test
    public void testGetString() throws Exception {
        System.out.println(String.format(Locale.CHINA, "【%d】 %s %s", ++i, expect, input));
        assertTrue(HttpUrlConnectionUtil.getString(input).contains(expect));
    }
}