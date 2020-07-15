package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hungdt.periodtracked.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyGridAdapter extends ArrayAdapter {
    int periodCircle;
    int periodLength;
    int beginRed = 0;
    int endRed = 0;
    int beginEgg = 0;
    int endEgg = 0;
    int eggDay = 0;
    Date firstDate;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    SimpleDateFormat sdfYeah = new SimpleDateFormat("yyyy");
    List<Date> dates;
    Calendar currentCalendar;
    Calendar calendar = Calendar.getInstance();
    Calendar dateCalendar = Calendar.getInstance();
    LayoutInflater inflater;
    private ImageView imgLeft, imgRight, imgRedDay, imgEggDay, imgEgg;
    TextView txtDay;

    public MyGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentCalendar, Date firstDate, int periodCircle, int periodLength) {
        super(context, R.layout.item_month);
        this.dates = dates;
        this.firstDate = firstDate;
        this.currentCalendar = currentCalendar;
        this.periodCircle = periodCircle;
        this.periodLength = periodLength;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //20/6/2020 - Calendar
        //15/7/2020


        Date date = dates.get(position);
        dateCalendar.setTime(date);

        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        int currentYear = currentCalendar.get(Calendar.YEAR);

        int instanceDay = calendar.get(Calendar.DAY_OF_MONTH);
        int instanceMonth = calendar.get(Calendar.MONTH) + 1;
        int instanceYear = calendar.get(Calendar.YEAR);


        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_month, parent, false);
        }
        initView(view);
        //////////////////////////////////
        if(firstDate!=null){
            boolean isRedDay = false;
            beginRed = countNumberDay(Integer.parseInt(sdfYeah.format(firstDate)), Integer.parseInt(sdfMonth.format(firstDate)), Integer.parseInt(sdfDay.format(firstDate)));
            endRed = beginRed + periodLength;
            eggDay = beginRed + periodCircle - 15;
            beginEgg = eggDay - 6;
            endEgg = eggDay + 4;
            int displayDate = countNumberDay(displayYear, displayMonth, displayDay);
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
            if (beginRed <= displayDate && displayDate < endRed) {
                isRedDay = true;
                if (displayDate == beginRed) {
                    imgLeft.setVisibility(View.VISIBLE);
                }
                if (displayDate == endRed - 1) {
                    imgRight.setVisibility(View.VISIBLE);
                }
                imgRedDay.setVisibility(View.VISIBLE);
            }
            if (beginEgg <= displayDate && displayDate <= endEgg) {
                if (isRedDay) {
                    beginEgg++;
                } else {
                    if (displayDate == beginEgg) {
                        imgLeft.setVisibility(View.VISIBLE);
                    }
                    if (displayDate == endEgg) {
                        imgRight.setVisibility(View.VISIBLE);
                    }
                    imgEggDay.setVisibility(View.VISIBLE);
                }
            }

            if (displayDate == eggDay) {
                imgEgg.setVisibility(View.VISIBLE);
            }
        }
        //////////////////////////////////

        if (displayDay == instanceDay && displayMonth == instanceMonth && displayYear == instanceYear) {
            txtDay.setTextColor(getContext().getResources().getColor(R.color.red));
        }

        if (displayMonth != currentMonth || displayYear != currentYear) {
            view.setVisibility(View.GONE);
        }

        txtDay.setText(String.valueOf(displayDay));

        return view;
    }

    private void initView(View view) {
        imgLeft = view.findViewById(R.id.imgLeft);
        imgRight = view.findViewById(R.id.imgRight);
        imgRedDay = view.findViewById(R.id.imgRedDay);
        imgEggDay = view.findViewById(R.id.imgEggDay);
        imgEgg = view.findViewById(R.id.imgEgg);
        txtDay = view.findViewById(R.id.txtDay);

        imgLeft.setVisibility(View.GONE);
        imgRight.setVisibility(View.GONE);
        imgRedDay.setVisibility(View.GONE);
        imgEggDay.setVisibility(View.GONE);
        imgEgg.setVisibility(View.GONE);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }
}
