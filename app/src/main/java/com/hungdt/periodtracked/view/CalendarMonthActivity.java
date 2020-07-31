package com.hungdt.periodtracked.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.CalendarPick;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.adapter.CalendarPickAdapter;
import com.hungdt.periodtracked.view.adapter.CalendarShowAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarMonthActivity extends AppCompatActivity {
    private Calendar currentCalendar = Calendar.getInstance();
    private Date currentDate;
    private ImageView imgPrevious, imgNext, imgBack;
    private TextView txtDate;
    private RecyclerView rcvCalendarShow;
    private List<CalendarPick> calendarShows = new ArrayList<>();
    private CalendarShowAdapter calendarShowAdapter;
    private static final int MAX_CALENDAR_DAYS = 42;
    Date date = null;
    Date firstDate = null;
    public static Date dateAdapter = null;
    private String beginDay;
    private int periodLength;
    private int periodCircle;


    Calendar calendar = Calendar.getInstance();

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);
        initView();

        beginDay = MySetting.getFirstDay(this);
        periodLength = MySetting.getPeriodLength(this);
        periodCircle = MySetting.getPeriodCircle(this);
        Date date = null;
        try {
            date = sdfDate.parse(beginDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int beginOfDay = countNumberDay(Integer.parseInt(sdfYear.format(date)), Integer.parseInt(sdfMonth.format(date)), Integer.parseInt(sdfDay.format(date)));


        setUpCalendar();

        rcvCalendarShow.setLayoutManager(new GridLayoutManager(this, 7));
        calendarShowAdapter = new CalendarShowAdapter(this, calendarShows, currentCalendar, periodCircle, periodLength,beginOfDay);
        rcvCalendarShow.setAdapter(calendarShowAdapter);

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, -1);
                setUpCalendar();
                calendarShowAdapter.setFirstDate();
                calendarShowAdapter.notifyDataSetChanged();
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, 1);
                setUpCalendar();
                Log.e("111", "(1)firstDate: " + sdfDate.format(firstDate));
                calendarShowAdapter.setFirstDate();
                calendarShowAdapter.notifyDataSetChanged();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpCalendar() {
        currentDate = currentCalendar.getTime();
        String instanceDate = dateFormat.format(currentCalendar.getTime());
        txtDate.setText(instanceDate);
        calendarShows.clear();
        Calendar calender2 = (Calendar) currentCalendar.clone();
        calender2.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = calender2.get(Calendar.DAY_OF_WEEK) - 1;
        calender2.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (calendarShows.size() < MAX_CALENDAR_DAYS) {
            calendarShows.add(new CalendarPick(calender2.getTime()));
            calender2.add(Calendar.DAY_OF_MONTH, 1);
        }
        //////////
        if (!beginDay.equals(getString(R.string.not_sure))) {
            try {
                date = sdfDate.parse(beginDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);
            Date dateCheck = null;
            try {
                dateCheck = sdfDate.parse(beginDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert dateCheck != null;
            while (dateCheck.before(currentDate)) {
                calendar.add(Calendar.DATE, periodCircle);
                dateCheck = calendar.getTime();
                Log.e("11112", "dateCheck: " + sdfDate.format(dateCheck));
            }
            calendar.add(Calendar.DATE, -(periodCircle * 2));
            firstDate = calendar.getTime();
            Log.e("11112", "firstDate: " + sdfDate.format(firstDate));
        } else {
            firstDate = null;
        }
        dateAdapter = firstDate;
        /////////////////////
    }

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        txtDate = findViewById(R.id.txtDate);
        rcvCalendarShow = findViewById(R.id.rcvCalendarShow);
    }
}
