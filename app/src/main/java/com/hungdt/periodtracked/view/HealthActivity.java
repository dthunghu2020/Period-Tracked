package com.hungdt.periodtracked.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungdt.periodtracked.R;

public class HealthActivity extends AppCompatActivity {
    private  BottomNavigationView bnvPT;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        bnvPT= findViewById(R.id.bnvPT);
        bnvPT.setSelectedItemId(R.id.health);

        bnvPT.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.today:
                        startActivity(new Intent(HealthActivity.this, TodayActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.report:
                        startActivity(new Intent(HealthActivity.this, ReportActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.health:
                        return true;

                }
                return false;
            }
        });
    }
}
