package com.hungdt.periodtracked.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hungdt.periodtracked.view.fragment.TodayFragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hungdt.periodtracked.view.MainActivity.dataList;
import static com.unity3d.services.core.properties.ClientProperties.getActivity;

public class LogActivity extends AppCompatActivity {
    LogAdapter motionAdapter, symptomAdapter, physicAdapter, ovulationAdapter;
    List<Log> motionList = new ArrayList<>();
    List<Log> symptomList = new ArrayList<>();
    List<Log> physicList = new ArrayList<>();
    List<Log> ovulationList = new ArrayList<>();

    TextView txtDayTitle, txtWeight, txtSleep, txtWater;
    RecyclerView rcvMotion, rcvPhysic, rcvSymptom, rcvOvulation;
    LinearLayout llWeight, llSleep, llWater, llWeightInput, llSleepInput, llWaterInput, llUp;
    Button btnSave;
    ImageView imgBack;
    EditText edtWeight, edtHour, edtMinutes, edtLit;
    boolean inputWeightVisible = false;
    boolean inputSleepVisible = false;
    boolean inputWatertVisible = false;

    int position = -1;
    boolean haveData = false;
    String curDay;

    boolean haveSleep=false;
    private float kilogram = -1;
    private int hour = 0;
    private int minute = -1;
    private float lit = -1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();

        llWeightInput.setVisibility(View.GONE);
        llSleepInput.setVisibility(View.GONE);
        llWaterInput.setVisibility(View.GONE);
        llUp.setVisibility(View.GONE);

        motionList.add(new Log(R.drawable.calm, R.string.motion01, false));
        motionList.add(new Log(R.drawable.happy, R.string.motion02, false));
        motionList.add(new Log(R.drawable.energetic, R.string.motion03, false));
        motionList.add(new Log(R.drawable.frisky, R.string.motion04, false));
        motionList.add(new Log(R.drawable.mood_swing, R.string.motion05, false));
        motionList.add(new Log(R.drawable.irritated, R.string.motion06, false));
        motionList.add(new Log(R.drawable.sad, R.string.motion07, false));
        motionList.add(new Log(R.drawable.anxious, R.string.motion08, false));
        motionList.add(new Log(R.drawable.depressed, R.string.motion09, false));
        motionList.add(new Log(R.drawable.feeling_guilty, R.string.motion10, false));
        motionList.add(new Log(R.drawable.obsessive_thoughts, R.string.motion11, false));
        motionList.add(new Log(R.drawable.apathetic, R.string.motion12, false));
        motionList.add(new Log(R.drawable.confused, R.string.motion13, false));

        symptomList.add(new Log(R.drawable.fine, R.string.symptom01, false));
        symptomList.add(new Log(R.drawable.cramps, R.string.symptom02, false));
        symptomList.add(new Log(R.drawable.tender_breast, R.string.symptom03, false));
        symptomList.add(new Log(R.drawable.headache, R.string.symptom04, false));
        symptomList.add(new Log(R.drawable.acne, R.string.symptom05, false));
        symptomList.add(new Log(R.drawable.backache, R.string.symptom06, false));
        symptomList.add(new Log(R.drawable.nausea, R.string.symptom07, false));
        symptomList.add(new Log(R.drawable.fatigue, R.string.symptom08, false));
        symptomList.add(new Log(R.drawable.craving, R.string.symptom09, false));
        symptomList.add(new Log(R.drawable.insomnia, R.string.symptom10, false));
        symptomList.add(new Log(R.drawable.constipation, R.string.symptom11, false));
        symptomList.add(new Log(R.drawable.diarrhea, R.string.symptom12, false));
        symptomList.add(new Log(R.drawable.abdominal_pain, R.string.symptom13, false));
        symptomList.add(new Log(R.drawable.perineum_pain, R.string.symptom14, false));
        symptomList.add(new Log(R.drawable.swelling, R.string.symptom15, false));

        physicList.add(new Log(R.drawable.didnt_excercise, R.string.physic01, false));
        physicList.add(new Log(R.drawable.running, R.string.physic02, false));
        physicList.add(new Log(R.drawable.cycling, R.string.physic03, false));
        physicList.add(new Log(R.drawable.gym, R.string.physic04, false));
        physicList.add(new Log(R.drawable.yoga, R.string.physic05, false));
        physicList.add(new Log(R.drawable.team_sport, R.string.physic06, false));
        physicList.add(new Log(R.drawable.swimming, R.string.physic07, false));

        ovulationList.add(new Log(R.drawable.didnot_take_test, R.string.ovulation1, false));
        ovulationList.add(new Log(R.drawable.positive, R.string.ovulation2, false));
        ovulationList.add(new Log(R.drawable.negative, R.string.ovulation3, false));
        ovulationList.add(new Log(R.drawable.ovulation_my_method, R.string.ovulation4, false));


        @SuppressLint("SimpleDateFormat") DateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        @SuppressLint("SimpleDateFormat") DateFormat sdfMonth = new SimpleDateFormat("MM");
        @SuppressLint("SimpleDateFormat") DateFormat sdfDay = new SimpleDateFormat("dd");
        @SuppressLint("SimpleDateFormat") DateFormat sdfYear = new SimpleDateFormat("yyyy");
        curDay = getIntent().getStringExtra(KEY.CURDAY);
        Date date = new Date();
        try {
            date = sdfDate.parse(curDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        switch (Integer.parseInt(sdfMonth.format(date))) {
            case 1:
                txtDayTitle.setText(getResources().getString(R.string.january) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 2:
                txtDayTitle.setText(getResources().getString(R.string.february) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 3:
                txtDayTitle.setText(getResources().getString(R.string.march) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 4:
                txtDayTitle.setText(getResources().getString(R.string.april) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 5:
                txtDayTitle.setText(getResources().getString(R.string.may) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 6:
                txtDayTitle.setText(getResources().getString(R.string.june) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 7:
                txtDayTitle.setText(getResources().getString(R.string.july) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 8:
                txtDayTitle.setText(getResources().getString(R.string.august) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 9:
                txtDayTitle.setText(getResources().getString(R.string.september) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 10:
                txtDayTitle.setText(getResources().getString(R.string.october) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 11:
                txtDayTitle.setText(getResources().getString(R.string.november) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            case 12:
                txtDayTitle.setText(getResources().getString(R.string.december) + " " + sdfDay.format(date) + ", " + sdfYear.format(date));
                break;
            default:
                break;
        }
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
            kilogram = data.getWeight();
            hour = data.getHour();
            minute = data.getMinutes();
            lit = data.getWater();
            String[] idsM = data.getIdMotion().split(" ");
            String[] idsS = data.getIdSymptom().split(" ");
            String[] idsP = data.getIdPhysic().split(" ");
            String[] idsO = data.getIdOvulation().split(" ");
            for (String s : idsM) {
                if (!s.trim().equals("")) {
                    motionList.get(Integer.parseInt(s)).setChecked(true);
                }
            }
            for (String s : idsS) {
                if (!s.trim().equals("")) {
                    symptomList.get(Integer.parseInt(s)).setChecked(true);
                }
            }
            for (String s : idsP) {
                if (!s.trim().equals("")) {
                    physicList.get(Integer.parseInt(s)).setChecked(true);
                }
            }
            for (String s : idsO) {
                if (!s.trim().equals("")) {
                    ovulationList.get(Integer.parseInt(s)).setChecked(true);
                }
            }
        }
        if (kilogram == -1) {
            txtWeight.setText("-- kg");
        } else {
            txtWeight.setText(kilogram + " kg");
        }

        if (minute == -1) {
            txtSleep.setText("--h--m");
        } else {
            txtSleep.setText(hour + "h" + minute + "m");
        }
        if (lit == -1) {
            txtWater.setText("-- L");
        } else {
            txtWater.setText(lit + " L");
        }


        rcvMotion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        motionAdapter = new LogAdapter(this, motionList);
        rcvMotion.setAdapter(motionAdapter);

        rcvSymptom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        symptomAdapter = new LogAdapter(this, symptomList);
        rcvSymptom.setAdapter(symptomAdapter);

        rcvPhysic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        physicAdapter = new LogAdapter(this, physicList);
        rcvPhysic.setAdapter(physicAdapter);

        rcvOvulation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ovulationAdapter = new LogAdapter(this, ovulationList);
        rcvOvulation.setAdapter(ovulationAdapter);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mId = "";
                String sId = "";
                String pId = "";
                String oId = "";
                for (int i = 0; i < motionList.size(); i++) {
                    if (motionList.get(i).isChecked()) {
                        String text = mId + " " + i;
                        mId = text;
                    }
                }
                for (int i = 0; i < symptomList.size(); i++) {
                    if (symptomList.get(i).isChecked()) {
                        String text = sId + " " + i;
                        sId = text;
                    }
                }
                for (int i = 0; i < physicList.size(); i++) {
                    if (physicList.get(i).isChecked()) {
                        String text = pId + " " + i;
                        pId = text;
                    }
                }
                for (int i = 0; i < ovulationList.size(); i++) {
                    if (ovulationList.get(i).isChecked()) {
                        String text = oId + " " + i;
                        oId = text;
                    }
                }

                if (position == -1) {
                    dataList.add(new Data("id", curDay, "type", mId.trim(), sId.trim(), pId.trim(), oId.trim(), kilogram, hour, minute, lit));
                    DBHelper.getInstance(LogActivity.this).addPeriodData(curDay, "type", mId.trim(), sId.trim(), pId.trim(), oId.trim(), kilogram, hour, minute, lit);
                    position = dataList.size() - 1;
                } else {
                    dataList.get(position).setIdMotion(mId.trim());
                    dataList.get(position).setIdSymptom(sId.trim());
                    dataList.get(position).setIdPhysic(pId.trim());
                    dataList.get(position).setIdOvulation(oId.trim());
                    DBHelper.getInstance(LogActivity.this).updatePeriod(curDay, "type", mId.trim(), sId.trim(), pId.trim(), oId.trim(), kilogram, hour, minute, lit);
                }


                Toast.makeText(LogActivity.this, "Save success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TodayFragment.ACTION_UPDATE_LOG);
                intent.putExtra(KEY.POSITION, position);
                sendBroadcast(intent);
                onBackPressed();


            }
        });

        llWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputWeightVisible) {
                    inputWeightVisible = false;
                    llWeightInput.setVisibility(View.GONE);
                    llUp.setVisibility(View.GONE);
                } else {
                    inputWeightVisible = true;
                    llWeightInput.setVisibility(View.VISIBLE);
                    if (kilogram != -1) {
                        edtWeight.setText("" + kilogram);
                    }
                    llUp.setVisibility(View.VISIBLE);
                }
                if (inputSleepVisible) {
                    inputSleepVisible = false;
                    llSleepInput.setVisibility(View.GONE);
                }
                if (inputWatertVisible) {
                    inputWatertVisible = false;
                    llWaterInput.setVisibility(View.GONE);
                }

            }
        });


        llSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputSleepVisible) {
                    inputSleepVisible = false;
                    llSleepInput.setVisibility(View.GONE);
                    llUp.setVisibility(View.GONE);
                } else {
                    inputSleepVisible = true;
                    llSleepInput.setVisibility(View.VISIBLE);
                    if (minute != -1) {
                        edtHour.setText("" + hour);
                        edtMinutes.setText("" + minute);
                    }
                    llUp.setVisibility(View.VISIBLE);
                }
                if (inputWeightVisible) {
                    inputWeightVisible = false;
                    llWeightInput.setVisibility(View.GONE);
                }
                if (inputWatertVisible) {
                    inputWatertVisible = false;
                    llWaterInput.setVisibility(View.GONE);
                }
            }
        });

        llWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputWatertVisible) {
                    inputWatertVisible = false;
                    llWaterInput.setVisibility(View.GONE);
                    llUp.setVisibility(View.GONE);
                } else {
                    inputWatertVisible = true;
                    llWaterInput.setVisibility(View.VISIBLE);
                    if (lit != -1) {
                        edtLit.setText("" + lit);
                    }
                    llUp.setVisibility(View.VISIBLE);
                }
                if (inputWeightVisible) {
                    inputWeightVisible = false;
                    llWeightInput.setVisibility(View.GONE);
                }
                if (inputSleepVisible) {
                    inputSleepVisible = false;
                    llSleepInput.setVisibility(View.GONE);
                }
            }
        });
        llUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llUp.setVisibility(View.GONE);
                if (inputWeightVisible) {
                    inputWeightVisible = false;
                    llWeightInput.setVisibility(View.GONE);
                }
                if (inputSleepVisible) {
                    inputSleepVisible = false;
                    llSleepInput.setVisibility(View.GONE);
                }
                if (inputWatertVisible) {
                    inputWatertVisible = false;
                    llWaterInput.setVisibility(View.GONE);
                }
            }
        });


        edtWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    kilogram = Float.parseFloat(String.valueOf(s));
                } else {
                    kilogram = -1;
                }
                if(kilogram ==-1){
                    txtWeight.setText("-- kg");
                }else {
                    txtWeight.setText(kilogram + " kg");
                }
            }
        });
        edtHour.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    hour = Integer.parseInt(String.valueOf(s));
                } else {
                    hour = 0;
                }
                if(minute==-1){
                    if(hour==0){
                        txtSleep.setText(  "--h--m");
                    }else {
                        minute = 0;
                        txtSleep.setText(hour + "h" + minute + "m");
                    }
                }else {
                    txtSleep.setText(hour + "h" + minute + "m");
                }


            }
        });
        edtMinutes.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    haveSleep=true;
                    minute = Integer.parseInt(String.valueOf(s));
                } else {
                    minute = -1;
                }

                if(minute==-1){
                    if(hour==0){
                        txtSleep.setText(  "--h--m");
                    }else {
                        minute=0;
                        txtSleep.setText(hour + "h" + minute + "m");
                    }
                }else {
                    txtSleep.setText(hour + "h" + minute + "m");
                }

            }
        });
        edtLit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    lit = Float.parseFloat(String.valueOf(s));
                } else {
                    lit = -1;
                }
                if(lit==-1){
                    txtWater.setText("-- L");
                }else {
                    txtWater.setText(lit + " L");
                }

            }
        });

        KeyboardVisibilityEvent.setEventListener(
                LogActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            btnSave.setVisibility(View.GONE);
                        } else {
                            btnSave.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        rcvMotion = findViewById(R.id.rcvMotion);
        rcvSymptom = findViewById(R.id.rcvSymptom);
        rcvPhysic = findViewById(R.id.rcvPhysic);
        rcvOvulation = findViewById(R.id.rcvOvulation);
        btnSave = findViewById(R.id.btnSave);
        txtDayTitle = findViewById(R.id.txtDayTitle);
        txtWeight = findViewById(R.id.txtWeight);
        txtSleep = findViewById(R.id.txtSleep);
        txtWater = findViewById(R.id.txtWater);
        llWeight = findViewById(R.id.llWeight);
        llSleep = findViewById(R.id.llSleep);
        llWater = findViewById(R.id.llWater);
        llWeightInput = findViewById(R.id.llWeightInput);
        edtWeight = findViewById(R.id.edtWeight);
        llUp = findViewById(R.id.llUp);
        llSleepInput = findViewById(R.id.llSleepInput);
        llWaterInput = findViewById(R.id.llWaterInput);
        edtHour = findViewById(R.id.edtHour);
        edtMinutes = findViewById(R.id.edtMinutes);
        edtLit = findViewById(R.id.edtLit);
    }
}
