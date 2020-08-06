package com.hungdt.periodtracked.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.CalendarPick;
import com.hungdt.periodtracked.model.SettingCalendar;
import com.hungdt.periodtracked.view.adapter.SettingCalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SettingPeriodActivity extends AppCompatActivity {
    private ImageView imgBack;
    private Button btnSave;
    private RecyclerView rcvEditPeriod;
    SettingCalendarAdapter settingCalendarAdapter;
    private List<SettingCalendar> settingCalendars= new ArrayList<>();
    private  Calendar calendar = Calendar.getInstance();
    private  Calendar currentCalendar = Calendar.getInstance();
    private Date currentDate ;
    private static final int MAX_CALENDAR_DAYS = 42;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_period);

        imgBack = findViewById(R.id.imgBack);
        btnSave = findViewById(R.id.btnSave);
        rcvEditPeriod = findViewById(R.id.rcvEditPeriod);
        //Setting Calendar

        //

        List<String> monthOfYear = new ArrayList<>();
        //txtDate.setText(instanceDate);


        currentCalendar.add(Calendar.MONTH, -18);
        for(int i = 0; i <20;i++){
            currentCalendar.add(Calendar.MONTH, 1);
            currentDate = calendar.getTime();
            String instanceDate = dateFormat.format(currentCalendar.getTime());
            monthOfYear.add(instanceDate);
            Calendar calender2 = (Calendar) currentCalendar.clone();
            calender2.set(Calendar.DAY_OF_MONTH, 1);
            int firstDayOfMonth = calender2.get(Calendar.DAY_OF_WEEK) - 1;
            calender2.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);
            List<CalendarPick> calendarPicks = new ArrayList<>();
            while (calendarPicks.size() < MAX_CALENDAR_DAYS) {
                dateFormat.format(currentCalendar.getTime());
                calendarPicks.add(new CalendarPick(calender2.getTime()));
                calender2.add(Calendar.DAY_OF_MONTH, 1);
            }
            settingCalendars.add(new SettingCalendar(calendarPicks));
        }



        rcvEditPeriod.setLayoutManager(new LinearLayoutManager(this));
        settingCalendarAdapter = new SettingCalendarAdapter(this,settingCalendars,monthOfYear);
        rcvEditPeriod.setAdapter(settingCalendarAdapter);
        ((LinearLayoutManager)rcvEditPeriod.getLayoutManager()).scrollToPositionWithOffset(17, 200);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
    }
}
