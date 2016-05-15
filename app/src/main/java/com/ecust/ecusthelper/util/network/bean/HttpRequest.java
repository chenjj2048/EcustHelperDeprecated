package com.ecust.ecusthelper.util.network.bean;

import android.text.TextUtils;

import com.ecust.ecusthelper.util.network.constant.HttpConstant;

/**
 * Created on 2016/3/16
 *
 * @author chenjj2048
 */
public class HttpRequest extends cn.trinea.android.common.entity.HttpRequest {
    private HttpRequest(String url) {
        super(url);
        this.setConnectTimeout(HttpConstant.DEFAULT_CONNECT_TIMEOUT);
        this.setReadTimeout(HttpConstant.DEFAULT_READ_TIMEOUT);
    }

    public static HttpRequest ofUrl(String url) {
        if (TextUtils.isEmpty(url))
            throw new NullPointerException("URL地址不能为空");
        return new HttpRequest(url);
    }
}
