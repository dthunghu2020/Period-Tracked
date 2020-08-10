package com.hungdt.periodtracked.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.utils.MySetting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Question2Activity extends AppCompatActivity {
    private CheckBox checkbox;
    private NumberPicker numberPicker;
    private TextView txtNotification;
    private ConstraintLayout clData;
    private int length=5;
    public static final String ACTION_FINISH_Q2= "F_Q2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_period_length);

        LinearLayout llCheckBox = findViewById(R.id.llCheckBox);
        Button btnNext = findViewById(R.id.btnNext);
        checkbox = findViewById(R.id.checkbox);
        numberPicker = findViewById(R.id.numberPicker);
        txtNotification = findViewById(R.id.txtNotification);
        clData = findViewById(R.id.clData);

        checkbox.setChecked(false);

        txtNotification.setVisibility(View.GONE);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(30);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.setTextSize(146f);
        }
        /*numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value+" days";
            }
        })*/;

        try {
            Method method = numberPicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(numberPicker, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                length = newVal;
            }
        });

        numberPicker.setValue(5);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked()){
                    MySetting.putPeriodLength(getApplicationContext(), 5);
                }else if(!checkbox.isChecked()){
                    MySetting.putPeriodLength(getApplicationContext(), length);
                }
                startActivity(new Intent(Question2Activity.this,Question3Activity.class));
            }
        });

        llCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    checkbox.setChecked(false);
                    clData.setVisibility(View.VISIBLE);
                    txtNotification.setVisibility(View.GONE);
                } else {
                    checkbox.setChecked(true);
                    clData.setVisibility(View.GONE);
                    txtNotification.setVisibility(View.VISIBLE);
                }
            }
        });
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    checkbox.setChecked(true);
                    clData.setVisibility(View.GONE);
                    txtNotification.setVisibility(View.VISIBLE);
                } else {
                    checkbox.setChecked(false);
                    clData.setVisibility(View.VISIBLE);
                    txtNotification.setVisibility(View.GONE);
                }
            }
        });
        IntentFilter intentFilter = new IntentFilter(ACTION_FINISH_Q2);
        registerReceiver(broadcast, intentFilter);
    }
    private BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcast);
        super.onDestroy();
    }
}
