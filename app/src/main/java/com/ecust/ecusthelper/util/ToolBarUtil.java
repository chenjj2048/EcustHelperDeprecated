package com.ecust.ecusthelper.util;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created on 2016/7/17
 *
 * @author chenjj2048
 */
public class ToolbarUtil {

    public static void setupToolbar(Activity target, Toolbar toolbar) {
        AppCompatActivity activity;
        if (target instanceof AppCompatActivity)
            activity = (AppCompatActivity) target;
        else
            throw new IllegalArgumentException("请传个AppCompatActivity进来");

        activity.setSupportActionBar(toolbar);
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
