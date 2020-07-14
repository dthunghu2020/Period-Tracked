package com.hungdt.periodtracked.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.utils.MySetting;

public class TodayActivity extends AppCompatActivity {
    private TextView txtGetData;
    private BottomNavigationView bnvPT;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        initView();

        bnvPT.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.today:
                        return true;
                    case R.id.report:
                        startActivity(new Intent(TodayActivity.this, ReportActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.health:
                        startActivity(new Intent(TodayActivity.this, HealthActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
        txtGetData.setText("Ngày bắt đầu: " + MySetting.getFirstDay(TodayActivity.this) + "\nSố ngày rớt: " + MySetting.getPeriodLength(TodayActivity.this) + "\nChu kì:" + MySetting.getPeriodCircle(TodayActivity.this));
        bnvPT.setSelectedItemId(R.id.today);
    }

    private void initView() {
        bnvPT = findViewById(R.id.bnvPT);
        txtGetData = findViewById(R.id.txtGetData);
    }
}