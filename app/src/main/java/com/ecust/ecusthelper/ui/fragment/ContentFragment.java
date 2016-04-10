package com.ecust.ecusthelper.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecust.ecusthelper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 彩笔怪盗基德 on 2016/3/22.
 * https://github.com/chenjj2048
 */
public class ContentFragment extends BaseFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_content, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        setupToolbar((AppCompatActivity) getActivity());
    }

    private void setupToolbar(AppCompatActivity activity) {
        activity.setSupportActionBar(mToolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
