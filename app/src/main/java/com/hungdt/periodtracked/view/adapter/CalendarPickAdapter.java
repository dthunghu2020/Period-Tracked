package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hungdt.periodtracked.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarPickAdapter  extends ArrayAdapter {

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
    TextView txtDay,txtTodayPick,txtToday, txtDayPick;
    ConstraintLayout clPick;

    public CalendarPickAdapter(@NonNull Context context, List<Date> dates, Calendar currentCalendar, Date firstDate) {
        super(context, R.layout.item_month);
        this.dates = dates;
        this.firstDate = firstDate;
        this.currentCalendar = currentCalendar;
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
            view = inflater.inflate(R.layout.item_pick_day, parent, false);
        }
        initView(view);

        clPick.setVisibility(View.INVISIBLE);
        txtToday.setVisibility(View.INVISIBLE);

        if (displayDay == instanceDay && displayMonth == instanceMonth && displayYear == instanceYear) {
            txtToday.setVisibility(View.VISIBLE);
        }

        if (displayMonth != currentMonth || displayYear != currentYear) {
            view.setVisibility(View.GONE);
        }

        txtDay.setText(String.valueOf(displayDay));
        txtDayPick.setText(String.valueOf(displayDay));

        return view;
    }

    private void initView(View view) {
        txtDay = view.findViewById(R.id.txtDay);
        txtTodayPick = view.findViewById(R.id.txtTodayPick);
        txtToday = view.findViewById(R.id.txtToday);
        txtDayPick = view.findViewById(R.id.txtDayPick);
        clPick = view.findViewById(R.id.clPick);
    }

    public void visiblePick(){
        clPick.setVisibility(View.VISIBLE);
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
}
