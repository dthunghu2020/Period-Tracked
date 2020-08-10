package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Data;
import com.hungdt.periodtracked.model.SettingCalendar;
import com.hungdt.periodtracked.utils.KEY;
import com.hungdt.periodtracked.view.SettingPeriodActivity;
import com.hungdt.periodtracked.view.fragment.TodayFragment;

import java.util.Calendar;
import java.util.List;

public class SettingCalendarAdapter extends RecyclerView.Adapter<SettingCalendarAdapter.SettingCalendarHolder> {

    List<SettingCalendar> settingCalendars;
    LayoutInflater layoutInflater;
    SettingCheckBoxAdapter settingCheckBoxAdapter;
    List<String> monthOfYears;

    public SettingCalendarAdapter(Context context, List<SettingCalendar> settingCalendars,  List<String> monthOfYears) {
        this.settingCalendars = settingCalendars;
        this.monthOfYears = monthOfYears;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SettingCalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingCalendarHolder(layoutInflater.inflate(R.layout.item_setting_calendar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingCalendarHolder holder, int position) {
        holder.txtDate.setText(monthOfYears.get(position));
        holder.rcvSettingCalendar.setLayoutManager(new GridLayoutManager(layoutInflater.getContext(), 7));
        settingCheckBoxAdapter = new SettingCheckBoxAdapter(layoutInflater.getContext(), settingCalendars.get(position).getCalendarPicks(), monthOfYears.get(position));
        holder.rcvSettingCalendar.setAdapter(settingCheckBoxAdapter);

        settingCheckBoxAdapter.setOnCalendarSettingListener(new SettingCheckBoxAdapter.OnCalendarSettingListener() {
            @Override
            public void OnItemClicked(int position,String day) {
                Intent intent = new Intent(SettingPeriodActivity.ACTION_UPDATE_BEGIN_CIRCLE);
                intent.putExtra(KEY.BEGIN_CYCLE, day);
                layoutInflater.getContext().sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return settingCalendars.size();
    }

    static class SettingCalendarHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        RecyclerView rcvSettingCalendar;

        public SettingCalendarHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            rcvSettingCalendar = itemView.findViewById(R.id.rcvSettingCalendar);
        }
    }
}
