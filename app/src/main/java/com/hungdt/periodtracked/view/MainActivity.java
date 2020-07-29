package com.hungdt.periodtracked.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.material.navigation.NavigationView;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.database.DBHelper;
import com.hungdt.periodtracked.model.Data;
import com.hungdt.periodtracked.model.Log;
import com.hungdt.periodtracked.utils.Helper;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.fragment.PaperFragment;
import com.hungdt.periodtracked.view.fragment.TodayFragment;
import com.hungdt.periodtracked.view.fragment.ReportFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    private ImageView imgMenu;
    private DrawerLayout drawerLayout;
    private ConstraintLayout clToday, clReport, clInSight;
    private ImageView imgTodayOn, imgTodayOff, imgReportOn, imgReportOff, imgInsightOn, imgInsightOff;
    private TextView txtToday, txtReport, txtInsight;
    private boolean readyToPurchase = false;
    private BillingProcessor bp;
    public static List<Data> dataList = new ArrayList<>();
    public static List<Log> motions = new ArrayList<>();
    public static List<Log> symptoms = new ArrayList<>();
    public static List<Log> physics = new ArrayList<>();
    public static List<Log> ovulations = new ArrayList<>();
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            bp = BillingProcessor.newBillingProcessor(this, getString(R.string.BASE64), this); // doesn't bind
            bp.initialize(); // binds
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        /*if (ContactConfig.getInstance().getConfig().getBoolean("config_on")) {
            Ads.initNativeGg((LinearLayout) hView.findViewById(R.id.lnNative), this, true, true);
        }*/

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) drawerLayout.openDrawer(Gravity.LEFT);
                else drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_remove_add:
                        try {
                            removeAds();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.nav_rate_us:
                        startActivity(new Intent(MainActivity.this, RateAppActivity.class));
                        break;
                    case R.id.nav_more_app:
                        startActivity(new Intent(MainActivity.this, MoreAppActivity.class));
                        break;
                    case R.id.nav_feedback_dev:
                        Helper.feedback(MainActivity.this);
                        break;
                    case R.id.nav_share:
                        Helper.shareApp(MainActivity.this);
                        break;
                    case R.id.nav_VIP:
                        try {
                            startActivity(new Intent(MainActivity.this, VipActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.nav_policy:
                        startActivity(new Intent(MainActivity.this, PolicyActivity.class));
                        /*if (!showInterstitial()) {
                            if (UnityAds.isInitialized() && UnityAds.isReady(getString(R.string.INTER_UNI)))
                                UnityAds.show(MainActivity.this, getString(R.string.INTER_UNI));
                        }*/
                        break;
                    case R.id.nav_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

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


        imgTodayOff.setVisibility(View.INVISIBLE);
        txtToday.setTextColor(getResources().getColor(R.color.violet));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TodayFragment()).commit();

        clToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new TodayFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                imgTodayOn.setVisibility(View.VISIBLE);
                imgTodayOff.setVisibility(View.INVISIBLE);
                imgReportOff.setVisibility(View.VISIBLE);
                imgReportOn.setVisibility(View.INVISIBLE);
                imgInsightOff.setVisibility(View.VISIBLE);
                imgInsightOn.setVisibility(View.INVISIBLE);
                txtToday.setTextColor(getResources().getColor(R.color.violet));
                txtReport.setTextColor(getResources().getColor(R.color.gray));
                txtInsight.setTextColor(getResources().getColor(R.color.gray));
            }
        });
        clReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new ReportFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                imgReportOn.setVisibility(View.VISIBLE);
                imgReportOff.setVisibility(View.INVISIBLE);
                imgTodayOff.setVisibility(View.VISIBLE);
                imgTodayOn.setVisibility(View.INVISIBLE);
                imgInsightOff.setVisibility(View.VISIBLE);
                imgInsightOn.setVisibility(View.INVISIBLE);
                txtToday.setTextColor(getResources().getColor(R.color.gray));
                txtReport.setTextColor(getResources().getColor(R.color.violet));
                txtInsight.setTextColor(getResources().getColor(R.color.gray));
            }
        });
        clInSight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new PaperFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                imgTodayOn.setVisibility(View.INVISIBLE);
                imgTodayOff.setVisibility(View.VISIBLE);
                imgReportOff.setVisibility(View.VISIBLE);
                imgReportOn.setVisibility(View.INVISIBLE);
                imgInsightOff.setVisibility(View.INVISIBLE);
                imgInsightOn.setVisibility(View.VISIBLE);
                txtToday.setTextColor(getResources().getColor(R.color.gray));
                txtReport.setTextColor(getResources().getColor(R.color.gray));
                txtInsight.setTextColor(getResources().getColor(R.color.violet));
            }
        });

    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        drawerLayout = findViewById(R.id.draw_layout);
        clToday = findViewById(R.id.clToday);
        clReport = findViewById(R.id.clReport);
        clInSight = findViewById(R.id.clInSight);
        imgTodayOn = findViewById(R.id.imgTodayOn);
        imgTodayOff = findViewById(R.id.imgTodayOff);
        imgReportOn = findViewById(R.id.imgReportOn);
        imgReportOff = findViewById(R.id.imgReportOff);
        imgInsightOn = findViewById(R.id.imgInsightOn);
        imgInsightOff = findViewById(R.id.imgInsightOff);
        txtToday = findViewById(R.id.txtToday);
        txtReport = findViewById(R.id.txtReport);
        txtInsight = findViewById(R.id.txtInsight);
    }

    private void removeAds() {
        try {
            if (readyToPurchase) {
                bp.subscribe(this, getString(R.string.ID_REMOVE_ADS));
            } else {
                Toast.makeText(this, "Billing not initialized", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(this, "Thank you for your purchased!", Toast.LENGTH_SHORT).show();
        if (productId.equals(getString(R.string.ID_REMOVE_ADS))) {
            checkRemoveAds();
        } else if (productId.equals(getString(R.string.ID_SUBSCRIPTION))) {
            MySetting.setSubscription(this, true);
            MySetting.putRemoveAds(this, true);
        }
        Toast.makeText(this, "Thanks for your Purchased!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "You have declined payment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {
        readyToPurchase = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bp != null) {
            bp.release();
        }
    }

    private void checkRemoveAds() {
        try {
            if (bp.isSubscribed(getString(R.string.ID_REMOVE_ADS))) {
                MySetting.putRemoveAds(this, true);
            } else {
                MySetting.putRemoveAds(this, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
