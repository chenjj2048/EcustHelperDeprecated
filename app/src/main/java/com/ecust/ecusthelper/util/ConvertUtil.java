package com.ecust.ecusthelper.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by 彩笔怪盗基德 on 2016/3/17.
 * https://github.com/chenjj2048
 */
@SuppressWarnings("unused")
public class ConvertUtil {

    //Todo: sp px dp
    private static final int BUFFER_SIZE = 1024;

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
