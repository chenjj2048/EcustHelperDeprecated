package com.ecust.ecusthelper.util;

import android.test.AndroidTestCase;

import com.ecust.ecusthelper.net.common.HttpConstant;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by 彩笔怪盗基德 on 2016/3/17.
 * https://github.com/chenjj2048
 */
public class ConvertUtilTest extends AndroidTestCase {

    public void test_String_InputStream_Convert() throws Exception {
        final String encoding = HttpConstant.DEFAULT_ENCODING;

        String from = Calendar.getInstance().toString();
        InputStream inputStream = ConvertUtil.string2InputStream(from, encoding);
        String target = ConvertUtil.inputStream2String(inputStream, encoding);

        assertEquals(from, target);
    }

    public void test_Bytes_InputStream_Convert() throws Exception {
        Random random = new Random();
        byte[] from = new byte[random.nextInt(10000) + 1];
        for (int i = 0; i < from.length; i++)
            from[i] = (byte) random.nextInt(255);

        InputStream inputStream = ConvertUtil.bytes2InputStream(from);
        byte[] target = ConvertUtil.inputStream2bytes(inputStream);

        assertEquals(from.length, target.length);
        for (int i = 0; i < from.length; i++)
            assertEquals(from[i], target[i]);
    }
}
