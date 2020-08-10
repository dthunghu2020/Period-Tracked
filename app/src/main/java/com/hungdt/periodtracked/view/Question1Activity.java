package com.hungdt.periodtracked.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.dataset.Constant;
import com.hungdt.periodtracked.model.CalendarPick;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.adapter.CalendarPickAdapter;
import com.hungdt.periodtracked.view.adapter.LogAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Question1Activity extends AppCompatActivity {
    private TextView txtFirstDay, txtNotification, txtDate;
    private Button btnNext;
    ImageView imgNext, imgPrevious;
    LinearLayout  llCalendar, llSelectedDate;
    RecyclerView rcvPick;
    Calendar calendar = Calendar.getInstance();
    private boolean haveData = false;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar currentCalendar = Calendar.getInstance();
    Date currentDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    List<CalendarPick> calendarPicks = new ArrayList<>();
    CalendarPickAdapter calendarPickAdapter;
    private String firstDate = "";
    public static final String ACTION_FINISH_Q1 = "F_Q1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_period_start);
        if (MySetting.firstTime(this)) {
            initView();
            btnNext.setVisibility(View.GONE);
            txtNotification.setVisibility(View.GONE);
            //imgNext.setVisibility(View.INVISIBLE);

            setUpCalendar();
            llSelectedDate.setVisibility(View.INVISIBLE);

            rcvPick.setLayoutManager(new GridLayoutManager(this, 7));
            calendarPickAdapter = new CalendarPickAdapter(this, calendarPicks, currentCalendar);
            rcvPick.setAdapter(calendarPickAdapter);

            calendarPickAdapter.setOnCalendarPickListener(new CalendarPickAdapter.OnCalendarPickListener() {
                @Override
                public void OnItemClicked(int position) {
                    btnNext.setVisibility(View.VISIBLE);
                    for (int i = 0; i < calendarPicks.size(); i++) {
                        if (calendarPicks.get(i).isPicked()) {
                            calendarPicks.get(i).setPicked(false);
                        }
                    }
                    llSelectedDate.setVisibility(View.VISIBLE);
                    calendarPicks.get(position).setPicked(true);
                    firstDate = sdfDate.format(calendarPicks.get(position).getDate().getTime());
                    txtFirstDay.setText(firstDate);
                    calendarPickAdapter.notifyDataSetChanged();
                }
            });

            imgPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentCalendar.add(Calendar.MONTH, -1);
                    setUpCalendar();
                    calendarPickAdapter.notifyDataSetChanged();
                    imgNext.setVisibility(View.VISIBLE);
                }
            });

            imgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentCalendar.add(Calendar.MONTH, 1);
                    setUpCalendar();
                    calendarPickAdapter.notifyDataSetChanged();
                    if (currentCalendar.get(Calendar.MONTH) < calendar.get(Calendar.MONTH) && currentCalendar.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR)) {
                        imgNext.setVisibility(View.VISIBLE);
                    } /*else {
                    imgNext.setVisibility(View.INVISIBLE);
                }*/
                }
            });


            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = txtFirstDay.getText().toString();
                    Date date = null;
                    Date date1 = Calendar.getInstance().getTime();
                    try {
                        date = sdfDate.parse(text);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (countNumberDay(Integer.parseInt(sdfYear.format(date)), Integer.parseInt(sdfMonth.format(date)), Integer.parseInt(sdfDay.format(date)))
                            > countNumberDay(Integer.parseInt(sdfYear.format(date1)), Integer.parseInt(sdfMonth.format(date1)), Integer.parseInt(sdfDay.format(date1)))) {
                        Toast.makeText(Question1Activity.this, "Please select a date no greater than today", Toast.LENGTH_SHORT).show();
                    } else {
                        MySetting.putFirstDay(getApplicationContext(), text);
                        MySetting.setBeginCycle(getApplicationContext(), text);
                        startActivity(new Intent(Question1Activity.this, Question2Activity.class));
                    }


                }
            });

        } else {
            startActivity(new Intent(Question1Activity.this, MainActivity.class));
        }
        IntentFilter intentFilter = new IntentFilter(ACTION_FINISH_Q1);
        registerReceiver(broadcast, intentFilter);
    }

    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    private void setUpCalendar() {
        currentDate = currentCalendar.getTime();
        String instanceDate = dateFormat.format(currentCalendar.getTime());
        txtDate.setText(instanceDate);
        calendarPicks.clear();
        Calendar calender2 = (Calendar) currentCalendar.clone();
        calender2.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = calender2.get(Calendar.DAY_OF_WEEK) - 1;
        calender2.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (calendarPicks.size() < MAX_CALENDAR_DAYS) {
            calendarPicks.add(new CalendarPick(calender2.getTime()));
            if (sdfDate.format(calender2.getTime()).equals(firstDate)) {
                calendarPicks.get(calendarPicks.size() - 1).setPicked(true);
            }
            calender2.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void initView() {
        txtFirstDay = findViewById(R.id.txtFirstDay);
        llCalendar = findViewById(R.id.llCalendar);
        btnNext = findViewById(R.id.btnNext);
        txtNotification = findViewById(R.id.txtNotification);
        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        txtDate = findViewById(R.id.txtDate);
        rcvPick = findViewById(R.id.rcvPick);
        llSelectedDate = findViewById(R.id.llSelectedDate);
    }

   /* private String getInstantDateTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(Constant.getDateTimeFormat());
        return sdf.format(calendar.getTime());
    }*/

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcast);
        super.onDestroy();
    }
}
