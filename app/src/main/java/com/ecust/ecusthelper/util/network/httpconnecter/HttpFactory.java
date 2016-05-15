package com.ecust.ecusthelper.util.network.httpconnecter;

import com.ecust.ecusthelper.util.network.interfacer.IHttp;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
@Deprecated
public class HttpFactory {
    public static IHttp newUrlConnection() {
        return new URLConnectionStrategy();
    }
}
