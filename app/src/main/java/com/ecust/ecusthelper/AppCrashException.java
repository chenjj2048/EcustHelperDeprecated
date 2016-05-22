package com.ecust.ecusthelper;

import com.ecust.ecusthelper.util.log.logUtil;

/**
 * Created on 2016/5/7
 *
 * @author chenjj2048
 */
public class AppCrashException implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        showCrashInformation(thread, ex);
        saveCrashInformation(thread, ex);
    }

    private void showCrashInformation(Thread thread, Throwable ex) {
        String msg = String.format("Throwable on Thread %s - %s", thread.getName(), ex.toString());
        logUtil.e(this, "【uncaughtException】" + msg);
        ex.printStackTrace();
//        System.exit(0);
    }

    private void saveCrashInformation(Thread thread, Throwable ex) {
        //Nothing
    }
}
