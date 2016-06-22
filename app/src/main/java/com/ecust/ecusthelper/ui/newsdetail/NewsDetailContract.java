package com.ecust.ecusthelper.ui.newsdetail;

import android.content.Context;

import com.ecust.ecusthelper.adapter.NewsDetailAdapter;

/**
 * Created on 2016/6/20
 *
 * @author chenjj2048
 */
public interface NewsDetailContract {
    interface IView {
        Context getContext();
    }

    interface Presenter {
        void fetchData();

        NewsDetailAdapter getAdapter();


    }
}
