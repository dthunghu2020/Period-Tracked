package com.hungdt.periodtracked.weekcalendar.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.hungdt.periodtracked.weekcalendar.listener.GetViewHelper;
import com.hungdt.periodtracked.weekcalendar.utils.CalendarUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.hungdt.periodtracked.weekcalendar.WeekCalendar.DAYS_OF_WEEK;


public class DayAdapter extends BaseAdapter {

    private List<DateTime> dateTimes;
    private GetViewHelper getViewHelper;
    private DateTime selectDateTime;

    public DayAdapter(DateTime startDateTime, GetViewHelper getViewHelper, DateTime selectDateTime) {
        this.getViewHelper = getViewHelper;
        this.selectDateTime = selectDateTime;
        dateTimes = new ArrayList<>();
        for (int i = 0; i < DAYS_OF_WEEK; i++) {
            dateTimes.add(new DateTime(startDateTime).plusDays(i));
        }
    }

    @Override
    public int getCount() {
        return DAYS_OF_WEEK;
    }

    @Override
    public DateTime getItem(int position) {
        return dateTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewHelper.getDayView(position, convertView, parent, dateTimes.get(position), CalendarUtil.isSameDay(dateTimes.get(position), selectDateTime));
    }

    public void setSelectDateTime(DateTime selectDateTime) {
        this.selectDateTime = selectDateTime;
    }
}
