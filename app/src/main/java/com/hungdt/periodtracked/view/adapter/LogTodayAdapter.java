package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Log;

import java.util.List;

public class LogTodayAdapter extends RecyclerView.Adapter<LogTodayAdapter.LogTodayHolder> {
    List<Log> items;
    LayoutInflater layoutInflater;

    public LogTodayAdapter(Context context, List<Log> items) {
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LogTodayAdapter.LogTodayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogTodayAdapter.LogTodayHolder(layoutInflater.inflate(R.layout.item_log_today, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final LogTodayHolder holder, final int position) {
        Glide.with(layoutInflater.getContext())
                .load(items.get(position).getIdImage())
                .into(holder.imgItem);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class LogTodayHolder extends RecyclerView.ViewHolder{
        ImageView imgItem;
        public LogTodayHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
        }
    }
}
