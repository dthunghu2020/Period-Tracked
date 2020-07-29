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
import com.hungdt.periodtracked.model.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {
    private LayoutInflater layoutInflater;
    private List<Report> reportList;

    public ReportAdapter(Context context, List<Report> reportList) {
        this.reportList = reportList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReportHolder(layoutInflater.inflate(R.layout.item_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        Report report = reportList.get(position);
        if(report.getCount()==0){
            holder.txtItemR.setText(report.getType());
        }else {
            holder.txtItemR.setText(report.getType() + " " + report.getCount() + "%");
        }

        Glide.with(layoutInflater.getContext())
                .load(report.getImageId())
                .into(holder.imgItemR);
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    static class ReportHolder extends RecyclerView.ViewHolder {
        ImageView imgItemR;
        TextView txtItemR;

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            imgItemR = itemView.findViewById(R.id.imgItemR);
            txtItemR = itemView.findViewById(R.id.txtItemR);
        }
    }
}
