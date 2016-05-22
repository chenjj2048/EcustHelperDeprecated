package com.ecust.ecusthelper.util;

import android.test.AndroidTestCase;

import java.util.Random;

/**
 * Created on 2016/3/17
 *
 * @author chenjj2048
 */
public class SizeUtilTest extends AndroidTestCase {
    //px、sp、dp反复转换后引起的最大误差
    private final int ALLOWED_MAX_DIFF = 1;
    private int TEST_RANGE;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.TEST_RANGE = new Random().nextInt(10000);
    }

    public void test_px_dp_Convert() {
        for (int px1 = 0; px1 <= TEST_RANGE; px1++) {
            int dp = SizeUtil.px2dp(getContext(), px1);
            int px2 = SizeUtil.dp2px(getContext(), dp);
            assertTrue(Math.abs(px1 - px2) <= ALLOWED_MAX_DIFF);
        }
    }

    public void test_px_sp_Convert() {
        for (int px1 = 0; px1 <= TEST_RANGE; px1++) {
            int sp = SizeUtil.px2sp(getContext(), px1);
            int px2 = SizeUtil.dp2px(getContext(), sp);
            assertTrue(Math.abs(px1 - px2) <= ALLOWED_MAX_DIFF);
        }
    }

    public void test_dp_sp_Convert() {
        for (int dp1 = 0; dp1 <= TEST_RANGE; dp1++) {
            int sp = SizeUtil.dp2sp(getContext(), dp1);
            int dp2 = SizeUtil.sp2dp(getContext(), sp);
            assertTrue(Math.abs(dp1 - dp2) <= ALLOWED_MAX_DIFF);
        }
    }
}
