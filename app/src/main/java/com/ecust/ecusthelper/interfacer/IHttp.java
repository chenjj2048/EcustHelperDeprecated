package com.ecust.ecusthelper.interfacer;

import android.support.annotation.WorkerThread;

import com.ecust.ecusthelper.net.common.HttpCallback;
import com.ecust.ecusthelper.net.common.HttpRequest;

/**
 * Created by 彩笔怪盗基德 on 2016/3/15.
 * https://github.com/chenjj2048
 */
public interface IHttp {
    @WorkerThread
    void getSynchronousData(String url, HttpCallback callback);

    @WorkerThread
    void getSynchronousData(HttpRequest request, HttpCallback callback);
}
