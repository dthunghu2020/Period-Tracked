package com.hungdt.periodtracked.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Log;
import com.hungdt.periodtracked.view.adapter.LogAdapter;
import com.hungdt.periodtracked.view.fragment.ReportFragment;

import java.util.ArrayList;
import java.util.List;

import static com.hungdt.periodtracked.view.MainActivity.motions;
import static com.hungdt.periodtracked.view.MainActivity.ovulations;
import static com.hungdt.periodtracked.view.MainActivity.physics;
import static com.hungdt.periodtracked.view.MainActivity.setData;
import static com.hungdt.periodtracked.view.MainActivity.symptoms;

public class LogActivity extends AppCompatActivity {
    LogAdapter motionAdapter;
    LogAdapter symptomAdapter;
    LogAdapter physicAdapter;
    LogAdapter ovulationAdapter;
    List<Log> motionss = new ArrayList<>();
    List<Log> symptomss = new ArrayList<>();
    List<Log> physicss = new ArrayList<>();
    List<Log> ovulationss = new ArrayList<>();

    RecyclerView rcvMotion,rcvPhysic,rcvSymptom,rcvOvulation;
    Button btnSave;
    ImageView imgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        imgBack = findViewById(R.id.imgBack);
        rcvMotion = findViewById(R.id.rcvMotion);
        rcvSymptom = findViewById(R.id.rcvSymptom);
        rcvPhysic = findViewById(R.id.rcvPhysic);
        rcvOvulation = findViewById(R.id.rcvOvulation);
        btnSave = findViewById(R.id.btnSave);

        if(!setData){
            motions.add(new Log(R.drawable.calm,R.string.motion3,false));
            motions.add(new Log(R.drawable.happy,R.string.motion8,false));
            motions.add(new Log(R.drawable.energetic,R.string.motion6,false));
            motions.add(new Log(R.drawable.frisky,R.string.motion7,false));
            motions.add(new Log(R.drawable.mood_swing,R.string.motion10,false));
            motions.add(new Log(R.drawable.irritated,R.string.motion9,false));
            motions.add(new Log(R.drawable.sad,R.string.motion13,false));
            motions.add(new Log(R.drawable.anxious,R.string.motion1,false));
            motions.add(new Log(R.drawable.depressed,R.string.motion5,false));
            motions.add(new Log(R.drawable.feeling_guilty,R.string.motion12,false));
            motions.add(new Log(R.drawable.obsessive_thoughts,R.string.motion11,false));
            motions.add(new Log(R.drawable.apathetic,R.string.motion2,false));
            motions.add(new Log(R.drawable.confused,R.string.motion4,false));

            symptoms.add(new Log(R.drawable.fine,R.string.symptom8,false));
            symptoms.add(new Log(R.drawable.cramps,R.string.symptom5,false));
            symptoms.add(new Log(R.drawable.tender_breast,R.string.symptom15,false));
            symptoms.add(new Log(R.drawable.headache,R.string.symptom10,false));
            symptoms.add(new Log(R.drawable.acne,R.string.symptom2,false));
            symptoms.add(new Log(R.drawable.backache,R.string.symptom3,false));
            symptoms.add(new Log(R.drawable.nausea,R.string.symptom12,false));
            symptoms.add(new Log(R.drawable.fatigue,R.string.symptom9,false));
            symptoms.add(new Log(R.drawable.craving,R.string.symptom6,false));
            symptoms.add(new Log(R.drawable.insomnia,R.string.symptom11,false));
            symptoms.add(new Log(R.drawable.constipation,R.string.symptom4,false));
            symptoms.add(new Log(R.drawable.diarrhea,R.string.symptom7,false));
            symptoms.add(new Log(R.drawable.abdominal_pain,R.string.symptom1,false));
            symptoms.add(new Log(R.drawable.perineum_pain,R.string.symptom13,false));
            symptoms.add(new Log(R.drawable.swelling,R.string.symptom14,false));

            physics.add(new Log(R.drawable.didnt_excercise,R.string.physic2,false));
            physics.add(new Log(R.drawable.running,R.string.physic4,false));
            physics.add(new Log(R.drawable.cycling,R.string.physic1,false));
            physics.add(new Log(R.drawable.gym,R.string.physic3,false));
            physics.add(new Log(R.drawable.yoga,R.string.physic7,false));
            physics.add(new Log(R.drawable.team_sport,R.string.physic6,false));
            physics.add(new Log(R.drawable.swimming,R.string.physic5,false));

            ovulations.add(new Log(R.drawable.didnot_take_test,R.string.ovulation1,false));
            ovulations.add(new Log(R.drawable.positive,R.string.ovulation4,false));
            ovulations.add(new Log(R.drawable.negative,R.string.ovulation2,false));
            ovulations.add(new Log(R.drawable.ovulation_my_method,R.string.ovulation3,false));

            setData=true;
        }
        motionss.addAll(motions);
        symptomss.addAll(symptoms);
        physicss.addAll(physics);
        ovulationss.addAll(ovulations);


        rcvMotion.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        motionAdapter = new LogAdapter(this,motionss);
        rcvMotion.setAdapter(motionAdapter);

        rcvSymptom.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        symptomAdapter = new LogAdapter(this,symptomss);
        rcvSymptom.setAdapter(symptomAdapter);

        rcvPhysic.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        physicAdapter = new LogAdapter(this,physicss);
        rcvPhysic.setAdapter(physicAdapter);

        rcvOvulation.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ovulationAdapter = new LogAdapter(this,ovulationss);
        rcvOvulation.setAdapter(ovulationAdapter);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motions.clear();
                symptoms.clear();
                physics.clear();
                ovulations.clear();
                motions.addAll(motionss);
                symptoms.addAll(symptomss);
                physics.addAll(physicss);
                ovulations.addAll(ovulationss);
                Toast.makeText(LogActivity.this, "Save success", Toast.LENGTH_SHORT).show();
                sendBroadcast(new Intent(ReportFragment.ACTION_UPDATE_LOG));
            }
        });
    }

}
