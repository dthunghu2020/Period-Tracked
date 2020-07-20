package com.hungdt.periodtracked.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.CalendarMonthActivity;
import com.hungdt.periodtracked.weekcalendar.WeekCalendar;
import com.hungdt.periodtracked.weekcalendar.listener.DateSelectListener;
import com.hungdt.periodtracked.weekcalendar.listener.GetViewHelper;
import com.hungdt.periodtracked.weekcalendar.listener.WeekChangeListener;
import com.hungdt.periodtracked.weekcalendar.utils.CalendarUtil;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TodayFragment extends Fragment {
    private LinearLayout llCalendar;
    private TextView txtMonth;
    private WeekCalendar weekCalendar;

    Date date;
    Date firstDate;
    int periodCircle;
    int periodLength;
    int beginRed = 0;
    int endRed = 0;
    int beginEgg = 0;
    int endEgg = 0;
    int eggDay = 0;
    String beginDay;

    SimpleDateFormat sdfMyDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfLibraryDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfMY = new SimpleDateFormat("MM-yyyy");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    SimpleDateFormat sdfYeah = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

    public TodayFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today,container,false);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        beginDay = MySetting.getFirstDay(getContext());
        periodCircle = MySetting.getPeriodCircle(getContext());
        periodLength = MySetting.getPeriodLength(getContext());
        txtMonth.setText(getString(R.string.month) + sdfMY.format(Calendar.getInstance().getTime()));

        weekCalendar.setGetViewHelper(new GetViewHelper() {
            @Override
            public View getDayView(int position, View convertView, ViewGroup parent, DateTime dateTime, boolean select) {
                Log.e("123", "getDayView: ");
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_week_day, parent, false);
                }
                //////////
                ImageView imgRedDay = convertView.findViewById(R.id.imgRedDay);
                ImageView imgChoseDay = convertView.findViewById(R.id.imgChoseDay);
                ImageView imgEggDay = convertView.findViewById(R.id.imgEggDay);
                ImageView imgEgg = convertView.findViewById(R.id.imgEgg);

                imgRedDay.setVisibility(View.GONE);
                imgEggDay.setVisibility(View.GONE);
                imgEgg.setVisibility(View.GONE);
                imgChoseDay.setVisibility(View.GONE);
                ////////////
                TextView tvDay = (TextView) convertView.findViewById(R.id.tv_day);
                tvDay.setText(dateTime.toString("dd"));
                if (CalendarUtil.isToday(dateTime) && select) {
                    tvDay.setTextColor(getResources().getColor(R.color.red));
                    imgChoseDay.setVisibility(View.VISIBLE);
                } else if (CalendarUtil.isToday(dateTime)) {
                    tvDay.setTextColor(getResources().getColor(R.color.red));
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                } else if (select) {
                    tvDay.setTextColor(Color.WHITE);
                    //tvDay.setBackgroundResource(R.drawable.circular_blue);
                    imgChoseDay.setVisibility(View.VISIBLE);
                } else {
                    tvDay.setTextColor(Color.BLACK);
                    tvDay.setBackgroundColor(Color.TRANSPARENT);
                }

                //////////
                if(!beginDay.equals(getString(R.string.not_sure))){
                    try {
                        date = sdfMyDate.parse(beginDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    assert date != null;
                    calendar.setTime(date);
                    Date dateCheck = null;
                    try {
                        dateCheck = sdfMyDate.parse(beginDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // chuyển từ date của nó về Date của mình
                    String dateLibrary = dateTime.toString();
                    String itemDay = dateLibrary.substring(0, 10);
                    Date itemDate = null;
                    try {
                        itemDate = sdfLibraryDate.parse(itemDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    assert itemDate != null;
                    itemDay = sdfMyDate.format(itemDate);
                    try {
                        itemDate = sdfMyDate.parse(itemDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    ///////
                    Date firstDateOfWeek = null;
                    try {
                        firstDateOfWeek = sdfMyDate.parse(weekCalendar.getCurrentFirstDay().toString("dd-MM-yyyy"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    while (dateCheck.before(firstDateOfWeek)) {
                        calendar.add(Calendar.DATE, periodCircle);
                        dateCheck = calendar.getTime();
                    }
                    calendar.add(Calendar.DATE, -periodCircle);
                    firstDate = calendar.getTime();
                    /////////////////
                    beginRed = countNumberDay(Integer.parseInt(sdfYeah.format(firstDate)), Integer.parseInt(sdfMonth.format(firstDate)), Integer.parseInt(sdfDay.format(firstDate)));
                    endRed = beginRed + periodLength;
                    eggDay = beginRed + periodCircle - 15;
                    beginEgg = eggDay - 6;
                    endEgg = eggDay + 4;
                    int displayDate = countNumberDay(Integer.parseInt(sdfYeah.format(itemDate)), Integer.parseInt(sdfMonth.format(itemDate)), Integer.parseInt(sdfDay.format(itemDate)));
                    if (displayDate > endRed) {
                        beginRed += periodCircle;
                        endRed += periodCircle;
                    }
                    if (displayDate > eggDay) {
                        eggDay += periodCircle;
                    }
                    if (displayDate > endEgg) {
                        beginEgg += periodCircle;
                        endEgg += periodCircle;
                    }
                    boolean isRedDay = false;
                    if (beginRed <= displayDate && displayDate < endRed) {
                        isRedDay = true;
                           /* if (displayDate == beginRed) {
                                imgLeft.setVisibility(View.VISIBLE);
                            }
                            if (displayDate == endRed - 1) {
                                imgRight.setVisibility(View.VISIBLE);
                            }*/
                        imgRedDay.setVisibility(View.VISIBLE);
                    }
                    if (beginEgg <= displayDate && displayDate <= endEgg) {
                        if (isRedDay) {
                            beginEgg++;
                        } else {
                               /* if (displayDate == beginEgg) {
                                    imgLeft.setVisibility(View.VISIBLE);
                                }
                                if (displayDate == endEgg) {
                                    imgRight.setVisibility(View.VISIBLE);
                                }*/
                            imgEggDay.setVisibility(View.VISIBLE);
                        }
                    }

                    if (displayDate == eggDay) {
                        imgEgg.setVisibility(View.VISIBLE);
                    }
                }

                return convertView;
            }

            @Override
            public View getWeekView(int position, View convertView, ViewGroup parent, String week) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_week, parent, false);
                }
                TextView tvWeek = (TextView) convertView.findViewById(R.id.tv_week);
                tvWeek.setText(week);
                if (position == 0 || position == 6) {
                    tvWeek.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                return convertView;
            }
        });

        weekCalendar.setWeekChangedListener(new WeekChangeListener() {
            @Override
            public void onWeekChanged(DateTime firstDayOfWeek) {
                String text = getString(R.string.month) + firstDayOfWeek.toString("MM-yyyy");
                txtMonth.setText(text);
            }
        });

        weekCalendar.setDateSelectListener(new DateSelectListener() {
            @Override
            public void onDateSelect(DateTime selectDate) {
                String text = getString(R.string.month) + selectDate.toString("MM-yyyy");
                txtMonth.setText(text);
            }
        });

        llCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CalendarMonthActivity.class));
            }
        });

    }

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }

    private void initView(View view) {
        llCalendar = view.findViewById(R.id.llCalendar);
        txtMonth = view.findViewById(R.id.txtMonth);
        weekCalendar = view.findViewById(R.id.week_calendar);
    }
}