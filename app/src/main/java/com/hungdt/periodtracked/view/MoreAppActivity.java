package com.hungdt.periodtracked.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.GameItem;
import com.hungdt.periodtracked.utils.Ads;
import com.hungdt.periodtracked.utils.Helper;
import com.hungdt.periodtracked.utils.MySetting;
import com.hungdt.periodtracked.view.adapter.GameAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MoreAppActivity extends AppCompatActivity {

    private ImageView imgStarLight1, imgStarLight2, imgStarLight3, imgStarLight4, imgStarLight5;
    private LinearLayout lnMoregame;
    private RecyclerView rvListMoregame;
    private TextView tvMoregame, tvHideMoregame;
    private GameAdapter adapterMoregame;
    private ArrayList<GameItem> listMoregame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_app);
        //Helper.setColorStatusBar(this, R.color.status_bar);
        initView();
        initMoregame();
        Ads.initNativeGgFb((LinearLayout) findViewById(R.id.lnNative), this, false);
    }


    private void initMoregame() {
        lnMoregame = findViewById(R.id.lnMoregame);
        tvMoregame = findViewById(R.id.tvMoreGame);
        tvHideMoregame = findViewById(R.id.tvHideMoreGame);
        rvListMoregame = findViewById(R.id.rvListMoregame);
        rvListMoregame.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvListMoregame.setLayoutManager(layoutManager);
        rvListMoregame.setNestedScrollingEnabled(false);
        if (!MySetting.getConfigMoregame(this).equals("false")) {
            try {
                listMoregame = new ArrayList<GameItem>();
                JSONArray jsonArray = new JSONArray(MySetting.getConfigMoregame(this));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GameItem item = new GameItem();
                    item.setId(i);
                    item.setName(jsonObject.getString("name"));
                    item.setPathImage(jsonObject.getString("pathImage"));
                    item.setPackageName(jsonObject.getString("packageName"));
                    item.setStateInstall(jsonObject.getString("stateInstall"));
                    item.setLive(jsonObject.getBoolean("isLive"));

                    listMoregame.add(item);
                }

                if (listMoregame != null && listMoregame.size() > 0) {
                    adapterMoregame = new GameAdapter(listMoregame, this, false);
                    rvListMoregame.setAdapter(adapterMoregame);
                } else {
                    lnMoregame.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lnMoregame.setVisibility(View.GONE);
        }

        tvMoregame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.callPublisherPlayStore(MoreAppActivity.this, getString(R.string.PLAY_STORE_DEV_NAME));
            }
        });
        tvHideMoregame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvHideMoregame.getText().toString().toLowerCase().equals("hide")) {
                    rvListMoregame.setVisibility(View.GONE);
                    tvHideMoregame.setText("Expand");
                } else {
                    rvListMoregame.setVisibility(View.VISIBLE);
                    tvHideMoregame.setText("Hide");
                }

            }
        });
        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initView() {

        imgStarLight1 = (ImageView) findViewById(R.id.imgStarLight1Rate);
        imgStarLight2 = (ImageView) findViewById(R.id.imgStarLight2Rate);
        imgStarLight3 = (ImageView) findViewById(R.id.imgStarLight3Rate);
        imgStarLight4 = (ImageView) findViewById(R.id.imgStarLight4Rate);
        imgStarLight5 = (ImageView) findViewById(R.id.imgStarLight5Rate);

        click();
    }

    private void click() {
        imgStarLight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MySetting.putRateApp(MoreAppActivity.this, 1);
                setData();
                try {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Helper.feedback(MoreAppActivity.this);
                        }
                    }, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        imgStarLight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MySetting.putRateApp(MoreAppActivity.this, 2);
                setData();
                try {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Helper.feedback(MoreAppActivity.this);
                        }
                    }, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgStarLight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MySetting.putRateApp(MoreAppActivity.this, 3);
                setData();
                try {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Helper.feedback(MoreAppActivity.this);
                        }
                    }, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgStarLight4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySetting.putRateApp(MoreAppActivity.this, 4);
                setData();
                try {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Helper.callPlayStore(MoreAppActivity.this, getPackageName());
                        }
                    }, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgStarLight5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySetting.putRateApp(MoreAppActivity.this, 5);
                setData();
                try {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Helper.callPlayStore(MoreAppActivity.this, getPackageName());
                        }
                    }, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setData() {
        if (MySetting.isRateApp(this) == 0) {
            imgStarLight1.setImageResource(R.drawable.ic_like_off);
            imgStarLight2.setImageResource(R.drawable.ic_like_off);
            imgStarLight3.setImageResource(R.drawable.ic_like_off);
            imgStarLight4.setImageResource(R.drawable.ic_like_off);
            imgStarLight5.setImageResource(R.drawable.ic_like_off);
        } else if (MySetting.isRateApp(this) == 1) {
            imgStarLight1.setImageResource(R.drawable.ic_like_on);
            imgStarLight2.setImageResource(R.drawable.ic_like_off);
            imgStarLight3.setImageResource(R.drawable.ic_like_off);
            imgStarLight4.setImageResource(R.drawable.ic_like_off);
            imgStarLight5.setImageResource(R.drawable.ic_like_off);
        } else if (MySetting.isRateApp(this) == 2) {
            imgStarLight1.setImageResource(R.drawable.ic_like_on);
            imgStarLight2.setImageResource(R.drawable.ic_like_on);
            imgStarLight3.setImageResource(R.drawable.ic_like_off);
            imgStarLight4.setImageResource(R.drawable.ic_like_off);
            imgStarLight5.setImageResource(R.drawable.ic_like_off);
        } else if (MySetting.isRateApp(this) == 3) {
            imgStarLight1.setImageResource(R.drawable.ic_like_on);
            imgStarLight2.setImageResource(R.drawable.ic_like_on);
            imgStarLight3.setImageResource(R.drawable.ic_like_on);
            imgStarLight4.setImageResource(R.drawable.ic_like_off);
            imgStarLight5.setImageResource(R.drawable.ic_like_off);
        } else if (MySetting.isRateApp(this) == 4) {
            imgStarLight1.setImageResource(R.drawable.ic_like_on);
            imgStarLight2.setImageResource(R.drawable.ic_like_on);
            imgStarLight3.setImageResource(R.drawable.ic_like_on);
            imgStarLight4.setImageResource(R.drawable.ic_like_on);
            imgStarLight5.setImageResource(R.drawable.ic_like_off);
        } else if (MySetting.isRateApp(this) == 5) {
            imgStarLight1.setImageResource(R.drawable.ic_like_on);
            imgStarLight2.setImageResource(R.drawable.ic_like_on);
            imgStarLight3.setImageResource(R.drawable.ic_like_on);
            imgStarLight4.setImageResource(R.drawable.ic_like_on);
            imgStarLight5.setImageResource(R.drawable.ic_like_on);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

}

