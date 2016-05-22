package com.ecust.ecusthelper.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created on 2016/4/18
 *
 * @author chenjj2048
 */
public class IOUtils {
    private static final int BUFFER_SIZE = 1024;

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
