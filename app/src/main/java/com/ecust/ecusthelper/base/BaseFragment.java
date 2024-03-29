package com.ecust.ecusthelper.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.view.View;

import com.annimon.stream.Objects;

import butterknife.ButterKnife;

/**
 * Created on 2016/4/5
 *
 * @author chenjj2048
 */
public abstract class BaseFragment extends Fragment {
    private final Handler handler = new Handler();
    private Context context;

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        Objects.requireNonNull(context);
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    @CallSuper
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Objects.requireNonNull(activity);
        this.context = activity;
    }

    @Override
    public Context getContext() {
        return Objects.requireNonNull(context, "请在onAttach后使用");
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        handler.removeMessages(0);
    }

    protected void executeUntilFragmentAttached(Runnable runnable) {
        handler.postDelayed(() -> {
            if (context != null) {
                runnable.run();
            } else {
                executeUntilFragmentAttached(runnable);
            }
        }, 50);
    }
}
