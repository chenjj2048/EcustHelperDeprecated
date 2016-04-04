package com.ecust.ecusthelper.util.logUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2016/3/22
 *
 * @author chenjj2048
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogStatus {
    String tagName() default "";

    boolean enable() default true;

    @LogTagStyleDef int style() default LogTagStyle.STYLE_DEFAULT;
}
