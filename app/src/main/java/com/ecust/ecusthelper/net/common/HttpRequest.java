package com.ecust.ecusthelper.net.common;

/**
 * Created by 彩笔怪盗基德 on 2016/3/16.
 * https://github.com/chenjj2048
 */
public class HttpRequest extends cn.trinea.android.common.entity.HttpRequest {
    //返回编码的encoding
    private String encoding;

    public HttpRequest(String url) {
        super(url);
        this.encoding = HttpConstant.DEFAULT_ENCODING;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
