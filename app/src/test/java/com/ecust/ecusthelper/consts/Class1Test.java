package com.ecust.ecusthelper.consts;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created on 2016/5/15
 *
 * @author chenjj2048
 */
public class Class1Test {

    @Test
    public void testGet() throws Exception {
        if (class1.get() == null)
            Assert.fail("为什么这里会失败");
    }
}