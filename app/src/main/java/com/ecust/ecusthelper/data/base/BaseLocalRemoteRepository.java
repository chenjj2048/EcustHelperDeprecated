package com.ecust.ecusthelper.data.base;


import com.annimon.stream.Objects;

/**
 * Created on 2016/5/20
 *
 * @author chenjj2048
 */
public abstract class BaseLocalRemoteRepository<Request, Result,
        Local extends IRepository.ILocalRepository, Remote extends IRepository.IRemoteRepository>
        implements IRepository<Request, Result> {

    private Local mLocal;
    private Remote mRemote;

    protected abstract Remote createRemoteRepository();

    protected abstract Local createLocalRepository();

    protected Local getLocalRepository() {
        if (mLocal == null)
            mLocal = createLocalRepository();
        Objects.requireNonNull(mLocal, "请先创建本地仓库");
        return mLocal;
    }

    protected Remote getRemoteRepository() {
        if (mRemote == null)
            mRemote = createRemoteRepository();
        Objects.requireNonNull(mRemote, "请先创建远程仓库");
        return mRemote;
    }
}
