package com.ecust.ecusthelper.di.module;

import android.content.Context;

import com.ecust.ecusthelper.adapter.NewsItemAdapter;
import com.ecust.ecusthelper.bean.news.NewsItem;
import com.ecust.ecusthelper.data.NewsRepository;
import com.ecust.ecusthelper.di.scope.PerFragment;
import com.ecust.ecusthelper.ui.news.NewsContract;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 2016/6/18
 *
 * @author chenjj2048
 */
@PerFragment
@Module
public class NewsPresenterModule {
    private final NewsContract.IView view;
    private NewsRepository mNewsRepository;

    public NewsPresenterModule(NewsContract.IView view) {
        this.view = view;
    }

    @PerFragment
    @Provides
    public NewsContract.IView provideView() {
        return view;
    }

    @PerFragment
    @Provides
    public NewsRepository provideNewsRepository() {
        if (mNewsRepository == null) {
            int index = view.getFragmentIndex();
            mNewsRepository = new NewsRepository(index);
        }
        return mNewsRepository;
    }

    @PerFragment
    @Provides
    public NewsItemAdapter provideNewsItemAdapter() {
        final Context context = view.getContext();
        final List<NewsItem> list = provideNewsRepository().getList();
        return new NewsItemAdapter(context, list);
    }
}
