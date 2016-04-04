package com.ecust.ecusthelper.util.logUtils;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_ALL;
import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_CLASS_AND_METHOD_NAME;
import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_CLASS_AND_TAG_NAME;
import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_CLASS_NAME;
import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_DEFAULT;
import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_METHOD_AND_TAG_NAME;
import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_METHOD_NAME;
import static com.ecust.ecusthelper.util.logUtils.LogTagStyle.STYLE_TAG_NAME;

/**
 * Created on 2016/3/22
 *
 * @author chenjj2048
 */
@IntDef({STYLE_CLASS_NAME, STYLE_METHOD_NAME, STYLE_TAG_NAME,
        STYLE_DEFAULT, STYLE_ALL,
        STYLE_CLASS_AND_METHOD_NAME, STYLE_CLASS_AND_TAG_NAME, STYLE_METHOD_AND_TAG_NAME})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
public @interface LogTagStyleDef {
}
