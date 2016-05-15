package com.ecust.ecusthelper.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;

/**
 * Created on 2016/5/7
 *
 * @author chenjj2048
 */
public abstract class BaseMvpActivity<P> extends BaseAppCompatActivity {
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
