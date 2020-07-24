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

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogHolder> {
    List<Log> items;
    LayoutInflater layoutInflater;

    public LogAdapter(Context context,List<Log> items) {
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogHolder(layoutInflater.inflate(R.layout.item_log, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final LogHolder holder, final int position) {
        if(items.get(position).isChecked()){
            holder.imgTick.setVisibility(View.VISIBLE);
        }else {
            holder.imgTick.setVisibility(View.INVISIBLE);
        }
        Glide.with(layoutInflater.getContext())
                .load(items.get(position).getIdImage())
                .into(holder.imgItem);
        holder.txtTitle.setText(items.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items.get(position).isChecked()){
                    items.get(position).setChecked(false);
                    holder.imgTick.setVisibility(View.INVISIBLE);
                }else {
                    items.get(position).setChecked(true);
                    holder.imgTick.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class LogHolder extends RecyclerView.ViewHolder{
        ImageView imgItem,imgTick;
        TextView txtTitle;
        public LogHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            imgTick = itemView.findViewById(R.id.imgTick);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
