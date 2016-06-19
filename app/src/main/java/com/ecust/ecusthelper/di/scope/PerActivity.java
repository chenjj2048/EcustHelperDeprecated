package com.ecust.ecusthelper.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created on 2016/6/18
 *
 * @author chenjj2048
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
