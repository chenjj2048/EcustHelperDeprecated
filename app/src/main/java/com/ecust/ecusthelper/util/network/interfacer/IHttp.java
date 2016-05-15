package com.ecust.ecusthelper.util.network.interfacer;

import android.support.annotation.WorkerThread;

import com.ecust.ecusthelper.util.network.bean.HttpCallback;
import com.ecust.ecusthelper.util.network.bean.HttpRequest;
import com.ecust.ecusthelper.util.network.bean.HttpResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created on 2016/3/15
 *
 * @author chenjj2048
 */
@Deprecated
public interface IHttp {
    /**
     * 异步方法
     * Http回调结束后，会自动关闭InputStream，无须担心
     */
    void getAsynchronousData(HttpRequest request, HttpCallback callback);

    /**
     * 同步方法
     * 用完请及时关闭HttpResponse的InputStream
     */
    //ToDo:关闭结果的流
    @Deprecated
    @WorkerThread
    HttpResponse getSynchronousData(HttpRequest request) throws IOException;

    void setExecutor(ExecutorService executor);
}
