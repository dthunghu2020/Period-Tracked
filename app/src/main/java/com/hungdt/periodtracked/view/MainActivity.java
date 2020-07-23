package com.hungdt.periodtracked.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.view.fragment.PaperFragment;
import com.hungdt.periodtracked.view.fragment.ReportFragment;
import com.hungdt.periodtracked.view.fragment.TodayFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bnv = findViewById(R.id.bnv);
        bnv.setItemIconTintList(null);
        bnv.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.bnv_today:
                    selectedFragment = new ReportFragment();
                    break;
                case R.id.bnv_report:
                    selectedFragment = new TodayFragment();
                    break;
                case R.id.bnv_paper:
                    selectedFragment = new PaperFragment();
                    break;
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}
