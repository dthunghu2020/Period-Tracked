package com.hungdt.periodtracked.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Paper;
import com.hungdt.periodtracked.view.adapter.PaperAdapter;

import java.util.ArrayList;
import java.util.List;

public class PaperFragment extends Fragment {

    private PaperAdapter pagerAdapter;
    private RecyclerView rcvPaper;
    private List<Paper> papers= new ArrayList<>();

    public PaperFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paper,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        papers.add(new Paper("0",R.string.title1,R.string.body1,R.drawable.paper1));
        papers.add(new Paper("0",R.string.title2,R.string.body2,R.drawable.paper2));
        papers.add(new Paper("0",R.string.title3,R.string.body3,R.drawable.paper3));
        papers.add(new Paper("0",R.string.title4,R.string.body4,R.drawable.paper4));
        papers.add(new Paper("0",R.string.title5,R.string.body5,R.drawable.paper5));
        papers.add(new Paper("0",R.string.title6,R.string.body6,R.drawable.paper6));
        papers.add(new Paper("0",R.string.title7,R.string.body7,R.drawable.paper7));
        papers.add(new Paper("0",R.string.title8,R.string.body8,R.drawable.paper8));

        rcvPaper.setHasFixedSize(true);
        rcvPaper.setLayoutManager( new LinearLayoutManager(getContext()));
        pagerAdapter = new PaperAdapter(getContext(),papers);
        rcvPaper.setAdapter(pagerAdapter);
    }

    private void initView(View view) {
        rcvPaper = view.findViewById(R.id.rcvPaper);
    }
}
