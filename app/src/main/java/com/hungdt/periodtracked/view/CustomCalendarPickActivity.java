package com.hungdt.periodtracked.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.adapter.CalendarMonthGridAdapter;
import com.hungdt.periodtracked.view.adapter.CalendarPickAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomCalendarPickActivity extends LinearLayout {

    ImageView imgPrevious, imgNext;
    TextView txtDate;
    GridView gvDay;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar currentCalendar = Calendar.getInstance();
    Date currentDate;
    Context context;

    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    Date date = null;
    Date firstDate = null;
    CalendarPickAdapter pickAdapter;
    List<Date> dates = new ArrayList<>();


    public CustomCalendarPickActivity(Context context) {
        super(context);
    }

    public CustomCalendarPickActivity(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        setUpCalendar();

        imgPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, -1);
                setUpCalendar();
            }
        });
        imgNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, 1);
                setUpCalendar();
            }
        });

        gvDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pickAdapter.visiblePick();
                pickAdapter.notifyDataSetChanged();
                Toast.makeText(context, "" + dayFormat.format(dates.get(position)) + "-" + monthFormat.format(dates.get(position)) + "-" + yearFormat.format(dates.get(position)), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public CustomCalendarPickActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.custom_calendar_month, this);
        imgNext = view.findViewById(R.id.imgNext);
        imgPrevious = view.findViewById(R.id.imgPrevious);
        txtDate = view.findViewById(R.id.txtDate);
        gvDay = view.findViewById(R.id.gvDay);
    }

    private void setUpCalendar() {
        //tháng năm lịch hiển thị.
        currentDate = currentCalendar.getTime();
        String instanceDate = dateFormat.format(currentCalendar.getTime());
        txtDate.setText(instanceDate);
        dates.clear();
        Calendar calender2 = (Calendar) currentCalendar.clone();
        calender2.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = calender2.get(Calendar.DAY_OF_WEEK) - 1;
        calender2.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(calender2.getTime());
            calender2.add(Calendar.DAY_OF_MONTH, 1);
        }

        pickAdapter = new CalendarPickAdapter(context, dates, currentCalendar, firstDate);
        gvDay.setAdapter(pickAdapter);


    }
}
