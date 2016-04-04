package com.ecust.ecusthelper.util.design.pattern;

/**
 * Created on 2016/4/4
 *
 * @author chenjj2048
 */
@SuppressWarnings("unused")
public abstract class ResponsibleOfChain<T> {
    private ResponsibleOfChain<T> successor;

    public ResponsibleOfChain<T> getSuccessor() {
        return successor;
    }

    /**
     * @param successor 后继处理内容
     * @return 传入的参数
     */
    public ResponsibleOfChain<T> setSuccessor(ResponsibleOfChain<T> successor) {
        this.successor = successor;
        return successor;
    }

    protected abstract boolean handlerItem(T data);

    public boolean handlerRequest(T data) {
        boolean hasHandlered = handlerItem(data);
        return hasHandlered || ((successor != null) && successor.handlerRequest(data));
    }
}
