package com.ecust.ecusthelper.util.network.bean;

import android.support.annotation.UiThread;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
@UiThread
public abstract class HttpCallback {
    public abstract void onResponse(HttpRequest request, HttpResponse response);

    public abstract void onError(HttpRequest request, Exception e);
}
