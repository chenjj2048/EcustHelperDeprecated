package com.ecust.ecusthelper.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 2016/6/18
 *
 * @author chenjj2048
 */
@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
