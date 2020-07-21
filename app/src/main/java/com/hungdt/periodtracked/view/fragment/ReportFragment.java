package com.hungdt.periodtracked.view.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.TrunkBranchAnnals;
import com.hungdt.periodtracked.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ReportFragment extends Fragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnViewChangeListener {
    TextView mTextMonthDay;
    CalendarView mCalendarView;
    CalendarLayout mCalendarLayout;


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
        mCalendarView = view.findViewById(R.id.calendarView);
        mCalendarLayout = view.findViewById(R.id.calendarLayout);
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
