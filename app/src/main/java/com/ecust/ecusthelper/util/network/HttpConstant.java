package com.ecust.ecusthelper.util.network;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2016/3/15
 *
 * @author chenjj2048
 */
public final class HttpConstant {
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_GBK = "GBK";
    public static final String ENCODING_GB2312 = "gb2312";
    public static final int DEFAULT_CONNECT_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(5);
    public static final int DEFAULT_READ_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);
    public static final String DEFAULT_ENCODING = ENCODING_UTF8;
}
