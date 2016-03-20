package com.ecust.ecusthelper.net.httpconnecter;

import com.ecust.ecusthelper.net.common.HttpCallback;
import com.ecust.ecusthelper.net.common.HttpRequest;
import com.ecust.ecusthelper.net.common.HttpResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 彩笔怪盗基德 on 2016/3/15.
 * https://github.com/chenjj2048
 */
public class URLConnectionStrategy extends AbstractHttpConnecter {
    protected URLConnectionStrategy() {
    }

    @Override
    public void getSynchronousData(HttpRequest request, HttpCallback callback) {
        if (super.isRequestOrCallbackEmpty(request, callback)) return;

        //访问网络
        InputStream inputStream = null;
        try {
            final URL url = new URL(request.getUrl());
            final int connectTimeout = request.getConnectTimeout();
            final int readTimeout = request.getReadTimeout();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (connectTimeout > 0)
                conn.setConnectTimeout(connectTimeout);
            if (readTimeout > 0)
                conn.setReadTimeout(readTimeout);

            //读取数据
            inputStream = conn.getInputStream();
            HttpResponse response = new HttpResponse(inputStream);
            //回调
            callback.onResponse(request, response);
        } catch (Exception e) {
            callback.onError(request, e);
        } finally {
            closeQuietly(inputStream);
        }
    }
}
