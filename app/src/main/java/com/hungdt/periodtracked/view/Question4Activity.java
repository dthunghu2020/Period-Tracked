package com.hungdt.periodtracked.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hungdt.periodtracked.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Question4Activity extends AppCompatActivity {
    private NumberPicker numberPicker;
    private TextView txtNotification,txtStep,txtTextDays;
    private ImageView imgStep;
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
        txtStep = findViewById(R.id.txtStep);
        txtTextDays = findViewById(R.id.txtTextDays);
        imgStep = findViewById(R.id.imgStep);

        txtNotification.setVisibility(View.GONE);
        llCheckBox.setVisibility(View.GONE);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("What year was you born?");
        txtStep.setText("Step 4 out of 4");
        txtTextDays.setText("               "+getResources().getString(R.string.year));

        Glide.with(this)
                .load(R.drawable.step4)
                .into(imgStep);

        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(2020);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.setTextSize(146f);
        }
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
                startActivity(new Intent(Question4Activity.this, MainActivity.class));
            }
        });

    }
}
