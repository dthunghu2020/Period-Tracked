package com.hungdt.periodtracked.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hungdt.periodtracked.view.SettingActivity;

public class MySetting {
    public static final String FIRST_DAY = "first_day";
    public static final String BEGIN_CYCLE = "cycle";
    public static final String P_LENGTH = "period_length";
    public static final String P_CIRCLE = "period_circle";
    public static final String GEMS = "gems";
    public static final String FIRST_TIME = "first";
    public static final String USER_NAME = "name";
    public static final String USER_BIRTH_YEAR = "birth_year";
    public static final String CONFIG_GG_FB = "dfhhddfhdf";
    public static final String CONFIG_MORE_GAME = "ssvbvbc";
    public static final String SETTINGS = "ggggdfgdfhfgs";
    public static final String KEY_REMOVE_ADS = "ncvdnrgdcn";
    public static final String KEY_RATE_APP = "yhfghhnrdffndxcx";
    public static final String KEY_SUBSCRIPTION = "nrcvfdbnbre";
    public static final String AM_8 = "8AM";
    public static final String AM_12 = "12AM";
    public static final String PM_8 = "8PM";


    public static void set8AM(Context context, boolean am8) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AM_8, am8);
        editor.apply();
    }

    public static boolean get8AM(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getBoolean(AM_8,true);
    }

    public static void set12AM(Context context, boolean am12) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AM_12, am12);
        editor.apply();
    }

    public static boolean get12AM(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getBoolean(AM_12,false);
    }

    public static void set8PM(Context context, boolean pm8) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PM_8, pm8);
        editor.apply();
    }

    public static boolean get8PM(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getBoolean(PM_8,false);
    }


    public static void setUserBirthYear(Context context, int year) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_BIRTH_YEAR, year);
        editor.apply();
    }

    public static int getUserBirthYear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getInt(USER_BIRTH_YEAR,2000);
    }

    public static void setBeginCycle(Context context, String cycle) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(BEGIN_CYCLE, cycle);
        editor.apply();
    }

    public static String getBeginCycle(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getString(BEGIN_CYCLE, "");
    }

    public static void setUserName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, name);
        editor.apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getString(USER_NAME, "");
    }

    public static void putFirstDay(Context context, String firstDay) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FIRST_DAY, firstDay);
        editor.apply();
    }

    public static String getFirstDay(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getString(FIRST_DAY, "01-01-2020");
    }

    public static void putPeriodLength(Context context, int length) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(P_LENGTH, length);
        editor.apply();
    }

    public static int getPeriodLength(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getInt(P_LENGTH,5);
    }

    public static void putPeriodCircle(Context context, int circle) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(P_CIRCLE, circle);
        editor.apply();
    }

    public static int getPeriodCircle(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getInt(P_CIRCLE, 28);
    }


    public static void putConfigMoregame(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CONFIG_MORE_GAME, value);
        editor.apply();
    }

    public static String getConfigMoregame(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getString(CONFIG_MORE_GAME, "false");
    }


    public static boolean firstTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getBoolean(FIRST_TIME, true);
    }

    public static void setFirstTime(Context context, boolean isFirst) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRST_TIME, isFirst);
        editor.apply();
    }


    public static int getGems(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getInt(GEMS, 0);
    }

    public static void setGems(Context context, int count) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GEMS, count);
        editor.apply();
    }

    public static void putConfigGgFb(Context context, long value) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(CONFIG_GG_FB, value);
        editor.apply();
    }

    public static long getConfigGgFb(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getLong(CONFIG_GG_FB, 0);
    }

    public static void setSubscription(Context context, boolean isSub) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_SUBSCRIPTION, isSub);
        editor.apply();
    }

    public static boolean isSubscription(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_SUBSCRIPTION, false);
    }

    public static int isRateApp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getInt(KEY_RATE_APP, 0);
    }

    public static boolean putRateApp(Context context, int value) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_RATE_APP, value);
        return editor.commit();
    }

    public static void putRemoveAds(Context context, boolean removeAds) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_REMOVE_ADS, removeAds);
        editor.apply();
    }

    public static boolean isRemoveAds(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_REMOVE_ADS, false);
    }



}
