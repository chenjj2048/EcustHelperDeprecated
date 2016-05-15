package com.ecust.ecusthelper.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;

/**
 * Created on 2016/5/10
 *
 * @author chenjj2048
 */
public abstract class BaseMvpFragment<P> extends BaseFragment {
    private P presenter;

    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setPresenter(createPresenter());
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setPresenter(createPresenter());
    }

    @NonNull
    protected P getPresenter() {
        return Objects.requireNonNull(presenter);
    }

    protected void setPresenter(@NonNull P presenter) {
        this.presenter = Objects.requireNonNull(presenter);
    }

    @NonNull
    protected abstract P createPresenter();
}
