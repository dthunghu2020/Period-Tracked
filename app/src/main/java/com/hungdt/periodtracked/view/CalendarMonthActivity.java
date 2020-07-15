package com.hungdt.periodtracked.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hungdt.periodtracked.R;

public class CalendarMonthActivity extends AppCompatActivity {
    CustomCalendarMonth customCalendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_month);
        customCalendarView = findViewById(R.id.custom_calendar_view);
   }
}
