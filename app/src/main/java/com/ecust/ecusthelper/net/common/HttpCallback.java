package com.ecust.ecusthelper.net.common;

/**
 * Created by 彩笔怪盗基德 on 2016/3/17.
 * https://github.com/chenjj2048
 */
public abstract class HttpCallback {
    public abstract void onResponse(HttpRequest request, HttpResponse response);

    public abstract void onError(HttpRequest request, Exception e);
}
