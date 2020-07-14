package com.hungdt.periodtracked.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.utils.MySetting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Question3Activity extends AppCompatActivity {
    private CheckBox checkbox;
    private NumberPicker numberPicker;
    private TextView txtNotification;
    private LinearLayout llCheckBox;
    private Button btnNext;
    private int circle=28;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_circle_length);

         llCheckBox = findViewById(R.id.llCheckBox);
         btnNext = findViewById(R.id.btnNext);
        checkbox = findViewById(R.id.checkbox);
        numberPicker = findViewById(R.id.numberPicker);
        txtNotification = findViewById(R.id.txtNotification);

        checkbox.setChecked(false);

        txtNotification.setVisibility(View.GONE);

        numberPicker.setMinValue(21);
        numberPicker.setMaxValue(100);
        numberPicker.setWrapSelectorWheel(false);

        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " days";
            }
        });

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
                circle = newVal;
            }
        });

        numberPicker.setValue(28);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    MySetting.putPeriodCircle(getApplicationContext(), 28);
                } else {
                    MySetting.putPeriodCircle(getApplicationContext(), circle);
                }

                startActivity(new Intent(Question3Activity.this, TodayActivity.class));
            }
        });

        llCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    checkbox.setChecked(false);
                    numberPicker.setVisibility(View.VISIBLE);
                    txtNotification.setVisibility(View.GONE);
                } else {
                    checkbox.setChecked(true);
                    numberPicker.setVisibility(View.GONE);
                    txtNotification.setVisibility(View.VISIBLE);
                }
            }
        });
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    checkbox.setChecked(true);
                    numberPicker.setVisibility(View.GONE);
                    txtNotification.setVisibility(View.VISIBLE);
                } else {
                    checkbox.setChecked(false);
                    numberPicker.setVisibility(View.VISIBLE);
                    txtNotification.setVisibility(View.GONE);
                }
            }
        });
    }
}
