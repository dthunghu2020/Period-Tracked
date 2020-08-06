package com.hungdt.periodtracked.model;

import java.util.List;

public class SettingCalendar {
    List<CalendarPick> calendarPicks;

    public SettingCalendar(List<CalendarPick> calendarPicks) {
        this.calendarPicks = calendarPicks;
    }

    public List<CalendarPick> getCalendarPicks() {
        return calendarPicks;
    }

    public void setCalendarPicks(List<CalendarPick> calendarPicks) {
        this.calendarPicks = calendarPicks;
    }
}
