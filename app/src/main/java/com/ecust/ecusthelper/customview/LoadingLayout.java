package com.ecust.ecusthelper.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.ecust.ecusthelper.R;

/**
 * Created on 2016/5/29
 *
 * @author chenjj2048
 */
@Deprecated
public class LoadingLayout extends RelativeLayout {
    /**
     * 状态类型（顺序不能乱，对应子布局）
     */
    public static final int LOADING = 0;
    public static final int ERROR = 1;
    public static final int EMPTY = 2;
    public static final int CONTENT = 3;

    /**
     * 默认布局资源
     */
    private final int DEFAULT_LOADING_RES = R.layout.loading;
    private final int DEFAULT_ERROR_RES = R.layout.loading_failure;
    private final int DEFAULT_EMPTY_RES = R.layout.loading_empty;

    /**
     * 当前状态
     */
    private int mStatus;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        try {
            final int loadingViewRes = a.getResourceId(R.styleable.LoadingLayout_loadingView, DEFAULT_LOADING_RES);
            final int errorViewRes = a.getResourceId(R.styleable.LoadingLayout_errorView, DEFAULT_ERROR_RES);
            final int emptyViewRes = a.getResourceId(R.styleable.LoadingLayout_emptyView, DEFAULT_EMPTY_RES);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(loadingViewRes, this, true);
            inflater.inflate(errorViewRes, this, true);
            inflater.inflate(emptyViewRes, this, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        /**
         * 总共4个子布局
         */
        if (this.getChildCount() != 4)
            throw new IllegalStateException("布局下有且仅有一个子布局!");

        /**
         * 默认显示空界面
         */
        showEmptyView();
    }

    public void showLoadingView() {
        switchStatus(LOADING);
    }

    public void showErrorView() {
        switchStatus(ERROR);
    }

    public void showEmptyView() {
        switchStatus(EMPTY);
    }

    public void showContentView() {
        switchStatus(CONTENT);
    }

    /**
     * 切换布局状态
     *
     * @param which 布局类型
     */
    private void switchStatus(int which) {
        if (which < 0 || which > 4)
            throw new IllegalArgumentException();

        for (int i = 0; i < this.getChildCount(); i++) {
            this.getChildAt(i).setVisibility(GONE);
        }
        this.getChildAt(which).setVisibility(VISIBLE);
        mStatus = which;
    }

    public int getCurrentState() {
        return mStatus;
    }
}
