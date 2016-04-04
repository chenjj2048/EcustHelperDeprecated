package com.ecust.ecusthelper.util;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
@SuppressWarnings("unused")
public class ConvertUtil {
    private static final int BUFFER_SIZE = 1024;

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

    public static byte[] inputStream2bytes(InputStream inputStream) throws IOException {
        int len;
        byte[] result;
        byte[] buffer = new byte[BUFFER_SIZE];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
        result = baos.toByteArray();

        inputStream.close();
        baos.close();

        return result;
    }

    public static InputStream bytes2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static String inputStream2String(InputStream inputStream, String encoding)
            throws IOException {
        String result;
        int len;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((len = inputStream.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
        result = baos.toString(encoding);

        inputStream.close();
        baos.close();

        return result;
    }

    public static InputStream string2InputStream(String string, String encoding)
            throws UnsupportedEncodingException {
        return new ByteArrayInputStream(string.getBytes(encoding));
    }
}
