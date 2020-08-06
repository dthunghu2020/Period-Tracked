package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.SettingCalendar;

import java.util.List;

public class SettingCalendarAdapter extends RecyclerView.Adapter<SettingCalendarAdapter.SettingCalendarHolder> {

    List<SettingCalendar> settingCalendars;
    LayoutInflater layoutInflater;
    SettingCheckBoxAdapter settingCheckBoxAdapter;
    List<String> monthOfYears;

    public SettingCalendarAdapter(Context context, List<SettingCalendar> settingCalendars, List<String> monthOfYears) {
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
        settingCheckBoxAdapter = new SettingCheckBoxAdapter(layoutInflater.getContext(), settingCalendars.get(position).getCalendarPicks(),monthOfYears.get(position));
        holder.rcvSettingCalendar.setAdapter(settingCheckBoxAdapter);

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
            txtDate=itemView.findViewById(R.id.txtDate);
            rcvSettingCalendar=itemView.findViewById(R.id.rcvSettingCalendar);
        }
    }
}
