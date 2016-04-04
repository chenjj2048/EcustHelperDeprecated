package com.ecust.ecusthelper.util;

import android.test.AndroidTestCase;

import com.ecust.ecusthelper.util.logUtils.LogTagStyle;

/**
 * Created on 2016/3/22
 *
 * @author chenjj2048
 */
public class logTagStyleTest extends AndroidTestCase {
    public void test_Base_Log_Tag_Style() {
        assertEquals(LogTagStyle.STYLE_CLASS_NAME, 1);
        assertEquals(LogTagStyle.STYLE_METHOD_NAME, 1 << 1);
        assertEquals(LogTagStyle.STYLE_TAG_NAME, 1 << 2);
    }
}
