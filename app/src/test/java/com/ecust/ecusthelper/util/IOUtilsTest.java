package com.ecust.ecusthelper.util;

import com.ecust.ecusthelper.util.network.HttpConstant;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created on 2016/5/21
 *
 * @author chenjj2048
 */
public class IOUtilsTest {
    static int TEST_RANGE;
    static Random random;

    @BeforeClass
    public static void beforeClass() {
        random = new Random();
        TEST_RANGE = random.nextInt(10000);
    }

    @Test
    public void test_String_InputStream_Convert() throws Exception {
        final String encoding = HttpConstant.DEFAULT_ENCODING;

        String from = Calendar.getInstance().toString();

        InputStream inputStream = IOUtils.string2InputStream(from, encoding);
        String target = IOUtils.inputStream2String(inputStream, encoding);

        assertEquals(from, target);
    }

    @Test
    public void test_Bytes_InputStream_Convert() throws Exception {
        byte[] from = new byte[TEST_RANGE <= 0 ? 1 : TEST_RANGE];
        for (int i = 0; i < from.length; i++)
            from[i] = (byte) random.nextInt(255);

        InputStream inputStream = IOUtils.bytes2InputStream(from);
        byte[] target = IOUtils.inputStream2bytes(inputStream);

        assertArrayEquals(from, target);
    }
}