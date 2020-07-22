package com.hungdt.periodtracked.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.TrunkBranchAnnals;
import com.haibin.calendarview.WeekViewPager;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.utils.MySetting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

public class ReportFragment extends Fragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnViewChangeListener {
    TextView mTextMonthDay, txtCircleText;
    CalendarView mCalendarView;
    CalendarLayout mCalendarLayout;

    Date curDate;
    Date firstDateOfWeek;
    Date date;
    Date firstDate;
    int beginRed = 0;
    int endRed = 0;
    int beginEgg = 0;
    int endEgg = 0;
    int eggDay = 0;
    int periodCircle;
    int periodLength;
    String beginDay;
    int beginDayNumber;


    String bR;

    SimpleDateFormat sdfMyDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdfLibraryDate = new SimpleDateFormat("yyyyMMdd");
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
        txtCircleText = view.findViewById(R.id.txtCircleText);
        mCalendarView = view.findViewById(R.id.calendarView);
        mCalendarLayout = view.findViewById(R.id.calendarLayout);

        beginDay = MySetting.getFirstDay(getActivity());
        Log.e("HDT0309", "onViewCreated: " + beginDay);
        periodCircle = MySetting.getPeriodCircle(getActivity());
        periodLength = MySetting.getPeriodLength(getActivity());

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

        Log.e("HDT0309", "firstDateOfWeek " + mCalendarView.getCurrentWeekCalendars().get(0));
        ///////////// Convert Date
        String firstDateString = mCalendarView.getCurrentWeekCalendars().get(0).toString();
        try {
            firstDateOfWeek = sdfLibraryDate.parse(firstDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert firstDateOfWeek != null;
        firstDateString = sdfMyDate.format(firstDateOfWeek);
        try {
            firstDateOfWeek = sdfMyDate.parse(firstDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("HDT0309", "firstDateOfWeek " + firstDateOfWeek); //Oke
        ///
        Log.e("HDT0309", "curDay " + mCalendarView.getCurDay()); //Oke

        if (!beginDay.equals(getString(R.string.not_sure))) {
            try {
                date = sdfMyDate.parse(beginDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            beginDayNumber = countNumberDay(Integer.parseInt(sdfYeah.format(date)), Integer.parseInt(sdfMonth.format(date)), Integer.parseInt(sdfDay.format(date)));
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);
            Date beginDate = null;
            try {
                beginDate = sdfMyDate.parse(beginDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            while (beginDate.before(firstDateOfWeek)) {
                calendar.add(java.util.Calendar.DATE, periodCircle);
                beginDate = calendar.getTime();
            }
            //calendar.add(java.util.Calendar.DATE, -periodCircle);
            firstDate = calendar.getTime();
            /////////////////
            beginRed = countNumberDay(Integer.parseInt(sdfYeah.format(firstDate)), Integer.parseInt(sdfMonth.format(firstDate)), Integer.parseInt(sdfDay.format(firstDate)));
            bR = "" + sdfDay.format(firstDate) + "-" + sdfMonth.format(firstDate) + "-" + sdfYeah.format(firstDate);
            endRed = beginRed + periodLength - 1;
            eggDay = beginRed + periodCircle - 15;
            beginEgg = eggDay - 6;
            endEgg = eggDay + 4;

            int curDayNumber = countNumberDay(mCalendarView.getCurYear(),mCalendarView.getCurMonth(),mCalendarView.getCurDay());
            while (curDayNumber >= beginRed + periodCircle) {
                beginRed += periodCircle;
                endRed += periodCircle;
                eggDay += periodCircle;
                beginEgg += periodCircle;
                endEgg += periodCircle;
            }

            if (curDayNumber >= beginRed && curDayNumber <= endRed) {
                int number = curDayNumber - beginRed + 1;
                txtCircleText.setText("Period:\nDay " + number);
            } else {
                if (curDayNumber < eggDay) {
                    int number = eggDay - curDayNumber;
                    if (curDayNumber < beginEgg) {
                        txtCircleText.setText("Ovulation in\n" + number + " days\nLow chance of getting pregnant");
                    } else {
                        txtCircleText.setText("Ovulation in\n" + number + " days\nHave a chance of getting pregnant");
                    }
                } else if (curDayNumber > eggDay) {
                    int number = beginRed + periodCircle - curDayNumber ;
                    if (curDayNumber > endEgg) {
                        txtCircleText.setText("Period in\n" + number + " days\nLow chance of getting pregnant");
                    } else {
                        txtCircleText.setText("Period in\n" + number + " days\nHave a chance to get pregnant");
                    }
                } else {
                    txtCircleText.setText("Prediction: Day of\nOVULATION\nHigh chance of getting pregnant");
                }
            }
            Log.e("HDT0309", "Data:  firstDate-" + firstDateString + " beginDay: " + firstDate);
            //int displayDate = countNumberDay(Integer.parseInt(sdfYeah.format(itemDate)), Integer.parseInt(sdfMonth.format(itemDate)), Integer.parseInt(sdfDay.format(itemDate)));
        } else {
            txtCircleText.setText("Log the first day of your last period for better predictions\nLog Period");
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Toast.makeText(getContext(), String.format("%s : LongClickOutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
//Todo ............................................................................................
        Log.e("HDT0309", "(2)onCalendarSelect: calendar " + calendar + " - isClick " + isClick);
        //Toast.makeText(getContext(), getCalendarText(calendar), Toast.LENGTH_SHORT).show();
        //calendar format: yyyyMMdd

        if (!beginDay.equals(getString(R.string.not_sure))) {
            String curDateString = calendar.toString();
            try {
                curDate = sdfLibraryDate.parse(curDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert curDate != null;
            curDateString = sdfMyDate.format(curDate);
            try {
                curDate = sdfMyDate.parse(curDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("HDT0309", "curDate " + curDate); //Oke
            int curDayNumber = countNumberDay(Integer.parseInt(sdfYeah.format(curDate)), Integer.parseInt(sdfMonth.format(curDate)), Integer.parseInt(sdfDay.format(curDate)));
            if (curDayNumber >= beginDayNumber) {
                if (curDayNumber >= beginRed + periodCircle) {
                    beginRed += periodCircle;
                    endRed += periodCircle;
                    eggDay += periodCircle;
                    beginEgg += periodCircle;
                    endEgg += periodCircle;
                }

                if (beginRed>curDayNumber) {
                    beginRed -= periodCircle;
                    endRed -= periodCircle;
                    eggDay -= periodCircle;
                    beginEgg -= periodCircle;
                    endEgg -= periodCircle;
                }

                //Thay đổi text dòng trên hiển thị ngày tháng năm
                mTextMonthDay.setText("day " + calendar.getDay() + " month" + calendar.getMonth() + " year " + calendar.getYear());

                if (curDayNumber >= beginRed && curDayNumber <= endRed) {
                    int number = curDayNumber - beginRed + 1;
                    txtCircleText.setText("Period:\nDay " + number);
                } else {
                    if (curDayNumber < eggDay) {
                        int number = eggDay - curDayNumber;
                        if (curDayNumber < beginEgg) {
                            txtCircleText.setText("Ovulation in\n" + number + " days\nLow chance of getting pregnant");
                        } else {
                            txtCircleText.setText("Ovulation in\n" + number + " days\nHave a chance of getting pregnant");
                        }
                    } else if (curDayNumber > eggDay) {
                        int number = beginRed + periodCircle - curDayNumber ;
                        if (curDayNumber > endEgg) {
                            txtCircleText.setText("Period in\n" + number + " days\nLow chance of getting pregnant");
                        } else {
                            txtCircleText.setText("Period in\n" + number + " days\nHave a chance to get pregnant");
                        }
                    } else {
                        txtCircleText.setText("Prediction: Day of\nOVULATION\nHigh chance of getting pregnant");
                    }
                }

                Log.e("HDT0309", "selectDay: +" + curDayNumber + "\nbeginRed: " + bR + " " + beginRed + "\nendRed" + endRed + "\neggDay" + eggDay);
                //txtCircleText.setText("selectDay: +"+curDayNumber+"\nbeginRed: "+bR+" "+beginRed+"\nendRed"+endRed+"\neggDay"+eggDay);

            } else {
                txtCircleText.setText("Predictions will be more accurate if you log your past periods\nLog Period ");
            }
        }else {
            txtCircleText.setText("Log the first day of your last period for better predictions\nLog Period");
        }

        /*Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
        Log.e("onDateSelected", "  " + mCalendarView.getSelectedCalendar().getScheme() +
                "  --  " + mCalendarView.getSelectedCalendar().isCurrentDay());*/
        //Todo ............................................................................................

    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {
        //Todo ............................................................................................
        ///////////// Convert Date
        String firstDateString = weekCalendars.get(0).toString();
        try {
            firstDateOfWeek = sdfLibraryDate.parse(firstDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert firstDateOfWeek != null;
        firstDateString = sdfMyDate.format(firstDateOfWeek);
        try {
            firstDateOfWeek = sdfMyDate.parse(firstDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("HDT0309", "firstDateOfWeek " + firstDateOfWeek); //Oke
        ///
        for (Calendar calendar : weekCalendars) {
            //Log.e("HDT0309", "(3)onWeekChange:" + calendar.toString());
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
        return String.format("%s", "ngày " + calendar.getDay() + " tháng " + calendar.getMonth() + " năm " + calendar.getYear());
    }

    private int countNumberDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }
}
