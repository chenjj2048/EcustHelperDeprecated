package com.ecust.ecusthelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ecust.ecusthelper.consts.NewsFragmentTitleConst;
import com.ecust.ecusthelper.ui.news.NewsFragment;
import com.ecust.ecusthelper.util.log.logUtil;

/**
 * Created on 2016/4/24
 *
 * @author chenjj2048
 */
//Todo:以后与FragmentPagerAdapter比较下，看内存占用大小有多少区别
public class NewsViewPagerAdapter extends FragmentStatePagerAdapter {
    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NewsFragmentTitleConst.getTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        NewsFragment mFragment = NewsFragment.newInstance(position);
        logUtil.v(this, mFragment.toString());
        return mFragment;
    }

    @Override
    public int getCount() {
        return NewsFragmentTitleConst.size();
    }
}
