package com.hungdt.periodtracked.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.utils.KEY;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.fragment.TodayFragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SettingActivity extends AppCompatActivity {
    ConstraintLayout clPeriodLength, clPeriodCircle,clPeriodYear;
    LinearLayout llPC_Up, llPU_Up;
    ImageView imgEditName, imgEditCircle, imgEditLength, imgBack,imgEditYear;
    EditText edtName;
    Button btnSave;
    TextView txtCircle, txtLength,txtYear;
    NumberPicker npCircle, npLength,npYear;
    String userName;
    int circle,length,year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        clPeriodCircle.setVisibility(View.GONE);
        clPeriodLength.setVisibility(View.GONE);
        clPeriodYear.setVisibility(View.GONE);
        llPC_Up.setVisibility(View.GONE);
        llPU_Up.setVisibility(View.GONE);

        circle = MySetting.getPeriodCircle(this);
        length = MySetting.getPeriodLength(this);
        userName = MySetting.getUserName(this);
        year = MySetting.getUserBirthYear(this);
        txtCircle.setText("" + circle);
        txtLength.setText("" + length);
        txtYear.setText(""+year);
        edtName.setText(userName);

        //set Year
        npYear.setMinValue(1950);
        npYear.setMaxValue(2050);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            npYear.setTextSize(146f);
        }
        npYear.setValue(year);
        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = newVal;
                txtYear.setText("" + year);
            }
        });
        npYear.setWrapSelectorWheel(false);

        try {
            Method method = npYear.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(npYear, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //setCircle
        npCircle.setMinValue(21);
        npCircle.setMaxValue(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            npCircle.setTextSize(146f);
        }
        npCircle.setValue(circle);
        npCircle.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                circle = newVal;
                txtCircle.setText("" + circle);
            }
        });
        npCircle.setWrapSelectorWheel(false);

        try {
            Method method = npCircle.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(npCircle, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //setLength
        npLength.setMinValue(1);
        npLength.setMaxValue(30);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            npLength.setTextSize(146f);
        }
        npLength.setValue(length);
        npLength.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                length = newVal;
                txtLength.setText("" + length);
            }
        });


        try {
            Method method = npLength.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(npLength, true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    userName = String.valueOf(s);
                } else {
                    userName = "";
                }
            }
        });

        imgEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setFocusableInTouchMode(true);
                edtName.requestFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                edtName.setFocusableInTouchMode(false);
                imgEditName.setVisibility(View.INVISIBLE);
            }
        });
        imgEditYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clPeriodYear.setVisibility(View.VISIBLE);
                llPU_Up.setVisibility(View.VISIBLE);
                imgEditYear.setVisibility(View.INVISIBLE);
            }
        });
        imgEditCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clPeriodCircle.setVisibility(View.VISIBLE);
                llPC_Up.setVisibility(View.VISIBLE);
                imgEditCircle.setVisibility(View.INVISIBLE);
            }
        });
        imgEditLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clPeriodLength.setVisibility(View.VISIBLE);
                llPC_Up.setVisibility(View.VISIBLE);
                imgEditLength.setVisibility(View.INVISIBLE);
            }
        });

        llPU_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPU_Up.setVisibility(View.GONE);
                clPeriodYear.setVisibility(View.GONE);
                imgEditName.setVisibility(View.VISIBLE);
                imgEditYear.setVisibility(View.VISIBLE);
            }
        });

        llPC_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPC_Up.setVisibility(View.GONE);
                clPeriodCircle.setVisibility(View.GONE);
                clPeriodLength.setVisibility(View.GONE);
                imgEditCircle.setVisibility(View.VISIBLE);
                imgEditLength.setVisibility(View.VISIBLE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySetting.setUserName(SettingActivity.this,userName);
                MySetting.setUserBirthYear(SettingActivity.this,year);
                MySetting.putPeriodCircle(SettingActivity.this,circle);
                MySetting.putPeriodLength(SettingActivity.this,length);
                Intent intent = new Intent(TodayFragment.ACTION_UPDATE_TODAY_FRAGMENT);
                sendBroadcast(intent);
                Toast.makeText(SettingActivity.this, "Save success!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        KeyboardVisibilityEvent.setEventListener(
                SettingActivity.this,
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

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        clPeriodLength = findViewById(R.id.clPeriodLength);
        clPeriodCircle = findViewById(R.id.clPeriodCircle);
        llPC_Up = findViewById(R.id.llPC_Up);
        llPU_Up = findViewById(R.id.llPU_Up);
        imgEditName = findViewById(R.id.imgEditName);
        imgEditCircle = findViewById(R.id.imgEditCircle);
        imgEditLength = findViewById(R.id.imgEditLength);
        edtName = findViewById(R.id.edtName);
        btnSave = findViewById(R.id.btnSave);
        txtCircle = findViewById(R.id.txtCircle);
        txtLength = findViewById(R.id.txtLength);
        imgBack = findViewById(R.id.imgBack);
        npCircle = findViewById(R.id.npCircle);
        npLength = findViewById(R.id.npLength);
        imgEditYear = findViewById(R.id.imgEditYear);
        npYear = findViewById(R.id.npYear);
        clPeriodYear = findViewById(R.id.clPeriodYear);
        txtYear = findViewById(R.id.txtYear);
    }

}
