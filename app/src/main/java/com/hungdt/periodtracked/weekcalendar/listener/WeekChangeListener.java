package com.hungdt.periodtracked.weekcalendar.listener;

import org.joda.time.DateTime;

public interface WeekChangeListener {
    void onWeekChanged(DateTime firstDayOfWeek);
}
