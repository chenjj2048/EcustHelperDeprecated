package com.ecust.ecusthelper.di.compent;

import com.ecust.ecusthelper.data.NewsRepository;
import com.ecust.ecusthelper.di.module.NewsRepositoryModule;
import com.ecust.ecusthelper.di.scope.PerFragment;

import dagger.Component;

/**
 * Created on 2016/6/18
 *
 * @author chenjj2048
 */
@PerFragment
@Component(modules = NewsRepositoryModule.class)
public interface NewsRepositoryCompent {
    void inject(NewsRepository newsRepository);
}
