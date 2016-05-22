package com.ecust.ecusthelper.util;

import android.content.Context;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
@SuppressWarnings("unused")
public class SizeUtil {
    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public static int px2dp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public static int sp2px(Context context, int sp) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaleDensity + 0.5);
    }

    public static int px2sp(Context context, int px) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaleDensity + 0.5);
    }

    public static int sp2dp(Context context, int sp) {
        return px2dp(context, sp2px(context, sp));
    }

    public static int dp2sp(Context context, int dp) {
        return px2sp(context, dp2px(context, dp));
    }
}
