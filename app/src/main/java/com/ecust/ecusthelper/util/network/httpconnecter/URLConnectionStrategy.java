package com.ecust.ecusthelper.util.network.httpconnecter;

import android.support.annotation.WorkerThread;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.util.network.bean.HttpRequest;
import com.ecust.ecusthelper.util.network.bean.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created on 2016/3/15
 *
 * @author chenjj2048
 */
@Deprecated
public  class URLConnectionStrategy extends AbstractHttp {
    URLConnectionStrategy() {
        super();
    }

    @Override
    @WorkerThread
    public HttpResponse getSynchronousData(HttpRequest request) throws IOException {
        Objects.requireNonNull(request);
        final URL url = toUrlFunction.apply(request);
        final int connectTimeout = request.getConnectTimeout();
        final int readTimeout = request.getReadTimeout();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (connectTimeout > 0)
            conn.setConnectTimeout(connectTimeout);
        if (readTimeout > 0)
            conn.setReadTimeout(readTimeout);

        return new HttpResponse(conn.getInputStream());
    }
}
