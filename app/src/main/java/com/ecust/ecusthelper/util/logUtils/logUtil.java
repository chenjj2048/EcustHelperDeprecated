package com.ecust.ecusthelper.util.logUtils;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.WeakHashMap;

/**
 * Created on 2016/3/21
 *
 * @author chenjj2048
 */
@SuppressWarnings("unused")
public class logUtil {
    private static boolean mGlobalEnable = true;
    private static WeakHashMap<String, String> mWeakHashMap = new WeakHashMap<>();

    public static void enable(boolean global_enable) {
        logUtil.mGlobalEnable = global_enable;
    }

    public static void v(String tag, String msg) {
        if (!mGlobalEnable) return;
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return;
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (!mGlobalEnable) return;
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return;
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!mGlobalEnable) return;
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return;
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (!mGlobalEnable) return;
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return;
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!mGlobalEnable) return;
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return;
        Log.e(tag, msg);
    }

    /**
     * 获取函数调用者
     *
     * @param stackDepth 调用者所在的堆栈深度
     */
    private static StackTraceElement getInvoker(int stackDepth) {
        /**
         * 0: dalvik.system.VMStack getThreadStackTrace()
         * 1: java.lang.Thread getStackTrace()
         * 2: 当前类 当前函数
         * 3: 来自调用类 来自调用函数
         */
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[stackDepth];
    }

    /**
     * 获取TAG名称
     *
     * @param obj 传入 this 或 xx.class
     * @return 如果注解中不允许显示，返回""，否则根据注解中的规则返回相应的TAG
     */
    private static String getTagNameFromObject(Object obj) {
        final int INVOKER_STACK_DEPTH = 5;
        final String invokerClassName = getInvoker(INVOKER_STACK_DEPTH).getClassName();
        final String invokerMethodName = getInvoker(INVOKER_STACK_DEPTH).getMethodName();
        final Class clazz = (obj instanceof Class) ? (Class) obj : obj.getClass();

        final String key = generateKey(invokerClassName, invokerMethodName);

        String tagName = mWeakHashMap.get(key);
        if (tagName == null) {
            tagName = createNewTagNameAndSave(clazz, invokerClassName, invokerMethodName);
        }
        return tagName;
    }

    private static String generateKey(String className, String methodName) {
        return className + methodName;
    }

    /**
     * 迭代类里的所有方法，寻找注解，产生TAG，并且存入WeakHashMap
     * 这里有个问题：如果一个函数比如func()，有各种多态形式的话，同时都有注解的话，可能TAG会有错误
     */
    private static String createNewTagNameAndSave(Class clazz, String invokerClassName, String invokerMethodName) {
        final Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(LogStatus.class)) {
                LogStatus annotation = method.getAnnotation(LogStatus.class);
                String key = generateKey(invokerClassName, method.getName());
                String tagName;

                if (annotation.enable()) {
                    tagName = getTagNameByAnnotationStyle(annotation, invokerClassName, invokerMethodName);
                    if (TextUtils.isEmpty(tagName))
                        tagName = invokerClassName + "." + invokerMethodName;
                } else {
                    tagName = "";
                }
                mWeakHashMap.put(key, tagName);
            }
        }

        final String key = generateKey(invokerClassName, invokerMethodName);
        String tagName = mWeakHashMap.get(key);
        //函数上不含注解
        if (tagName == null) {
            tagName = clazz.getCanonicalName();
        }
        return tagName;
    }

    /**
     * 通过注解类型及调用者类、函数名产生Tag
     */
    private static String getTagNameByAnnotationStyle(@NonNull LogStatus annotation,
                                                      String invokerClassName, String invokerMethodName) {
        final boolean showClassName = hasStyle(annotation.style(), LogTagStyle.STYLE_CLASS_NAME);
        final boolean showMethodName = hasStyle(annotation.style(), LogTagStyle.STYLE_METHOD_NAME);
        final boolean showTagName = hasStyle(annotation.style(), LogTagStyle.STYLE_TAG_NAME);

        StringBuilder sb = new StringBuilder();
        final String SPLIT_STRING = " ";
        if (showClassName)
            sb.append(invokerClassName).append(SPLIT_STRING);
        if (showMethodName)
            sb.append(invokerMethodName).append(SPLIT_STRING);
        if (showTagName)
            sb.append(annotation.tagName()).append(SPLIT_STRING);
        if (sb.length() > 0)
            sb.delete(sb.length() - SPLIT_STRING.length(), sb.length());

        return sb.toString();
    }

    /**
     * @param style    合并后的各种Style( STYLE_CLASS_NAME|STYLE_METHOD_NAME|STYLE_TAG_NAME )
     * @param logStyle 三种基础的LogStyle之一(STYLE_CLASS_NAME、STYLE_METHOD_NAME、STYLE_TAG_NAME)
     * @return 是否存在该基础的Style
     */
    private static boolean hasStyle(@LogTagStyleDef int style, @LogTagStyleDef int logStyle) {
        int bit = style & logStyle;
        return bit - logStyle == 0;
    }

    public static void v(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        v(TAG, msg);
    }

    public static void d(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        d(TAG, msg);
    }

    public static void i(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        i(TAG, msg);
    }

    public static void w(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        w(TAG, msg);
    }

    public static void e(Object obj, String msg) {
        if (!mGlobalEnable) return;
        final String TAG = getTagNameFromObject(obj);
        e(TAG, msg);
    }
}