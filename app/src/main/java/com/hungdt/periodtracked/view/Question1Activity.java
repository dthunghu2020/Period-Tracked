package com.hungdt.periodtracked.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.dataset.Constant;
import com.hungdt.periodtracked.utils.MySetting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Question1Activity extends AppCompatActivity {
    private TextView txtFirstDay, txtNotification;
    private Button btnChoose;
    private CheckBox checkbox;
    private Button btnNext;
    Calendar calendar = Calendar.getInstance();
    private boolean haveDate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_period_start);

        txtFirstDay = findViewById(R.id.txtFirstDay);
        LinearLayout llCheckBox = findViewById(R.id.llCheckBox);
        btnChoose = findViewById(R.id.btnChoose);
        btnNext = findViewById(R.id.btnNext);
        checkbox = findViewById(R.id.checkbox);
        txtNotification = findViewById(R.id.txtNotification);

        btnNext.setVisibility(View.GONE);
        txtNotification.setVisibility(View.GONE);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdfDate = new SimpleDateFormat(Constant.getDateFormat());
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdfDay = new SimpleDateFormat(Constant.getDayFormat());
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdfMonth = new SimpleDateFormat(Constant.getMonthFormat());
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdfYear = new SimpleDateFormat(Constant.getYearFormat());

        String instanceDate = sdfDate.format(calendar.getTime());
        txtFirstDay.setText(instanceDate);

        final DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtFirstDay.setText(sdfDate.format(calendar.getTime()));
                Date dateCalendar;
                try {
                    dateCalendar = sdfDate.parse(getInstantDateTime());
                    if (dateCalendar != null) {
                        calendar.set(Calendar.YEAR, Integer.parseInt(sdfDay.format(dateCalendar)));
                        calendar.set(Calendar.MONTH, Integer.parseInt(sdfMonth.format(dateCalendar)) - 1);
                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sdfYear.format(dateCalendar)));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveDate) {
                    btnNext.setVisibility(View.VISIBLE);
                    haveDate = true;
                }
                new DatePickerDialog(Question1Activity.this, dateDialog, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                if (checkbox.isChecked()) {
                    MySetting.putFirstDay(getApplicationContext(), getString(R.string.not_sure));
                    MySetting.putFirstDayOfCircle(getApplicationContext(), getString(R.string.not_sure));
                    startActivity(new Intent(Question1Activity.this, Question2Activity.class));
                } else {
                    if (countNumberDay(Integer.parseInt(sdfYear.format(date)), Integer.parseInt(sdfMonth.format(date)), Integer.parseInt(sdfDay.format(date)))
                            > countNumberDay(Integer.parseInt(sdfYear.format(date1)), Integer.parseInt(sdfMonth.format(date1)), Integer.parseInt(sdfDay.format(date1)))) {
                        Toast.makeText(Question1Activity.this, "Please select a date no greater than today", Toast.LENGTH_SHORT).show();
                    } else {
                        MySetting.putFirstDay(getApplicationContext(), text);
                        MySetting.putFirstDayOfCircle(getApplicationContext(), text);
                        startActivity(new Intent(Question1Activity.this, Question2Activity.class));
                    }

                }
            }
        });

        llCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveDate) {
                    btnNext.setVisibility(View.VISIBLE);
                    haveDate = true;
                }
                if (checkbox.isChecked()) {
                    checkbox.setChecked(false);
                    txtFirstDay.setVisibility(View.VISIBLE);
                    btnChoose.setVisibility(View.VISIBLE);
                    txtNotification.setVisibility(View.GONE);
                } else {
                    checkbox.setChecked(true);
                    txtFirstDay.setVisibility(View.GONE);
                    btnChoose.setVisibility(View.GONE);
                    txtNotification.setVisibility(View.VISIBLE);
                }
            }
        });
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveDate) {
                    btnNext.setVisibility(View.VISIBLE);
                    haveDate = true;
                }
                if (checkbox.isChecked()) {
                    checkbox.setChecked(true);
                    txtFirstDay.setVisibility(View.GONE);
                    btnChoose.setVisibility(View.GONE);
                    txtNotification.setVisibility(View.VISIBLE);
                } else {
                    checkbox.setChecked(false);
                    txtFirstDay.setVisibility(View.VISIBLE);
                    btnChoose.setVisibility(View.VISIBLE);
                    txtNotification.setVisibility(View.GONE);
                }
            }
        });

    }

    private String getInstantDateTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(Constant.getDateTimeFormat());
        return sdf.format(calendar.getTime());
    }

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }
}
