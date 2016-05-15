package com.ecust.ecusthelper.util.network.httpurlconnection;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.annimon.stream.Objects;
import com.ecust.ecusthelper.util.ConvertUtil;
import com.ecust.ecusthelper.util.IOUtils;
import com.ecust.ecusthelper.util.network.constant.HttpConstant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2016/5/10
 *
 * @author chenjj2048
 */
public class HttpUrlConnectionUtil {
    private static final int MAX_TIMEOUT_SECONDS = 30;

    @NonNull
    @WorkerThread
    public static String getString(@NonNull String urlString) throws RuntimeException {
        return getString(urlString,
                HttpConstant.DEFAULT_READ_TIMEOUT, HttpConstant.DEFAULT_CONNECT_TIMEOUT);
    }

    @NonNull
    @WorkerThread
    public static String getString(@NonNull String urlString,
                                   int connectTimeoutMillis, int readTimeoutMillis) throws RuntimeException {
        return getString(urlString, HttpConstant.DEFAULT_ENCODING,
                connectTimeoutMillis, readTimeoutMillis);
    }

    @NonNull
    @WorkerThread
    public static String getString(@NonNull String urlString, @NonNull String encoding,
                                   int connectTimeoutMillis, int readTimeoutMillis) throws RuntimeException {
        Objects.requireNonNull(urlString);
        Objects.requireNonNull(encoding);
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            connectTimeoutMillis = checkRange(connectTimeoutMillis, HttpConstant.DEFAULT_CONNECT_TIMEOUT);
            readTimeoutMillis = checkRange(readTimeoutMillis, HttpConstant.DEFAULT_READ_TIMEOUT);

            conn.setConnectTimeout(connectTimeoutMillis);
            conn.setReadTimeout(readTimeoutMillis);

            inputStream = conn.getInputStream();
            return ConvertUtil.inputStream2String(inputStream, encoding);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * @param timeoutMillis 传入的时间
     * @param defaultTime   如果传入时间不正确，采用此数值
     */
    private static int checkRange(int timeoutMillis, int defaultTime) {
        if (timeoutMillis <= 0 || timeoutMillis > TimeUnit.SECONDS.toMillis(MAX_TIMEOUT_SECONDS))
            return defaultTime;
        else
            return timeoutMillis;
    }
}
