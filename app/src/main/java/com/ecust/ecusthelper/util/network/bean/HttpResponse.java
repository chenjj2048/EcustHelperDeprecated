package com.ecust.ecusthelper.util.network.bean;

import android.support.annotation.Nullable;

import com.ecust.ecusthelper.util.IOUtils;
import com.ecust.ecusthelper.util.ConvertUtil;
import com.ecust.ecusthelper.util.network.constant.HttpConstant;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
public class HttpResponse extends cn.trinea.android.common.entity.HttpResponse implements Closeable {
    private InputStream inputStream;
    private boolean readed = true;

    public HttpResponse(InputStream inputStream) {
        this.inputStream = inputStream;
        this.readed = false;
    }

    @Nullable
    public InputStream getUnreadInputStream() {
        return inputStream;
    }

    /**
     * =====================
     * 以下几个函数请仅用一次
     * 否则会产生错误
     * =====================
     */

    public InputStream getInputStream() {
        if (readed)
            throw new RuntimeException("请不要对此类重复读取数据");
        else {
            readed = true;
            return inputStream;
        }
    }

    @Override
    @Nullable
    public String getResponseBody() {
        if (readed)
            throw new RuntimeException("请不要对此类重复读取数据");
        else
            try {
                readed = true;
                return ConvertUtil.inputStream2String(inputStream, HttpConstant.DEFAULT_ENCODING);
            } catch (Exception e) {
                return null;
            }
    }

    @Nullable
    public byte[] getBytes() {
        if (readed)
            throw new RuntimeException("请不要对此类重复读取数据");
        else
            try {
                readed = true;
                return ConvertUtil.inputStream2bytes(inputStream);
            } catch (Exception e) {
                return null;
            }
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(inputStream);
    }
}
