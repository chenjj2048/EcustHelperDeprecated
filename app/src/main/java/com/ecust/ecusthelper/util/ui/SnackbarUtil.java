package com.ecust.ecusthelper.util.ui;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;

import com.annimon.stream.Objects;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2016/4/25
 *
 * @author chenjj2048
 */
//Todo:连续显示两次的话，时间好像有点不对
public class SnackbarUtil {
    private static final int DISMISS = 0;
    private static Handler mHandler = new Handler(Looper.getMainLooper(), (msg) -> {
        Snackbar snackbar = (Snackbar) msg.obj;
        snackbar.dismiss();
        snackbar = null;
        return true;
    });

    /**
     * 指定Snackbar显示时间
     *
     * @param snackbar snackbar
     * @param time     显示时间,ms
     */
    public static void show(Snackbar snackbar, int time) {
        Objects.requireNonNull(snackbar);
        if (time == Snackbar.LENGTH_SHORT || time == Snackbar.LENGTH_LONG) {
            snackbar.setDuration(time).show();
            return;
        }

        checkTimeRange(time);

        //延迟调用Snackbar.dismiss()
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE).show();
        Message msg = Message.obtain(mHandler, DISMISS, snackbar);
        mHandler.removeMessages(DISMISS);
        mHandler.sendMessageDelayed(msg, time);
    }

    private static void checkTimeRange(int time) {
        if (time == Snackbar.LENGTH_SHORT || time == Snackbar.LENGTH_LONG || time == Snackbar.LENGTH_INDEFINITE)
            return;
        if (time < 0) {
            throw new IllegalArgumentException("Snackbar显示时间非负");
        } else if (time > TimeUnit.SECONDS.toMillis(15)) {
            throw new IllegalArgumentException("Snackbar请不要设置过长时间");
        }
    }
}
