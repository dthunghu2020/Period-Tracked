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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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
    ConstraintLayout clPeriodLength, clPeriodCircle, clPeriodYear;
    LinearLayout llPC_Up, llPU_Up;
    ImageView imgEditName, imgEditCircle, imgEditLength, imgBack, imgEditYear;
    EditText edtName;
    TextView txtName, txtCircle, txtLength, txtYear, txtSaveName, txtSaveYear, txtSaveCircle, txtSaveLength;
    NumberPicker npCircle, npLength, npYear;
    SwitchCompat sw8AM, sw12AM, sw8PM;
    String userName = "";
    int circle, length, year;
    int pickC, pickL, pickY;
    boolean openC = false;
    boolean openL = false;

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
        txtSaveName.setVisibility(View.INVISIBLE);
        txtSaveYear.setVisibility(View.INVISIBLE);
        txtSaveCircle.setVisibility(View.INVISIBLE);
        txtSaveLength.setVisibility(View.INVISIBLE);
        edtName.setVisibility(View.GONE);

        circle = MySetting.getPeriodCircle(this);
        length = MySetting.getPeriodLength(this);
        userName = MySetting.getUserName(this);
        year = MySetting.getUserBirthYear(this);
        txtCircle.setText("" + circle);
        txtLength.setText("" + length);
        txtYear.setText("" + year);
        if (userName.equals("")) {
            txtName.setText(getString(R.string.enter_your_name_or_nickname));
            txtName.setTextColor(getResources().getColor(R.color.gray));
        } else {
            edtName.setText(userName);
        }
        txtName.setText(userName);

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
                pickY = newVal;
                if (pickY == year) {
                    txtSaveYear.setVisibility(View.INVISIBLE);
                } else {
                    txtSaveYear.setVisibility(View.VISIBLE);
                }
                txtYear.setText("" + pickY);
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
                pickC = newVal;
                if (pickC == circle) {
                    txtSaveCircle.setVisibility(View.INVISIBLE);
                } else {
                    txtSaveCircle.setVisibility(View.VISIBLE);
                }
                txtCircle.setText("" + pickC);
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
                pickL = newVal;
                if (pickL == length) {
                    txtSaveLength.setVisibility(View.INVISIBLE);
                } else {
                    txtSaveLength.setVisibility(View.VISIBLE);
                }

                txtLength.setText("" + pickL);
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
                txtSaveName.setVisibility(View.VISIBLE);
                edtName.setVisibility(View.VISIBLE);
                edtName.setFocusableInTouchMode(true);
                edtName.requestFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                imgEditName.setVisibility(View.INVISIBLE);
                txtName.setVisibility(View.INVISIBLE);
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
                openC = true;
                clPeriodCircle.setVisibility(View.VISIBLE);
                llPC_Up.setVisibility(View.VISIBLE);
                imgEditCircle.setVisibility(View.INVISIBLE);
            }
        });
        imgEditLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openL = true;
                clPeriodLength.setVisibility(View.VISIBLE);
                llPC_Up.setVisibility(View.VISIBLE);
                imgEditLength.setVisibility(View.INVISIBLE);
            }
        });

        txtSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEditName.setVisibility(View.VISIBLE);
                edtName.setVisibility(View.GONE);
                txtSaveName.setVisibility(View.INVISIBLE);
                txtName.setVisibility(View.VISIBLE);
                if (userName.equals("")) {
                    txtName.setText(getString(R.string.enter_your_name_or_nickname));
                    txtName.setTextColor(getResources().getColor(R.color.gray));
                } else {
                    txtName.setText(userName);
                    txtName.setTextColor(getResources().getColor(R.color.violet));
                }
                MySetting.setUserName(SettingActivity.this, userName);
                Toast.makeText(SettingActivity.this, "Save name success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TodayFragment.ACTION_UPDATE_TODAY_FRAGMENT);
                sendBroadcast(intent);
            }
        });

        txtSaveYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = pickY;
                txtSaveYear.setVisibility(View.INVISIBLE);
                MySetting.setUserBirthYear(SettingActivity.this, year);
                Toast.makeText(SettingActivity.this, "Save birth year success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TodayFragment.ACTION_UPDATE_TODAY_FRAGMENT);
                sendBroadcast(intent);
                llPU_Up.setVisibility(View.GONE);
                clPeriodYear.setVisibility(View.GONE);
                imgEditYear.setVisibility(View.VISIBLE);
            }
        });
        txtSaveCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circle = pickC;
                txtSaveCircle.setVisibility(View.INVISIBLE);
                MySetting.putPeriodCircle(SettingActivity.this, circle);
                Toast.makeText(SettingActivity.this, "Save period circle success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TodayFragment.ACTION_UPDATE_TODAY_FRAGMENT);
                sendBroadcast(intent);
                clPeriodCircle.setVisibility(View.GONE);
                openC = false;
                if (!openL) {
                    llPC_Up.setVisibility(View.GONE);
                }
                imgEditCircle.setVisibility(View.VISIBLE);
            }
        });
        txtSaveLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                length = pickL;
                txtSaveLength.setVisibility(View.INVISIBLE);
                MySetting.putPeriodLength(SettingActivity.this, length);
                Toast.makeText(SettingActivity.this, "Save period length success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TodayFragment.ACTION_UPDATE_TODAY_FRAGMENT);
                sendBroadcast(intent);
                clPeriodYear.setVisibility(View.GONE);
                openL = false;
                if (!openC) {
                    llPC_Up.setVisibility(View.GONE);
                }
                imgEditLength.setVisibility(View.VISIBLE);
            }
        });

        llPU_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtYear.setText("" + year);
                txtSaveYear.setVisibility(View.INVISIBLE);
                llPU_Up.setVisibility(View.GONE);
                clPeriodYear.setVisibility(View.GONE);
                imgEditYear.setVisibility(View.VISIBLE);
            }
        });

        llPC_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtLength.setText("" + length);
                txtCircle.setText("" + circle);
                txtSaveLength.setVisibility(View.INVISIBLE);
                txtSaveCircle.setVisibility(View.INVISIBLE);
                llPC_Up.setVisibility(View.GONE);
                clPeriodCircle.setVisibility(View.GONE);
                clPeriodLength.setVisibility(View.GONE);
                imgEditCircle.setVisibility(View.VISIBLE);
                imgEditLength.setVisibility(View.VISIBLE);
            }
        });

        if (MySetting.get8AM(SettingActivity.this)) {
            sw8AM.setChecked(true);
        } else {
            sw8AM.setChecked(false);
        }
        if (MySetting.get12AM(SettingActivity.this)) {
            sw12AM.setChecked(true);
        } else {
            sw12AM.setChecked(false);
        }
        if (MySetting.get8PM(SettingActivity.this)) {
            sw8PM.setChecked(true);
        } else {
            sw8PM.setChecked(false);
        }

        sw8AM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   MySetting.set8AM(SettingActivity.this,true);
                } else {
                    MySetting.set8AM(SettingActivity.this,false);
                }
            }
        });
        sw12AM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MySetting.set12AM(SettingActivity.this,true);
                } else {
                    MySetting.set12AM(SettingActivity.this,false);
                }
            }
        });
        sw8PM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MySetting.set8PM(SettingActivity.this,true);
                } else {
                    MySetting.set8PM(SettingActivity.this,false);
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
        txtName = findViewById(R.id.txtName);
        txtCircle = findViewById(R.id.txtCircle);
        txtLength = findViewById(R.id.txtLength);
        imgBack = findViewById(R.id.imgBack);
        npCircle = findViewById(R.id.npCircle);
        npLength = findViewById(R.id.npLength);
        imgEditYear = findViewById(R.id.imgEditYear);
        npYear = findViewById(R.id.npYear);
        clPeriodYear = findViewById(R.id.clPeriodYear);
        txtYear = findViewById(R.id.txtYear);
        txtSaveName = findViewById(R.id.txtSaveName);
        txtSaveYear = findViewById(R.id.txtSaveYear);
        txtSaveCircle = findViewById(R.id.txtSaveCircle);
        txtSaveLength = findViewById(R.id.txtSaveLength);
        sw8AM = findViewById(R.id.sw8AM);
        sw12AM = findViewById(R.id.sw12AM);
        sw8PM = findViewById(R.id.sw8PM);
    }

}
