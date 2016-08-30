package com.ecust.ecusthelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;

import com.ecust.ecusthelper.consts.NewsTitleAndUrlConst;
import com.ecust.ecusthelper.ui.news.NewsFragment;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public final class NewsViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int MAX_FRAGMENT_LIMIT = 3;
    private final LruCache<Integer, NewsFragment> mCache =
            new LruCache<Integer, NewsFragment>(MAX_FRAGMENT_LIMIT) {
                @Override
                protected NewsFragment create(Integer key) {
                    return NewsFragment.newInstance(key);
                }
            };

    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NewsTitleAndUrlConst.getTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mCache.get(position);
    }

    @Override
    public int getCount() {
        return NewsTitleAndUrlConst.getCatalogCount();
    }
}
