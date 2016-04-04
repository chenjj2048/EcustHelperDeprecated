package com.ecust.ecusthelper.util;

import android.test.AndroidTestCase;

import com.ecust.ecusthelper.net.common.HttpConstant;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
public class ConvertUtilTest extends AndroidTestCase {
    //px、sp、dp反复转换后引起的最大误差
    private final int ALLOWED_MAX_DIFF = 1;
    private int TEST_RANGE;
    private Random random;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.random = new Random();
        this.TEST_RANGE = random.nextInt(10000);
    }

    public void test_px_dp_Convert() {
        for (int px1 = 0; px1 <= TEST_RANGE; px1++) {
            int dp = ConvertUtil.px2dp(getContext(), px1);
            int px2 = ConvertUtil.dp2px(getContext(), dp);
            assertTrue(Math.abs(px1 - px2) <= ALLOWED_MAX_DIFF);
        }
    }

    public void test_px_sp_Convert() {
        for (int px1 = 0; px1 <= TEST_RANGE; px1++) {
            int sp = ConvertUtil.px2sp(getContext(), px1);
            int px2 = ConvertUtil.dp2px(getContext(), sp);
            assertTrue(Math.abs(px1 - px2) <= ALLOWED_MAX_DIFF);
        }
    }

    public void test_dp_sp_Convert() {
        for (int dp1 = 0; dp1 <= TEST_RANGE; dp1++) {
            int sp = ConvertUtil.dp2sp(getContext(), dp1);
            int dp2 = ConvertUtil.sp2dp(getContext(), sp);
            assertTrue(Math.abs(dp1 - dp2) <= ALLOWED_MAX_DIFF);
        }
    }

    public void test_String_InputStream_Convert() throws Exception {
        final String encoding = HttpConstant.DEFAULT_ENCODING;

        String from = Calendar.getInstance().toString();
        InputStream inputStream = ConvertUtil.string2InputStream(from, encoding);
        String target = ConvertUtil.inputStream2String(inputStream, encoding);

        assertEquals(from, target);
    }

    public void test_Bytes_InputStream_Convert() throws Exception {
        byte[] from = new byte[TEST_RANGE <= 0 ? 1 : TEST_RANGE];
        for (int i = 0; i < from.length; i++)
            from[i] = (byte) random.nextInt(255);

        InputStream inputStream = ConvertUtil.bytes2InputStream(from);
        byte[] target = ConvertUtil.inputStream2bytes(inputStream);

        assertEquals(from.length, target.length);
        for (int i = 0; i < from.length; i++)
            assertEquals(from[i], target[i]);
    }
}
