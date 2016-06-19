package com.ecust.ecusthelper.di.compent;

import com.ecust.ecusthelper.di.module.NewsPresenterModule;
import com.ecust.ecusthelper.di.scope.PerFragment;
import com.ecust.ecusthelper.ui.news.NewsPresenter;

import dagger.Component;

/**
 * Created on 2016/6/18
 *
 * @author chenjj2048
 */
@PerFragment
@Component(modules = NewsPresenterModule.class)
public interface NewsPresenterCompent {
    void inject(NewsPresenter newsPresenter);
}
