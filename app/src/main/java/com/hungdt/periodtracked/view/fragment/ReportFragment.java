package com.hungdt.periodtracked.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Data;
import com.hungdt.periodtracked.model.Report;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.adapter.ReportAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.hungdt.periodtracked.view.MainActivity.dataList;

@SuppressLint("SimpleDateFormat")
public class ReportFragment extends Fragment {

    public static final String ACTION_UPDATE_REPORT_FRAGMENT = "update report";
    private String beginCircleDay;
    private int periodCircle;
    private String motionIds = "";
    private String symptomIds = "";
    private String physicIds = "";
    private String ovulationIds = "";
    int[] cM = new int[13];
    int[] cS = new int[15];
    int[] cP = new int[7];
    int[] cO = new int[4];
    private List<Report> motions = new ArrayList<>();
    private List<Report> symptoms = new ArrayList<>();
    private List<Report> physics = new ArrayList<>();
    private List<Report> ovulations = new ArrayList<>();
    private ReportAdapter motionAdapter, symptomAdapter, physicAdapter, ovulationAdapter;
    private RecyclerView rcvMotion, rcvSymptom, rcvPhysic, rcvOvulation;
    private TextView txtWeight, txtSleep, txtWater;
    float weight = -1;
    float times = -1;
    float water = -1;

    SimpleDateFormat sdfMyDate = new SimpleDateFormat("dd-MM-yyyy");

    public ReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvMotion = view.findViewById(R.id.rcvMoodR);
        rcvSymptom = view.findViewById(R.id.rcvSymptomR);
        rcvPhysic = view.findViewById(R.id.rcvPhysicR);
        rcvOvulation = view.findViewById(R.id.rcvOvulationR);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtSleep = view.findViewById(R.id.txtSleep);
        txtWater = view.findViewById(R.id.txtWater);
        setupData();

        rcvMotion.setLayoutManager(new LinearLayoutManager(getActivity()));
        motionAdapter = new ReportAdapter(getActivity(), motions);
        rcvMotion.setAdapter(motionAdapter);

        rcvSymptom.setLayoutManager(new LinearLayoutManager(getActivity()));
        symptomAdapter = new ReportAdapter(getActivity(), symptoms);
        rcvSymptom.setAdapter(symptomAdapter);

        rcvPhysic.setLayoutManager(new LinearLayoutManager(getActivity()));
        physicAdapter = new ReportAdapter(getActivity(), physics);
        rcvPhysic.setAdapter(physicAdapter);

        rcvOvulation.setLayoutManager(new LinearLayoutManager(getActivity()));
        ovulationAdapter = new ReportAdapter(getActivity(), ovulations);
        rcvOvulation.setAdapter(ovulationAdapter);

        IntentFilter intentFilter = new IntentFilter(ACTION_UPDATE_REPORT_FRAGMENT);
        getContext().registerReceiver(broadcastUpdateReport, intentFilter);
    }

    private void setupData() {
        beginCircleDay = MySetting.getBeginCycle(getActivity());
        periodCircle = MySetting.getPeriodCircle(getActivity());
        if (!beginCircleDay.equals(getString(R.string.not_sure))) {
            Arrays.fill(cM, 0);
            Arrays.fill(cS, 0);
            Arrays.fill(cP, 0);
            Arrays.fill(cO, 0);
            Date beginCircleDate = null;

            try {
                beginCircleDate = sdfMyDate.parse(beginCircleDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("123123", "(0)beginCircleDate: " + beginCircleDate);

            for (int i = 0; i < dataList.size(); i++) {
                Data data = dataList.get(i);
                Date date = null;
                try {
                    date = sdfMyDate.parse(data.getDay());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("123123", "(1)id: " + data.getIdMotion() + " / date: " + date);
                if (!date.before(beginCircleDate)) {
                    if (data.getWeight() != -1) {
                        if (weight == -1) {
                            weight = data.getWeight();
                        } else {
                            weight = (weight + data.getWeight()) / 2;
                        }

                    }
                    if (data.getMinutes() != -1) {
                        if (times == -1) {
                            times = data.getHour() * 60 + data.getMinutes();
                        } else {
                            times = (times + data.getHour() * 60 + data.getMinutes()) / 2;
                        }

                    }

                    if (data.getWater() != -1) {
                        if (water == -1) {
                            water = data.getWater();
                        } else {
                            water = (water + data.getWater()) / 2;
                        }

                    }
                    if (data.getIdMotion() != null) {
                        motionIds = data.getIdMotion() + " " + motionIds;
                    }
                    if (data.getIdSymptom() != null) {
                        symptomIds = data.getIdSymptom() + " " + symptomIds;
                    }
                    if (data.getIdPhysic() != null) {
                        physicIds = data.getIdPhysic() + " " + physicIds;
                    }
                    if (data.getIdOvulation() != null) {
                        ovulationIds = data.getIdOvulation() + " " + ovulationIds;
                    }


                    Log.e("123123", "(2)getDay: " + data.getDay() + "/" + beginCircleDay);
                }
            }
            Log.e("123123", "(3)motionIds: " + motionIds + "/" + symptomIds + "/" + physicIds + "/" + ovulationIds);

            int hours = (int) times / 60;
            int minutes = (int) times - hours * 60;


            if (weight != -1) {
                txtWeight.setText(weight + " kg");
            }
            if (times != -1) {
                txtSleep.setText(hours + "h" + minutes + "m");
            }
            if (water != -1) {
                txtWater.setText(water + " L");
            }


            String[] idsM = motionIds.split(" ");
            String[] idsS = symptomIds.split(" ");
            String[] idsP = physicIds.split(" ");
            String[] idsO = ovulationIds.split(" ");

            for (String s : idsM) {
                if (!s.trim().equals("")) {
                    switch (Integer.parseInt(s.trim())) {
                        case 0:
                            cM[0]++;
                            break;
                        case 1:
                            cM[1]++;
                            break;
                        case 2:
                            cM[2]++;
                            break;
                        case 3:
                            cM[3]++;
                            break;
                        case 4:
                            cM[4]++;
                            break;
                        case 5:
                            cM[5]++;
                            break;
                        case 6:
                            cM[6]++;
                            break;
                        case 7:
                            cM[7]++;
                            break;
                        case 8:
                            cM[8]++;
                            break;
                        case 9:
                            cM[9]++;
                            break;
                        case 10:
                            cM[10]++;
                            break;
                        case 11:
                            cM[11]++;
                            break;
                        case 12:
                            cM[12]++;
                            break;
                    }
                }
            }
            for (String s : idsS) {
                if (!s.trim().equals("")) {
                    switch (Integer.parseInt(s.trim())) {
                        case 0:
                            cS[0]++;
                            break;
                        case 1:
                            cS[1]++;
                            break;
                        case 2:
                            cS[2]++;
                            break;
                        case 3:
                            cS[3]++;
                            break;
                        case 4:
                            cS[4]++;
                            break;
                        case 5:
                            cS[5]++;
                            break;
                        case 6:
                            cS[6]++;
                            break;
                        case 7:
                            cS[7]++;
                            break;
                        case 8:
                            cS[8]++;
                            break;
                        case 9:
                            cS[9]++;
                            break;
                        case 10:
                            cS[10]++;
                            break;
                        case 11:
                            cS[11]++;
                            break;
                        case 12:
                            cS[12]++;
                            break;
                        case 13:
                            cS[13]++;
                            break;
                        case 14:
                            cS[14]++;
                            break;
                    }
                }
            }
            for (String s : idsP) {
                if (!s.trim().equals("")) {
                    switch (Integer.parseInt(s.trim())) {
                        case 0:
                            cP[0]++;
                            break;
                        case 1:
                            cP[1]++;
                            break;
                        case 2:
                            cP[2]++;
                            break;
                        case 3:
                            cP[3]++;
                            break;
                        case 4:
                            cP[4]++;
                            break;
                        case 5:
                            cP[5]++;
                            break;
                        case 6:
                            cP[6]++;
                            break;
                    }
                }
            }
            for (String s : idsO) {
                if (!s.trim().equals("")) {
                    switch (Integer.parseInt(s.trim())) {
                        case 0:
                            cO[0]++;
                            break;
                        case 1:
                            cO[1]++;
                            break;
                        case 2:
                            cO[2]++;
                            break;
                        case 3:
                            cO[3]++;
                            break;
                    }
                }
            }
            for (int i = 0; i < cM.length; i++) {
                if (cM[i] > 0) {
                    switch (i) {
                        case 0:
                            motions.add(new Report(R.drawable.calm, getResources().getString(R.string.motion01), cM[i] * 100 / periodCircle));
                            break;
                        case 1:
                            motions.add(new Report(R.drawable.happy, getResources().getString(R.string.motion02), cM[i] * 100 / periodCircle));
                            break;
                        case 2:
                            motions.add(new Report(R.drawable.energetic, getResources().getString(R.string.motion03), cM[i] * 100 / periodCircle));
                            break;
                        case 3:
                            motions.add(new Report(R.drawable.frisky, getResources().getString(R.string.motion04), cM[i] * 100 / periodCircle));
                            break;
                        case 4:
                            motions.add(new Report(R.drawable.mood_swing, getResources().getString(R.string.motion05), cM[i] * 100 / periodCircle));
                            break;
                        case 5:
                            motions.add(new Report(R.drawable.irritated, getResources().getString(R.string.motion06), cM[i] * 100 / periodCircle));
                            break;
                        case 6:
                            motions.add(new Report(R.drawable.sad, getResources().getString(R.string.motion07), cM[i] * 100 / periodCircle));
                            break;
                        case 7:
                            motions.add(new Report(R.drawable.anxious, getResources().getString(R.string.motion08), cM[i] * 100 / periodCircle));
                            break;
                        case 8:
                            motions.add(new Report(R.drawable.depressed, getResources().getString(R.string.motion09), cM[i] * 100 / periodCircle));
                            break;
                        case 9:
                            motions.add(new Report(R.drawable.feeling_guilty, getResources().getString(R.string.motion10), cM[i] * 100 / periodCircle));
                            break;
                        case 10:
                            motions.add(new Report(R.drawable.obsessive_thoughts, getResources().getString(R.string.motion11), cM[i] * 100 / periodCircle));
                            break;
                        case 11:
                            motions.add(new Report(R.drawable.apathetic, getResources().getString(R.string.motion12), cM[i] * 100 / periodCircle));
                            break;
                        case 12:
                            motions.add(new Report(R.drawable.confused, getResources().getString(R.string.motion13), (cM[i] * 100 / periodCircle)));
                            break;
                    }
                }
            }
            for (int i = 0; i < cS.length; i++) {

                if (cS[i] > 0) {
                    switch (i) {
                        case 0:
                            symptoms.add(new Report(R.drawable.fine, getResources().getString(R.string.symptom01), cS[i] * 100 / periodCircle));
                            break;
                        case 1:
                            symptoms.add(new Report(R.drawable.cramps, getResources().getString(R.string.symptom02), cS[i] * 100 / periodCircle));
                            break;
                        case 2:
                            symptoms.add(new Report(R.drawable.tender_breast, getResources().getString(R.string.symptom03), cS[i] * 100 / periodCircle));
                            break;
                        case 3:
                            symptoms.add(new Report(R.drawable.headache, getResources().getString(R.string.symptom04), cS[i] * 100 / periodCircle));
                            break;
                        case 4:
                            symptoms.add(new Report(R.drawable.acne, getResources().getString(R.string.symptom05), cS[i] * 100 / periodCircle));
                            break;
                        case 5:
                            symptoms.add(new Report(R.drawable.backache, getResources().getString(R.string.symptom06), cS[i] * 100 / periodCircle));
                            break;
                        case 6:
                            symptoms.add(new Report(R.drawable.nausea, getResources().getString(R.string.symptom07), cS[i] * 100 / periodCircle));
                            break;
                        case 7:
                            symptoms.add(new Report(R.drawable.fatigue, getResources().getString(R.string.symptom08), cS[i] * 100 / periodCircle));
                            break;
                        case 8:
                            symptoms.add(new Report(R.drawable.craving, getResources().getString(R.string.symptom09), cS[i] * 100 / periodCircle));
                            break;
                        case 9:
                            symptoms.add(new Report(R.drawable.insomnia, getResources().getString(R.string.symptom10), cS[i] * 100 / periodCircle));
                            break;
                        case 10:
                            symptoms.add(new Report(R.drawable.constipation, getResources().getString(R.string.symptom11), cS[i] * 100 / periodCircle));
                            break;
                        case 11:
                            symptoms.add(new Report(R.drawable.diarrhea, getResources().getString(R.string.symptom12), cS[i] * 100 / periodCircle));
                            break;
                        case 12:
                            symptoms.add(new Report(R.drawable.abdominal_pain, getResources().getString(R.string.symptom13), cS[i] * 100 / periodCircle));
                            break;
                        case 13:
                            symptoms.add(new Report(R.drawable.perineum_pain, getResources().getString(R.string.symptom14), cS[i] * 100 / periodCircle));
                            break;
                        case 14:
                            symptoms.add(new Report(R.drawable.swelling, getResources().getString(R.string.symptom15), cS[i] * 100 / periodCircle));
                            break;
                    }
                }
            }
            for (int i = 0; i < cP.length; i++) {
                if (cP[i] > 0) {
                    switch (i) {
                        case 0:
                            physics.add(new Report(R.drawable.didnt_excercise, getResources().getString(R.string.physic01), cP[i] * 100 / periodCircle));
                            break;
                        case 1:
                            physics.add(new Report(R.drawable.running, getResources().getString(R.string.physic02), cP[i] * 100 / periodCircle));
                            break;
                        case 2:
                            physics.add(new Report(R.drawable.cycling, getResources().getString(R.string.physic03), cP[i] * 100 / periodCircle));
                            break;
                        case 3:
                            physics.add(new Report(R.drawable.gym, getResources().getString(R.string.physic04), cP[i] * 100 / periodCircle));
                            break;
                        case 4:
                            physics.add(new Report(R.drawable.yoga, getResources().getString(R.string.physic05), cP[i] * 100 / periodCircle));
                            break;
                        case 5:
                            physics.add(new Report(R.drawable.team_sport, getResources().getString(R.string.physic06), cP[i] * 100 / periodCircle));
                            break;
                        case 6:
                            physics.add(new Report(R.drawable.swimming, getResources().getString(R.string.physic07), cP[i] * 100 / periodCircle));
                            break;
                    }
                }
            }
            for (int i = 0; i < cO.length; i++) {
                if (cO[i] > 0) {
                    switch (i) {
                        case 0:
                            ovulations.add(new Report(R.drawable.didnot_take_test, getResources().getString(R.string.ovulation1), cO[i] * 100 / periodCircle));
                            break;
                        case 1:
                            ovulations.add(new Report(R.drawable.positive, getResources().getString(R.string.ovulation2), cO[i] * 100 / periodCircle));
                            break;
                        case 2:
                            ovulations.add(new Report(R.drawable.negative, getResources().getString(R.string.ovulation3), cO[i] * 100 / periodCircle));
                            break;
                        case 3:
                            ovulations.add(new Report(R.drawable.ovulation_my_method, getResources().getString(R.string.ovulation4), cO[i] * 100 / periodCircle));
                            break;
                    }
                }
            }
            Log.e("123123", "(4)motions: " + motions.size() + " " + symptoms.size() + " " + physics.size() + " " + ovulations.size());

        }

        if (motions.size() == 0)
            motions.add(new Report(R.drawable.zo, getString(R.string.no_mood_yet), 0));
        if (symptoms.size() == 0)
            symptoms.add(new Report(R.drawable.ic_no_record_symptom, getString(R.string.no_symptom_yet), 0));
        if (physics.size() == 0)
            physics.add(new Report(R.drawable.ic_no_record_exercise, getString(R.string.no_physic_yet), 0));
        if (ovulations.size() == 0)
            ovulations.add(new Report(R.drawable.ic_no_record_ovulation, getString(R.string.no_ovulation_yet), 0));
    }

    private final BroadcastReceiver broadcastUpdateReport = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setupData();
            motionAdapter.notifyDataSetChanged();
            symptomAdapter.notifyDataSetChanged();
            physicAdapter.notifyDataSetChanged();
            ovulationAdapter.notifyDataSetChanged();
        }
    };
}