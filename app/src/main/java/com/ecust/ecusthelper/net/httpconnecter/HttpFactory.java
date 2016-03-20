package com.ecust.ecusthelper.net.httpconnecter;

import com.ecust.ecusthelper.interfacer.IHttp;

import java.util.WeakHashMap;

/**
 * Created by 彩笔怪盗基德 on 2016/3/16.
 * https://github.com/chenjj2048
 */
public class HttpFactory {
    private static final int HTTP_URL_CONNECTION = 1;
    private static final int VOLLEY = 2;
    private static final int OK_HTTP = 3;

    public static WeakHashMap<Integer, IHttp> mWeakHashMap;

    private HttpFactory() {
    }

    private static IHttp getHttpConnecter(int key) {
        if (mWeakHashMap == null)
            mWeakHashMap = new WeakHashMap<>();

        IHttp mConnecter = mWeakHashMap.get(key);
        if (mConnecter == null) {
            switch (key) {
                case HTTP_URL_CONNECTION:
                    mConnecter = new URLConnectionStrategy();
                    break;
                default:
                    throw new RuntimeException("没有这种类型");
            }
            mWeakHashMap.put(key, mConnecter);
        }
        return mConnecter;
    }

    public static IHttp newHttpUrlConnection() {
        return getHttpConnecter(HTTP_URL_CONNECTION);
    }
}
