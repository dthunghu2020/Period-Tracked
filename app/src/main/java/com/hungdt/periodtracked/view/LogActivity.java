package com.hungdt.periodtracked.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.database.DBHelper;
import com.hungdt.periodtracked.model.Data;
import com.hungdt.periodtracked.model.Log;
import com.hungdt.periodtracked.utils.KEY;
import com.hungdt.periodtracked.view.adapter.LogAdapter;
import com.hungdt.periodtracked.view.fragment.ReportFragment;

import java.util.ArrayList;
import java.util.List;

import static com.hungdt.periodtracked.view.MainActivity.dataList;
import static com.hungdt.periodtracked.view.MainActivity.motions;
import static com.hungdt.periodtracked.view.MainActivity.ovulations;
import static com.hungdt.periodtracked.view.MainActivity.physics;
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

    TextView txtDayTitle;
    RecyclerView rcvMotion, rcvPhysic, rcvSymptom, rcvOvulation;
    Button btnSave;
    ImageView imgBack;

    int position = -1;
    boolean haveData = false;
     String curDay;

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
        txtDayTitle = findViewById(R.id.txtDayTitle);

        motionss.addAll(motions);
        symptomss.addAll(symptoms);
        physicss.addAll(physics);
        ovulationss.addAll(ovulations);
        for(int i = 0; i<motions.size();i++){
            android.util.Log.e("111", "onCreate: "+motions.get(i).isChecked());
        }for(int i = 0; i<symptoms.size();i++){
            android.util.Log.e("111", "onCreate: "+symptoms.get(i).isChecked());
        }for(int i = 0; i<physics.size();i++){
            android.util.Log.e("111", "onCreate: "+physics.get(i).isChecked());
        }for(int i = 0; i<ovulations.size();i++){
            android.util.Log.e("111", "onCreate: "+ovulations.get(i).isChecked());
        }

        curDay = getIntent().getStringExtra(KEY.CURDAY);
        txtDayTitle.setText(curDay);
        Data data = null;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getDay().equals(curDay)) {
                data = dataList.get(i);
                position = i;
                break;
            }
        }
        if (data != null) {
            haveData = true;
            String[] idsM = data.getIdMotion().split(" ");
            String[] idsS = data.getIdSymptom().split(" ");
            String[] idsP = data.getIdPhysic().split(" ");
            String[] idsO = data.getIdOvulation().split(" ");


            for (String s : idsM) {
                if (!s.trim().equals("")) {
                    motionss.get(Integer.parseInt(s)).setChecked(true);
                }
            }
            for (String s : idsS) {
                if (!s.trim().equals("")) {
                    symptomss.get(Integer.parseInt(s)).setChecked(true);
                }
            }
            for (String s : idsP) {
                if (!s.trim().equals("")) {
                    physicss.get(Integer.parseInt(s)).setChecked(true);
                }
            }
            for (String s : idsO) {
                if (!s.trim().equals("")) {
                    ovulationss.get(Integer.parseInt(s)).setChecked(true);
                }
            }
        }


        rcvMotion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        motionAdapter = new LogAdapter(this, motionss);
        rcvMotion.setAdapter(motionAdapter);

        rcvSymptom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        symptomAdapter = new LogAdapter(this, symptomss);
        rcvSymptom.setAdapter(symptomAdapter);

        rcvPhysic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        physicAdapter = new LogAdapter(this, physicss);
        rcvPhysic.setAdapter(physicAdapter);

        rcvOvulation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ovulationAdapter = new LogAdapter(this, ovulationss);
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
                String mId = "";
                String sId = "";
                String pId = "";
                String oId = "";
                for (int i = 0; i < motionss.size(); i++) {
                    if (motionss.get(i).isChecked()) {
                        String text = mId + " " + i;
                        mId = text;
                    }
                }
                for (int i = 0; i < symptomss.size(); i++) {
                    if (symptomss.get(i).isChecked()) {
                        String text = sId + " " + i;
                        sId = text;
                    }
                }
                for (int i = 0; i < physicss.size(); i++) {
                    if (physicss.get(i).isChecked()) {
                        String text = pId + " " + i;
                        pId = text;
                    }
                }
                for (int i = 0; i < ovulationss.size(); i++) {
                    if (ovulationss.get(i).isChecked()) {
                        String text = oId + " " + i;
                        oId = text;
                    }
                }
                if (position == -1) {
                    dataList.add(new Data("id", curDay, "type", mId.trim(), sId.trim(), pId.trim(), oId.trim()));
                    DBHelper.getInstance(LogActivity.this).addPeriodData(curDay, "type", mId.trim(), sId.trim(), pId.trim(), oId.trim());
                    position = dataList.size() - 1;
                } else {
                    dataList.get(position).setIdMotion(mId.trim());
                    dataList.get(position).setIdSymptom(sId.trim());
                    dataList.get(position).setIdPhysic(pId.trim());
                    dataList.get(position).setIdOvulation(oId.trim());
                    DBHelper.getInstance(LogActivity.this).updatePeriod(curDay, "type", mId.trim(), sId.trim(), pId.trim(), oId.trim());
                }


                Toast.makeText(LogActivity.this, "Save success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReportFragment.ACTION_UPDATE_LOG);
                intent.putExtra(KEY.POSITION, position);
                sendBroadcast(intent);
            }
        });


    }

}
