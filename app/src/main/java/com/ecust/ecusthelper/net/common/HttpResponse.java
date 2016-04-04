package com.ecust.ecusthelper.net.common;

import com.ecust.ecusthelper.util.ConvertUtil;

import java.io.InputStream;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
public class HttpResponse extends cn.trinea.android.common.entity.HttpResponse {
    private InputStream inputStream;
    private boolean readed = true;

    public HttpResponse(InputStream inputStream) {
        this.inputStream = inputStream;
        this.readed = false;
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
}
