package com.hungdt.periodtracked.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.CalendarPick;
import com.hungdt.periodtracked.model.Data;
import com.hungdt.periodtracked.model.SettingCalendar;
import com.hungdt.periodtracked.utils.KEY;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.adapter.SettingCalendarAdapter;
import com.hungdt.periodtracked.view.fragment.TodayFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.hungdt.periodtracked.view.MainActivity.beginCircleDay;
import static com.hungdt.periodtracked.view.MainActivity.dataList;

public class SettingPeriodActivity extends AppCompatActivity {
    public static final String ACTION_UPDATE_BEGIN_CIRCLE = "update begin cycle";
    private ImageView imgBack;
    private Button btnSave;
    private RecyclerView rcvEditPeriod;
    SettingCalendarAdapter settingCalendarAdapter;
    private List<SettingCalendar> settingCalendars = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private Calendar currentCalendar = Calendar.getInstance();
    private Date currentDate;
    private static final int MAX_CALENDAR_DAYS = 42;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    public static String beginCycleDayAdapter = beginCircleDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_period);
        imgBack = findViewById(R.id.imgBack);
        btnSave = findViewById(R.id.btnSave);
        rcvEditPeriod = findViewById(R.id.rcvEditPeriod);

        btnSave.setVisibility(View.INVISIBLE);

        //Setting Calendar

        //

        List<String> monthOfYear = new ArrayList<>();
        //txtDate.setText(instanceDate);


        currentCalendar.add(Calendar.MONTH, -18);
        for (int i = 0; i < 20; i++) {
            currentCalendar.add(Calendar.MONTH, 1);
            //currentDate = calendar.getTime();
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

       /* for(String s: monthOfYear){
            List<Data> data = new ArrayList<>();
            for(int i = 0; i < dataList.size(); i++){
                Date date = null;
                try {
                    date = sdfDate.parse(dataList.get(i).getDay());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String month = dateFormat.format(date);
                if (month.equals(s)) {
                    data.add(dataList.get(i));
                }
            }
            if(data.size()>0){
                dataSettings.add(new DataSetting(data,s));
                Log.e("11111", ">00: "+dataSettings.size());
            }
        }*/


        rcvEditPeriod.setLayoutManager(new LinearLayoutManager(this));
        settingCalendarAdapter = new SettingCalendarAdapter(this, settingCalendars, monthOfYear);
        rcvEditPeriod.setAdapter(settingCalendarAdapter);
        ((LinearLayoutManager) rcvEditPeriod.getLayoutManager()).scrollToPosition(17);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginCycleDayAdapter = beginCircleDay;
                onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySetting.setBeginCycle(SettingPeriodActivity.this,beginCycleDayAdapter);
                sendBroadcast(new Intent(CalendarMonthActivity.ACTION_REFRESH_CALENDAR));
                sendBroadcast(new Intent(MainActivity.ACTION_UPDATE_DATA));
                onBackPressed();
                Toast.makeText(SettingPeriodActivity.this, R.string.save_period_start_success, Toast.LENGTH_SHORT).show();
            }
        });

        IntentFilter intentFilter = new IntentFilter(ACTION_UPDATE_BEGIN_CIRCLE);
        registerReceiver(broadCastUpdateCycle,intentFilter);
    }
    private final BroadcastReceiver broadCastUpdateCycle = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            beginCycleDayAdapter = intent.getStringExtra(KEY.BEGIN_CYCLE);
            settingCalendarAdapter.notifyDataSetChanged();
            if(!beginCycleDayAdapter.equals(beginCircleDay)){
                btnSave.setVisibility(View.VISIBLE);
            }else {
                btnSave.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastUpdateCycle);
    }
}
