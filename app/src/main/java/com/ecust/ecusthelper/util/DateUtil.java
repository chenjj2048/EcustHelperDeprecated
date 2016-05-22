package com.ecust.ecusthelper.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2016/5/21
 *
 * @author chenjj2048
 */
public class DateUtil {
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date getDate(Calendar calendar) {
        return calendar.getTime();
    }
}
