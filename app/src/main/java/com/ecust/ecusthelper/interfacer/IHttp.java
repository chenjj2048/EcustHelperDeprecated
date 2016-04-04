package com.ecust.ecusthelper.interfacer;

import android.support.annotation.WorkerThread;

import com.ecust.ecusthelper.net.common.HttpCallback;
import com.ecust.ecusthelper.net.common.HttpRequest;

/**
 * Created on 2016/3/15
 *
 * @author chenjj2048
 */
public interface IHttp {
    @WorkerThread
    void getSynchronousData(String url, HttpCallback callback);

    @WorkerThread
    void getSynchronousData(HttpRequest request, HttpCallback callback);
}
