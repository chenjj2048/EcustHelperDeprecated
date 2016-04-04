package com.ecust.ecusthelper.net.httpconnecter;

import com.ecust.ecusthelper.interfacer.IHttp;
import com.ecust.ecusthelper.net.common.HttpCallback;
import com.ecust.ecusthelper.net.common.HttpConstant;
import com.ecust.ecusthelper.net.common.HttpRequest;

import java.io.Closeable;

import static com.ecust.ecusthelper.net.common.HttpConstant.DEFAULT_ENCODING;

/**
 * Created on 2016/3/31
 *
 * @author chenjj2048
 */
public abstract class AbstractHttp implements IHttp {
    HttpRequest url2HttpRequest(String url) {
        HttpRequest request = new HttpRequest(url);
        request.setConnectTimeout(HttpConstant.DEFAULT_CONNECT_TIMEOUT);
        request.setReadTimeout(HttpConstant.DEFAULT_READ_TIMEOUT);
        request.setEncoding(DEFAULT_ENCODING);
        return request;
    }

    void requireNonNull(HttpRequest request, HttpCallback callback) throws NullPointerException {
        if (callback == null)
            throw new NullPointerException("没有回调函数");
        if (request == null)
            callback.onError(null, new NullPointerException("网络请求为空"));
    }

    void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (Exception e) {
            //Nothing
        }
    }

    @Override
    public void getSynchronousData(String url, HttpCallback callback) {
        getSynchronousData(url2HttpRequest(url), callback);
    }
}
