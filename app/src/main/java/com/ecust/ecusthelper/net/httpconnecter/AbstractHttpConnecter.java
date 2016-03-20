package com.ecust.ecusthelper.net.httpconnecter;

import com.ecust.ecusthelper.interfacer.IHttp;
import com.ecust.ecusthelper.net.common.HttpCallback;
import com.ecust.ecusthelper.net.common.HttpConstant;
import com.ecust.ecusthelper.net.common.HttpRequest;

import java.io.Closeable;

import static com.ecust.ecusthelper.net.common.HttpConstant.DEFAULT_ENCODING;

/**
 * Created by 彩笔怪盗基德 on 2016/3/15.
 * https://github.com/chenjj2048
 */
public abstract class AbstractHttpConnecter implements IHttp {
    protected HttpRequest url2HttpRequest(String url) {
        HttpRequest request = new HttpRequest(url);
        request.setConnectTimeout(HttpConstant.DEFAULT_CONNECT_TIMEOUT);
        request.setReadTimeout(HttpConstant.DEFAULT_READ_TIMEOUT);
        request.setEncoding(DEFAULT_ENCODING);
        return request;
    }

    //@SuppressWarnings({"StatementWithEmptyBody", "all"})
    protected void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (Exception e) {
            //Nothing
        }
    }

    protected boolean isRequestOrCallbackEmpty(HttpRequest request, HttpCallback callback) {
        if (callback == null) {
            return true;
        } else if (request == null) {
            callback.onError(null, new NullPointerException("网络请求为空"));
            return true;
        } else
            return false;
    }

    @Override
    public void getSynchronousData(String url, HttpCallback callback) {
        getSynchronousData(url2HttpRequest(url), callback);
    }
}
