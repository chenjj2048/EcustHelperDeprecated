package com.ecust.ecusthelper.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.base.BaseAppCompatActivity;
import com.ecust.ecusthelper.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by 彩笔怪盗基德 on 2016/3/22.
 * https://github.com/chenjj2048
 */
public class ContentFragment extends BaseFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseAppCompatActivity.setupToolbar(getActivity(), mToolbar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
