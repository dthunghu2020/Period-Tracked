package com.hungdt.periodtracked.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Paper;
import com.hungdt.periodtracked.view.adapter.PaperAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PaperActivity extends AppCompatActivity {
    private  BottomNavigationView bnvPT;

    private PaperAdapter pagerAdapter;
    private RecyclerView rcvPaper;
    private List<Paper> papers= new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

       initView();
        bnvPT.setSelectedItemId(R.id.health);


        bnvPT.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.today:
                        startActivity(new Intent(PaperActivity.this, TodayActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.report:
                        startActivity(new Intent(PaperActivity.this, ReportActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.health:
                        return true;

                }
                return false;
            }
        });

        papers.add(new Paper("0",R.string.title1,R.string.body1,R.drawable.paper1));
        papers.add(new Paper("0",R.string.title2,R.string.body2,R.drawable.paper2));
        papers.add(new Paper("0",R.string.title3,R.string.body3,R.drawable.paper3));
        papers.add(new Paper("0",R.string.title4,R.string.body4,R.drawable.paper4));
        papers.add(new Paper("0",R.string.title5,R.string.body5,R.drawable.paper5));
        papers.add(new Paper("0",R.string.title6,R.string.body6,R.drawable.paper6));
        papers.add(new Paper("0",R.string.title7,R.string.body7,R.drawable.paper7));
        papers.add(new Paper("0",R.string.title8,R.string.body8,R.drawable.paper8));

        rcvPaper.setHasFixedSize(true);
        rcvPaper.setLayoutManager( new LinearLayoutManager(this));
        pagerAdapter = new PaperAdapter(PaperActivity.this,papers);
        rcvPaper.setAdapter(pagerAdapter);
    }

    private void initView() {
        bnvPT= findViewById(R.id.bnvPT);
        rcvPaper = findViewById(R.id.rcvPaper);
    }
}
