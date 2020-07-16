package com.hungdt.periodtracked.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Paper;
import com.hungdt.periodtracked.utils.KEY;
import com.hungdt.periodtracked.view.DetailPaperActivity;

import java.security.Key;
import java.util.List;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperHolder> {

    private List<Paper> papers;
    private LayoutInflater layoutInflater;

    public PaperAdapter(Context context, List<Paper> papers) {
        this.papers = papers;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaperHolder(layoutInflater.inflate(R.layout.item_paper, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaperHolder holder, final int position) {
        holder.txtTitle.setText(papers.get(position).getTitle());
        //holder.txtBody.setVisibility(View.GONE);
        //holder.txtBody.setText(papers.get(position).getBody());
        Glide.with(layoutInflater.getContext())
                .load(papers.get(position).getIdImage())
                .into(holder.imgPaper);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layoutInflater.getContext(), DetailPaperActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY.PAPER,papers.get(position));
                intent.putExtras(bundle);
                layoutInflater.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return papers.size();
    }

    static class PaperHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtBody;
        ImageView imgPaper;

        public PaperHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            //txtBody = itemView.findViewById(R.id.txtBody);
            imgPaper = itemView.findViewById(R.id.imgPaper);
        }
    }
}
