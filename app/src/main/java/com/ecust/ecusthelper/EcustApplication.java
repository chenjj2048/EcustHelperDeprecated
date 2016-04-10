package com.ecust.ecusthelper;

import android.app.Application;
import android.content.Context;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.ecust.ecusthelper.util.logUtils.logUtil;
import com.squareup.leakcanary.LeakCanary;

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
        setupLogger();
        setup_LeakCanary();
        setup_Android_Bootstrap_UI();
    }

    private void setup_LeakCanary() {
        LeakCanary.install(this);
    }

    private void setupLogger() {
        logUtil.enable(BuildConfig.DEBUG);
    }

    private void setup_Android_Bootstrap_UI() {
        TypefaceProvider.registerDefaultIconSets();
    }
}
