package com.hungdt.periodtracked.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.TrunkBranchAnnals;
import com.hungdt.periodtracked.R;


import com.hungdt.periodtracked.utils.MySetting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportFragment extends Fragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnViewChangeListener {
    TextView mTextMonthDay,txtText;
    CalendarView mCalendarView;
    CalendarLayout mCalendarLayout;


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

    public ReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextMonthDay = view.findViewById(R.id.tv_month_day);
        txtText = view.findViewById(R.id.txtText);
        mCalendarView = view.findViewById(R.id.calendarView);
        mCalendarLayout = view.findViewById(R.id.calendarLayout);

        /////////////////
        beginDay = MySetting.getFirstDay(getContext());
        periodCircle = MySetting.getPeriodCircle(getContext());
        periodLength = MySetting.getPeriodLength(getContext());
        //txtMonth.setText(getString(R.string.month) + sdfMY.format(Calendar.getInstance().getTime()));
        ///
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
            }
        });

        //mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        //mCalendarView.setOnCalendarLongClickListener(this, true);
        mCalendarView.setOnWeekChangeListener(this);
        //mCalendarView.setOnYearViewChangeListener(this);

        //设置日期拦截事件，仅适用单选模式，当前无效
        //Đặt ngày để chặn sự kiện, chỉ áp dụng cho chế độ chọn một lần, hiện không hợp lệ
        //mCalendarView.setOnCalendarInterceptListener(this);

        mCalendarView.setOnViewChangeListener(this);
        mTextMonthDay.setText("day " + mCalendarView.getCurDay() + " month " + mCalendarView.getCurMonth() + " year " + mCalendarView.getCurYear());
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Toast.makeText(getContext(), String.format("%s : LongClickOutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
//Todo ............................................................................................
        Log.e("HDT0309", "(2)onCalendarSelect: calendar " + calendar + " - isClick " + isClick);
        Toast.makeText(getContext(), getCalendarText(calendar), Toast.LENGTH_SHORT).show();
        //calendar format: yyyyMMdd


        //Log.e("onDateSelected", "  -- " + calendar.getYear() + "  --  " + calendar.getMonth() + "  -- " + calendar.getDay());
        //mTextLunar.setVisibility(View.VISIBLE);


        //Thay đổi text dòng trên hiển thị ngày tháng năm
        mTextMonthDay.setText("day " + calendar.getDay() + " month" + calendar.getMonth() + " year " + calendar.getYear());


        Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
        Log.e("onDateSelected", "  " + mCalendarView.getSelectedCalendar().getScheme() +
                "  --  " + mCalendarView.getSelectedCalendar().isCurrentDay());
        Log.e("干支年纪 ： ", " -- " + TrunkBranchAnnals.getTrunkBranchYear(calendar.getLunarCalendar().getYear()));
        //Todo ............................................................................................
        txtText.setText(""+calendar);

        if(!beginDay.equals(getString(R.string.not_sure))){
            try {
                date = sdfMyDate.parse(beginDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            android.icu.util.Calendar calendarx = android.icu.util.Calendar.getInstance();
            assert date != null;
            calendarx.setTime(date);
            Date dateCheck = null;
            try {
                dateCheck = sdfMyDate.parse(beginDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // chuyển từ date của nó về Date của mình
            String dateLibrary = calendar.toString();
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
    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {
        //Todo ............................................................................................
        for (Calendar calendar : weekCalendars) {
            Log.e("HDT0309", "(3)onWeekChange:" + calendar.toString());
        }
    }

    @Override
    public void onMonthChange(int year, int month) {
        //Todo ............................................................................................
        Log.e("HDT0309", "onMonthChange: " + year + "  --  " + month);
        Calendar calendar = mCalendarView.getSelectedCalendar();


        mTextMonthDay.setText("day " + calendar.getDay() + " month " + calendar.getMonth());



        //Todo ............................................................................................
    }

    @Override
    public void onViewChange(boolean isMonthView) {
        //Todo ............................................................................................
        Log.e("HDT0309", "(1) onViewChange: isMonthView : " + (isMonthView ? "Xem tháng" : "Xem tuần"));
    }

    private static String getCalendarText(Calendar calendar) {
        Log.e("HDT0309", "(3)getCalendarText: ");
        return String.format("Lịch mới %s", "ngày " + calendar.getDay() + " tháng " + calendar.getMonth() + " năm " + calendar.getYear());
    }

}
