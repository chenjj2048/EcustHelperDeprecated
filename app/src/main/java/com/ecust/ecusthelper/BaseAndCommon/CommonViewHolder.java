package com.ecust.ecusthelper.baseAndCommon;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on 2016/6/18
 *
 * @author chenjj2048
 */
public class CommonViewHolder {
    private final SparseArray<View> mViews;
    private final View mConvertView;
    private int mPosition;

    public CommonViewHolder(Context context, ViewGroup parent, int layoutID, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        mConvertView.setTag(this);
    }

    public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutID, int position) {
        if (convertView == null) {
            return new CommonViewHolder(context, parent, layoutID, position);
        } else {
            CommonViewHolder viewHolder = (CommonViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
            return viewHolder;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewID) {
        View view = mViews.get(viewID);
        if (view == null) {
            view = mConvertView.findViewById(viewID);
            mViews.put(viewID, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
