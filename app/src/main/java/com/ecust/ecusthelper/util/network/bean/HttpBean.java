package com.ecust.ecusthelper.util.network.bean;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public class HttpBean {
    private HttpRequest request;
    private HttpResponse response;
    private HttpCallback callback;
    private Exception exception;

    public HttpCallback getCallback() {
        return callback;
    }

    public HttpBean setCallback(HttpCallback callback) {
        this.callback = callback;
        return this;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpBean setRequest(HttpRequest request) {
        this.request = request;
        return this;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public HttpBean setResponse(HttpResponse response) {
        this.response = response;
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public HttpBean setException(Exception exception) {
        this.exception = exception;
        return this;
    }
}
