package com.ecust.ecusthelper;

import android.app.Application;
import android.content.Context;

import com.ecust.ecusthelper.util.logUtils.logUtil;

/**
 * Created on 2016/3/21
 *
 * @author chenjj2048
 */
public class EcustApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        logUtil.enable(BuildConfig.DEBUG);
    }
}
