package com.ecust.ecusthelper.util;

import java.io.Closeable;

/**
 * Created on 2016/4/18
 *
 * @author chenjj2048
 */
public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
