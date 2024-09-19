package com.jpsolanoc.transactions.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Util {
    public static Date addOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    public static Date addHourOfDate(Date fechaExistente,int h,int m, int s) {
        LocalDateTime localDateTime = fechaExistente.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.withHour(h).withMinute(m).withSecond(s);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
