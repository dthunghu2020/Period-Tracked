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

public class Question4Activity extends AppCompatActivity {
    private NumberPicker numberPicker;
    private TextView txtNotification;
    private LinearLayout llCheckBox;
    private Button btnNext;
    private TextView txtTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_circle_length);

        llCheckBox = findViewById(R.id.llCheckBox);
        btnNext = findViewById(R.id.btnNext);
        numberPicker = findViewById(R.id.numberPicker);
        txtNotification = findViewById(R.id.txtNotification);

        txtNotification.setVisibility(View.GONE);
        llCheckBox.setVisibility(View.GONE);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Your birth year?");

        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(2020);
        numberPicker.setWrapSelectorWheel(false);

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

        numberPicker.setValue(1998);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Question4Activity.this, TodayActivity.class));
            }
        });

    }
}
