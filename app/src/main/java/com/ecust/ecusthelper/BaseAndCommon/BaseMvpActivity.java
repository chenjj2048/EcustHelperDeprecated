package com.ecust.ecusthelper.baseAndCommon;

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
    }

    @NonNull
    protected P getPresenter() {
        if (presenter == null) {
            setPresenter(createPresenter());
        }
        return Objects.requireNonNull(presenter);
    }

    protected void setPresenter(@NonNull P presenter) {
        this.presenter = Objects.requireNonNull(presenter);
    }

    @NonNull
    protected abstract P createPresenter();
}
