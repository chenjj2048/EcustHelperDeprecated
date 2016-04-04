package com.ecust.ecusthelper.net.common;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
public abstract class HttpCallback {
    public abstract void onResponse(HttpRequest request, HttpResponse response);

    public abstract void onError(HttpRequest request, Exception e);
}
