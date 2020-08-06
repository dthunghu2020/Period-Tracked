package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.CalendarPick;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.hungdt.periodtracked.view.CalendarMonthActivity.dateAdapter;

public class CalendarShowAdapter extends RecyclerView.Adapter<CalendarShowAdapter.CalendarShowHolder> {
    int periodCircle;
    int periodLength;
    int beginRed = 0;
    int endRed = 0;
    int beginEgg = 0;
    int endEgg = 0;
    int eggDay = 0;
    int numberBeginOfDay;
    private int countNumberFirstDate;
    private Date firstDate = dateAdapter;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    SimpleDateFormat sdfYeah = new SimpleDateFormat("yyyy");
    Calendar currentCalendar;
    Calendar calendar = Calendar.getInstance();
    Calendar dateCalendar = Calendar.getInstance();
    List<CalendarPick> calendars;
    LayoutInflater layoutInflater;
    private boolean setData = false;


    public CalendarShowAdapter(Context context, List<CalendarPick> calendars, Calendar currentCalendar, int periodCircle, int periodLength, int numberBeginOfDay) {
        this.calendars = calendars;
        this.currentCalendar = currentCalendar;
        this.periodCircle = periodCircle;
        this.periodLength = periodLength;
        this.numberBeginOfDay = numberBeginOfDay;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CalendarShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CalendarShowHolder(layoutInflater.inflate(R.layout.item_month, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarShowHolder holder, int position) {
        holder.itemView.setVisibility(View.VISIBLE);
        holder.imgLeft.setVisibility(View.INVISIBLE);
        holder.imgRight.setVisibility(View.INVISIBLE);
        holder.imgRedDay.setVisibility(View.INVISIBLE);
        holder.imgEggDay.setVisibility(View.INVISIBLE);
        holder.imgEgg.setVisibility(View.INVISIBLE);
        holder.txtToday.setVisibility(View.INVISIBLE);
        holder.txtDay.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.black));

        CalendarPick calendarShow = calendars.get(position);
        dateCalendar.setTime(calendarShow.getDate());

        Log.e("123123", "(1)firstDate: " + sdfDate.format(firstDate));
        Log.e("123123", "(1)countNumberFirstDate: " + countNumberFirstDate);


        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        int currentYear = currentCalendar.get(Calendar.YEAR);

        int instanceDay = calendar.get(Calendar.DAY_OF_MONTH);
        int instanceMonth = calendar.get(Calendar.MONTH) + 1;
        int instanceYear = calendar.get(Calendar.YEAR);

        holder.txtDay.setText(String.valueOf(displayDay));
        //holder.txtToday.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.black));

        //////////////////////////////////
        if (firstDate != null) {
            countNumberFirstDate = countNumberDay(Integer.parseInt(sdfYeah.format(firstDate)), Integer.parseInt(sdfMonth.format(firstDate)), Integer.parseInt(sdfDay.format(firstDate)));
            int countFirstDayOfList = countNumberDay(Integer.parseInt(sdfYeah.format(calendars.get(0).getDate())), Integer.parseInt(sdfMonth.format(calendars.get(0).getDate())), Integer.parseInt(sdfDay.format(calendars.get(0).getDate())));

            if (!setData) {

                if (countFirstDayOfList < countNumberFirstDate) {
                    countNumberFirstDate -= periodCircle;
                }
                setData = true;
            }
            Log.e("123123", "(2): " + countNumberFirstDate);

            boolean isRedDay = false;
            beginRed = countNumberFirstDate;
            endRed = beginRed + periodLength;
            eggDay = beginRed + periodCircle - 15;
            beginEgg = eggDay - 6;
            endEgg = eggDay + 4;
            int displayDate = countNumberDay(displayYear, displayMonth, displayDay);
            if (displayDate > endRed) {
                countNumberFirstDate += periodCircle;
                beginRed = countNumberFirstDate;
                endRed += periodCircle;
            }
            if (displayDate > eggDay) {
                eggDay += periodCircle;
            }
            if (displayDate > endEgg) {
                beginEgg += periodCircle;
                endEgg += periodCircle;
            }
            Log.e("11122", ""+displayDate+" "+ numberBeginOfDay);
            if (displayDate >= numberBeginOfDay) {
                Log.e("11122", "123123123123123");
                if (beginRed <= displayDate && displayDate < endRed) {
                    Log.e("123123", "red");
                    isRedDay = true;
                    /*if (displayDate == beginRed) {
                        holder.imgLeft.setVisibility(View.VISIBLE);
                    }
                    if (displayDate == endRed - 1) {
                        holder.imgRight.setVisibility(View.VISIBLE);
                    }*/
                    //holder.imgRedDay.setVisibility(View.VISIBLE);
                    holder.txtDay.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.red));
                    holder.txtToday.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.red));
                    holder.txtDay.setTypeface(holder.txtDay.getTypeface(), Typeface.BOLD);
                }
                if (beginEgg <= displayDate && displayDate <= endEgg) {
                    Log.e("123123", "egg");
                    if (isRedDay) {
                        beginEgg++;
                    } else {
                        /*if (displayDate == beginEgg) {
                            holder.imgLeft.setVisibility(View.VISIBLE);
                        }
                        if (displayDate == endEgg) {
                            holder.imgRight.setVisibility(View.VISIBLE);
                        }*/
                        holder.txtDay.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.violet));
                        holder.txtToday.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.violet));
                        holder.txtDay.setTypeface(holder.txtDay.getTypeface(), Typeface.BOLD);
                        //holder.imgEggDay.setVisibility(View.VISIBLE);
                    }
                }
                if (displayDate == eggDay) {
                    Log.e("123123", "eggday");
                    holder.imgEgg.setVisibility(View.VISIBLE);
                }
            }

        }
        //////////////////////////////////

        if (displayDay == instanceDay && displayMonth == instanceMonth && displayYear == instanceYear) {
            //holder.txtDay.setTextColor(layoutInflater.getContext().getResources().getColor(R.color.red));
            holder.txtToday.setVisibility(View.VISIBLE);
        }

        if (displayMonth != currentMonth || displayYear != currentYear) {
            holder.itemView.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }

    static class CalendarShowHolder extends RecyclerView.ViewHolder {
        ImageView imgLeft, imgRight, imgRedDay, imgEggDay, imgEgg;
        TextView txtDay, txtToday;

        public CalendarShowHolder(@NonNull View itemView) {
            super(itemView);
            imgLeft = itemView.findViewById(R.id.imgLeft);
            imgRight = itemView.findViewById(R.id.imgRight);
            imgRedDay = itemView.findViewById(R.id.imgRedDay);
            imgEggDay = itemView.findViewById(R.id.imgEggDay);
            imgEgg = itemView.findViewById(R.id.imgEgg);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtToday = itemView.findViewById(R.id.txtToday);
        }
    }

    public void setFirstDate() {
        firstDate = dateAdapter;
        setData = false;
    }
}
