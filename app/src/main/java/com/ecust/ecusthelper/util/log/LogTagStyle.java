package com.ecust.ecusthelper.util.log;

/**
 * Created on 2016/3/22
 *
 * @author chenjj2048
 */
@SuppressWarnings("unused")
public class LogTagStyle {
    //显示一种标签
    public static final int STYLE_CLASS_NAME = 1;
    public static final int STYLE_METHOD_NAME = 1 << 1;
    public static final int STYLE_TAG_NAME = 1 << 2;

    //显示两种标签
    public static final int STYLE_CLASS_AND_METHOD_NAME = STYLE_CLASS_NAME + STYLE_METHOD_NAME;
    public static final int STYLE_CLASS_AND_TAG_NAME = STYLE_CLASS_NAME + STYLE_TAG_NAME;
    public static final int STYLE_METHOD_AND_TAG_NAME = STYLE_METHOD_NAME + STYLE_TAG_NAME;

    //显示三种标签
    public static final int STYLE_ALL = STYLE_CLASS_NAME + STYLE_METHOD_NAME + STYLE_TAG_NAME;

    //默认样式
    public static final int STYLE_DEFAULT = STYLE_ALL;
}
