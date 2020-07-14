package com.hungdt.periodtracked.weekcalendar.utils;

import android.util.Log;

import org.joda.time.DateTime;

public class CalendarUtil {
    public static boolean isSameDay(DateTime t1, DateTime t2){
        Log.e("123", "isSameDay: "+t1+"  "+t2 );
        return t1.toString("yyyyMMdd").equals(t2.toString("yyyyMMdd"));
    }

    public static boolean isToday(DateTime t){
        Log.e("123", "isToday: "+t );
        return isSameDay(t, new DateTime());
    }

    public static boolean isRedDay(){
        return true;
    }

    public static boolean isEggDays(){
        return true;
    }

    public static boolean isEggDay(){
        return true;
    }



}
