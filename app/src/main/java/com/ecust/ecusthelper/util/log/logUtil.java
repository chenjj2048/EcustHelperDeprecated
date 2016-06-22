package com.ecust.ecusthelper.util.log;


import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ecust.ecusthelper.BuildConfig;
import com.ecust.ecusthelper.baseAndCommon.BaseAppCompatActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created on 2016/3/21
 *
 * @author chenjj2048
 */
@SuppressWarnings("all")
public final class logUtil {
    private static boolean mGlobalEnable = true;
    private static InnerLog mLog = new InnerLog(true);

    public static void enable(boolean global_enable) {
        logUtil.mGlobalEnable = global_enable;
    }

    public static void usingSystemOutPrintlnInstead(boolean b) {
        mLog = new InnerLog(b);
    }

    private static boolean checkValid(String str) {
        return mGlobalEnable && !TextUtils.isEmpty(str);
    }

    @NonNull
    private static String getTagNameFromObject(Object obj) {
        Class clazz;
        String result;
        if (obj instanceof Class)
            clazz = (Class) obj;
        else
            clazz = obj.getClass();
        result = clazz.getSimpleName();

        if (TextUtils.isEmpty(result))
            result = "TAG";
        return getTagPreffix() + result;
    }

    /**
     * 加前缀，用于AS里搜索过滤掉系统日志
     */
    private static String getTagPreffix() {
        return "MyLog-";
    }

    public static void v(String tag, String msg) {
        if (checkValid(tag) && checkValid(msg))
            mLog.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (checkValid(tag) && checkValid(msg))
            mLog.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (checkValid(tag) && checkValid(msg))
            mLog.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (checkValid(tag) && checkValid(msg))
            mLog.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (checkValid(tag) && checkValid(msg))
            mLog.e(tag, msg);
    }
//todo
    public static void dialog(String str) {
        if (!mGlobalEnable || !BuildConfig.DEBUG) return;
        final Context context = BaseAppCompatActivity.getCurrentActivity();
        if (context != null) {
            Observable.just(null)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                            mBuilder.setTitle("日志")
                                    .setMessage(str)
                                    .create()
                                    .show();
                        }
                    });

        }
    }

    public static void v(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        v(TAG, msg);
    }

    public static void d(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        d(TAG, msg);
    }

    public static void i(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        i(TAG, msg);
    }

    public static void w(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        w(TAG, msg);
    }

    public static void e(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        e(TAG, msg);
    }

    /**
     * 提供Log.x输出信息
     * 或者System.out.println输出信息
     */
    static class InnerLog {
        /**
         * 使用System.out.println代替
         */
        private static boolean usingSystemOutPrintln;

        public InnerLog(boolean usingSystemOutPrintlnStead) {
            InnerLog.usingSystemOutPrintln = usingSystemOutPrintlnStead;
        }

        public static void v(String tag, String msg) {
            if (usingSystemOutPrintln)
                System.out.println(msg);
            else
                Log.v(tag, msg);
        }

        public static void d(String tag, String msg) {
            if (usingSystemOutPrintln)
                System.out.println(msg);
            else
                Log.d(tag, msg);
        }

        public static void i(String tag, String msg) {
            if (usingSystemOutPrintln)
                System.out.println(msg);
            else
                Log.i(tag, msg);
        }

        public static void w(String tag, String msg) {
            if (usingSystemOutPrintln)
                System.out.println(msg);
            else
                Log.w(tag, msg);
        }

        public static void e(String tag, String msg) {
            if (usingSystemOutPrintln)
                System.out.println(msg);
            else
                Log.e(tag, msg);
        }
    }
}


