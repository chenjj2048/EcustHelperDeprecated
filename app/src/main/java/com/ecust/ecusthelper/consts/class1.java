package com.ecust.ecusthelper.consts;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created on 2016/5/15
 *
 * @author chenjj2048
 */
public class class1 {
    private static final ArrayList<Pair<String,String>> mList = new ArrayList<>();

    static {
        mList.add(new Pair("111", "111"));
    }

    public static String get() {
        return mList.get(0).first;
    }
}
