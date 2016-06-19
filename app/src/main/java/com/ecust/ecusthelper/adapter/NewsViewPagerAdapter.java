package com.ecust.ecusthelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;

import com.ecust.ecusthelper.consts.NewsConst;
import com.ecust.ecusthelper.ui.news.NewsFragment;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
public final class NewsViewPagerAdapter extends FragmentStatePagerAdapter {
    private final FragmentLruCache mCache;

    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mCache = new FragmentLruCache(3);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NewsConst.getTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mCache.get(position);
    }

    @Override
    public int getCount() {
        return NewsConst.getCatalogCount();
    }

    class FragmentLruCache extends LruCache<Integer, NewsFragment> {
        public FragmentLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected NewsFragment create(Integer key) {
            return NewsFragment.newInstance(key);
        }
    }
}
