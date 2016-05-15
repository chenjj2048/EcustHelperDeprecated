package com.ecust.ecusthelper.util.network.httpconnecter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.annimon.stream.Objects;
import com.annimon.stream.function.Function;
import com.ecust.ecusthelper.util.IOUtils;
import com.ecust.ecusthelper.util.network.bean.HttpBean;
import com.ecust.ecusthelper.util.network.bean.HttpCallback;
import com.ecust.ecusthelper.util.network.bean.HttpRequest;
import com.ecust.ecusthelper.util.network.bean.HttpResponse;
import com.ecust.ecusthelper.util.network.interfacer.IHttp;

import java.io.InputStream;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2016/3/31
 *
 * @author chenjj2048
 */
@Deprecated
public abstract class AbstractHttp implements IHttp {
    protected static Function<HttpRequest, URL> toUrlFunction = (HttpRequest request) -> {
        try {
            return new URL(request.getUrl());
        } catch (Exception e) {
            return null;
        }
    };
    private static UIHandler mHandlerOnUI = new UIHandler();

    //设置默认的执行线程
    private ExecutorService mExecutor = Executors.newCachedThreadPool();

    @Override
    public void setExecutor(ExecutorService executor) {
        Objects.requireNonNull(executor);
        this.mExecutor = executor;
    }

    @Override
    @SuppressWarnings("all")
    public void getAsynchronousData(HttpRequest request, HttpCallback callback) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(callback);
        mExecutor.execute(() -> {
            final HttpBean bean = new HttpBean()
                    .setRequest(request)
                    .setCallback(callback);
            final Message msg = Message.obtain(mHandlerOnUI);
            try {
                //成功获取数据
                msg.what = UIHandler.HANDLER_SUCCESS;
                msg.obj = bean.setResponse(getSynchronousData(request));
            } catch (Exception e) {
                //数据获取失败
                msg.what = UIHandler.HANDLER_FAIL;
                msg.obj = bean.setException(e);
            }
            //送至主线程
            msg.sendToTarget();
        });
    }

    /**
     * 用于数据在线程间切换
     */
    static class UIHandler extends Handler {
        public static final int HANDLER_SUCCESS = 0x0a00;
        public static final int HANDLER_FAIL = 0x0a01;

        public UIHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            final HttpBean bean = (HttpBean) msg.obj;
            final HttpCallback callback = Objects.requireNonNull(bean.getCallback(), "回调函数为空");
            final HttpRequest request = Objects.requireNonNull(bean.getRequest(), "请求为空");
            final HttpResponse response = Objects.requireNonNull(bean.getResponse(), "返回结果为空");

            switch (msg.what) {
                case HANDLER_SUCCESS:
                    InputStream inputStream = response.getUnreadInputStream();
                    callback.onResponse(request, response);
                    IOUtils.closeQuietly(inputStream);
                    break;
                case HANDLER_FAIL:
                    callback.onError(request, bean.getException());
                    break;
                default:
                    throw new NoSuchElementException();
            }
        }
    }
}
