package com.hungdt.periodtracked.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.database.DBHelper;
import com.hungdt.periodtracked.model.Data;
import com.hungdt.periodtracked.model.Log;
import com.hungdt.periodtracked.view.fragment.PaperFragment;
import com.hungdt.periodtracked.view.fragment.ReportFragment;
import com.hungdt.periodtracked.view.fragment.TodayFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static  List<Data> dataList = new ArrayList<>();
    public static List<Log> motions = new ArrayList<>();
    public static List<Log> symptoms = new ArrayList<>();
    public static List<Log> physics = new ArrayList<>();
    public static List<Log> ovulations = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bnv = findViewById(R.id.bnv);
        bnv.setItemIconTintList(null);
        bnv.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportFragment()).commit();

        dataList = DBHelper.getInstance(this).getAllData();

        motions.add(new Log(R.drawable.calm, R.string.motion01, false));
        motions.add(new Log(R.drawable.happy, R.string.motion02, false));
        motions.add(new Log(R.drawable.energetic, R.string.motion03, false));
        motions.add(new Log(R.drawable.frisky, R.string.motion04, false));
        motions.add(new Log(R.drawable.mood_swing, R.string.motion05, false));
        motions.add(new Log(R.drawable.irritated, R.string.motion06, false));
        motions.add(new Log(R.drawable.sad, R.string.motion07, false));
        motions.add(new Log(R.drawable.anxious, R.string.motion08, false));
        motions.add(new Log(R.drawable.depressed, R.string.motion09, false));
        motions.add(new Log(R.drawable.feeling_guilty, R.string.motion10, false));
        motions.add(new Log(R.drawable.obsessive_thoughts, R.string.motion11, false));
        motions.add(new Log(R.drawable.apathetic, R.string.motion12, false));
        motions.add(new Log(R.drawable.confused, R.string.motion13, false));

        symptoms.add(new Log(R.drawable.fine, R.string.symptom01, false));
        symptoms.add(new Log(R.drawable.cramps, R.string.symptom02, false));
        symptoms.add(new Log(R.drawable.tender_breast, R.string.symptom03, false));
        symptoms.add(new Log(R.drawable.headache, R.string.symptom04, false));
        symptoms.add(new Log(R.drawable.acne, R.string.symptom05, false));
        symptoms.add(new Log(R.drawable.backache, R.string.symptom06, false));
        symptoms.add(new Log(R.drawable.nausea, R.string.symptom07, false));
        symptoms.add(new Log(R.drawable.fatigue, R.string.symptom08, false));
        symptoms.add(new Log(R.drawable.craving, R.string.symptom09, false));
        symptoms.add(new Log(R.drawable.insomnia, R.string.symptom10, false));
        symptoms.add(new Log(R.drawable.constipation, R.string.symptom11, false));
        symptoms.add(new Log(R.drawable.diarrhea, R.string.symptom12, false));
        symptoms.add(new Log(R.drawable.abdominal_pain, R.string.symptom13, false));
        symptoms.add(new Log(R.drawable.perineum_pain, R.string.symptom14, false));
        symptoms.add(new Log(R.drawable.swelling, R.string.symptom15, false));

        physics.add(new Log(R.drawable.didnt_excercise, R.string.physic01, false));
        physics.add(new Log(R.drawable.running, R.string.physic02, false));
        physics.add(new Log(R.drawable.cycling, R.string.physic03, false));
        physics.add(new Log(R.drawable.gym, R.string.physic04, false));
        physics.add(new Log(R.drawable.yoga, R.string.physic05, false));
        physics.add(new Log(R.drawable.team_sport, R.string.physic06, false));
        physics.add(new Log(R.drawable.swimming, R.string.physic07, false));

        ovulations.add(new Log(R.drawable.didnot_take_test, R.string.ovulation1, false));
        ovulations.add(new Log(R.drawable.positive, R.string.ovulation2, false));
        ovulations.add(new Log(R.drawable.negative, R.string.ovulation3, false));
        ovulations.add(new Log(R.drawable.ovulation_my_method, R.string.ovulation4, false));

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
