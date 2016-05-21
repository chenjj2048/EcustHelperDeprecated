package com.ecust.ecusthelper.data.base;

/**
 * Created on 2016/5/20
 *
 * @author chenjj2048
 */
public interface Callback<Result> {
    /**
     * 网络不通等情况数据无法返回
     */
    void onDataNotAvailable();

    /**
     * 数据正常获取
     *
     * @param result 数据
     */
    void onDataArrived(Result result);

    /**
     * 未能预料到的各种异常
     *
     * @param e Exception（不包含网络超时、链接不通等情况）
     */
    void onException(Exception e);

    /**
     * 到达了底部
     */
    default void onReachEnd() {
    }
}