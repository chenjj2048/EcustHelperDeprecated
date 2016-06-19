package com.ecust.ecusthelper.di.module;

import com.ecust.ecusthelper.data.local.NewsLocalRepository;
import com.ecust.ecusthelper.data.remote.NewsRemoteRepository;
import com.ecust.ecusthelper.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 2016/6/18
 *
 * @author chenjj2048
 */
@PerFragment
@Module
public class NewsRepositoryModule {
    private final int index;

    public NewsRepositoryModule(int index) {
        this.index = index;
    }

    @PerFragment
    @Provides
    public int provideFragmentIndex() {
        return index;
    }

    @PerFragment
    @Provides
    public NewsLocalRepository provideNewsLocalRepository() {
        return new NewsLocalRepository();
    }

    @PerFragment
    @Provides
    public NewsRemoteRepository provideNewsRemoteRepository() {
        return new NewsRemoteRepository(index);
    }
}
