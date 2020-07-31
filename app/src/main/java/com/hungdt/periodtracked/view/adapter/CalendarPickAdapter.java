package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.CalendarPick;

import java.util.Calendar;
import java.util.List;

public class CalendarPickAdapter extends RecyclerView.Adapter<CalendarPickAdapter.CalendarPickHolder> {
    private OnCalendarPickListener onCalendarPickListener;
    List<CalendarPick> calendars;
    LayoutInflater layoutInflater;
    Calendar dateCalendar = Calendar.getInstance();
    Calendar currentCalendar ;
    Calendar calendar = Calendar.getInstance();

    public CalendarPickAdapter(Context context, List<CalendarPick> calendars,Calendar currentCalendar) {
        this.calendars = calendars;
        this.currentCalendar = currentCalendar;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CalendarPickHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CalendarPickHolder(layoutInflater.inflate(R.layout.item_pick_day, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CalendarPickHolder holder, final int position) {
        CalendarPick calendarPick = calendars.get(position);
        dateCalendar.setTime(calendarPick.getDate());
        holder.itemView.setVisibility(View.VISIBLE);
        holder.txtDay.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.black));

        final int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        final int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        final int displayYear = dateCalendar.get(Calendar.YEAR);

        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        int currentYear = currentCalendar.get(Calendar.YEAR);

        final int instanceDay = calendar.get(Calendar.DAY_OF_MONTH);
        final int instanceMonth = calendar.get(Calendar.MONTH) + 1;
        final int instanceYear = calendar.get(Calendar.YEAR);

        holder.txtToday.setVisibility(View.INVISIBLE);
        holder.txtTodayPick.setVisibility(View.INVISIBLE);


        if (displayDay == instanceDay && displayMonth == instanceMonth && displayYear == instanceYear) {
            holder.txtToday.setVisibility(View.VISIBLE);
            holder.txtTodayPick.setVisibility(View.VISIBLE);
        }

        if(!calendarPick.isPicked()){
            holder.clPick.setVisibility(View.INVISIBLE);
        }else {
            holder.txtToday.setVisibility(View.INVISIBLE);
            holder.clPick.setVisibility(View.VISIBLE);
        }

        if (displayMonth != currentMonth || displayYear != currentYear) {
            holder.itemView.setVisibility(View.INVISIBLE);
        }

        if(countNumberDay(displayYear,displayMonth,displayDay)>countNumberDay(instanceYear,instanceMonth,instanceDay)){
            holder.txtDay.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.gray));
        }

        holder.txtDay.setText(String.valueOf(displayDay));
        holder.txtDayPick.setText(String.valueOf(displayDay));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNumberDay(displayYear,displayMonth,displayDay)<=countNumberDay(instanceYear,instanceMonth,instanceDay)){
                    onCalendarPickListener.OnItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    static class CalendarPickHolder extends ViewHolder {
        TextView txtDay,txtTodayPick,txtToday,txtDayPick;
        ConstraintLayout clPick;
        public CalendarPickHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtTodayPick = itemView.findViewById(R.id.txtTodayPick);
            txtToday = itemView.findViewById(R.id.txtToday);
            txtDayPick = itemView.findViewById(R.id.txtDayPick);
            clPick = itemView.findViewById(R.id.clPick);
        }
    }

    public void setOnCalendarPickListener(OnCalendarPickListener onCalendarPickListener) {
        this.onCalendarPickListener = onCalendarPickListener;
    }

    public interface OnCalendarPickListener {
        void OnItemClicked(int position);
    }
    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }
}
