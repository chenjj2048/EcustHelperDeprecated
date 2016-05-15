package com.ecust.ecusthelper;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.ecust.ecusthelper.util.log.logUtil;
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
        setupUncaughtException();
        setupLeakCanary();
        setupAndroid_Bootstrap_UI();
        setupStrictMode();
    }

    private void setupStrictMode() {
        if (!BuildConfig.DEBUG) return;
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectCustomSlowCalls()    //StrictMode.noteSlowCall();
                .detectNetwork()   // or .detectAll() for all detectable problems
                .detectAll()
                .penaltyLog()
                .penaltyDialog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private void setupUncaughtException() {
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashException());
    }

    private void setupLeakCanary() {
        LeakCanary.install(this);
    }

    private void setupLogger() {
        logUtil.enable(BuildConfig.DEBUG);
    }

    private void setupAndroid_Bootstrap_UI() {
        TypefaceProvider.registerDefaultIconSets();
    }
}
