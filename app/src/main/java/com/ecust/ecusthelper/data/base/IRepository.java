package com.ecust.ecusthelper.data.base;

import android.support.annotation.NonNull;

/**
 * Created on 2016/5/20
 *
 * @author chenjj2048
 */
public interface IRepository<Request, Result> {
    @NonNull
    String getRepositoryName();

    default void getData(Request request, Callback<Result> callback) {
        throw new UnsupportedOperationException("Override It");
    }

    default boolean isDataExpired() {
        return true;
    }

    interface ILocalRepository<Request, Result> extends IRepository<Request, Result> {
    }

    interface IRemoteRepository<Request, Result> extends IRepository<Request, Result> {
    }
}
