package com.ecust.ecusthelper.data.base;

/**
 * Created on 2016/5/20
 *
 * @author chenjj2048
 */
public interface Callback<Result> {
    int REASON_DATA_STILL_VALID = 1;
    int REASON_NO_MORE_DATA = 2;
    int REASON_OTHERS = 3;

    /**
     * 网络不通等情况数据无法返回
     */
    void onDataNotAvailable(int reason);

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
}