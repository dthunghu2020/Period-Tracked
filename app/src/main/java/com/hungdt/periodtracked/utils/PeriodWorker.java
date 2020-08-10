package com.hungdt.periodtracked.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.dataset.Constant;
import com.hungdt.periodtracked.view.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PeriodWorker extends Worker {
    public static final String KEY_TASK_OUTPUT = "key_output";
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Plant";

    Random rd = new Random();
    //Calendar calendar = Calendar.getInstance();
    //private List<Remind> reminds = new ArrayList<>();

    public PeriodWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        if(MySetting.get8AM(getApplicationContext())){
            sendNotification(0);
        }
        if(MySetting.get12AM(getApplicationContext())){
            sendNotification(1);
        }
        if(MySetting.get8PM(getApplicationContext())){
            sendNotification(2);
        }

        Data data1 = new Data.Builder()
                .putString(KEY_TASK_OUTPUT, "Task Finished!")
                .build();


        return Result.success(data1);
    }

    private void sendNotification(int i) {
       int random = rd.nextInt(3);
                switch (random) {
                    case 0:
                        displayNotification("Period Tracker", "Check Yourself\n" +
                                "Add weight, mood, symptoms ... to predict correctly !!!", i);
                        break;
                    case 1:
                        displayNotification("Period Tracker", "Drink warm water if your stomach ache comes!", i);
                        break;
                    case 2:
                        displayNotification("Period Tracker", "Supplement iron supplements for yourself, girl!", i);
                        break;
                }
    }

    private void displayNotification(String task, String desc, int i) {

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri soundUri = Uri.parse(
                    "android.resource://" +
                            getApplicationContext().getPackageName() +
                            "/" +
                            R.raw.noti);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(soundUri, audioAttributes);
            manager.createNotificationChannel(channel);
        }

        //Phần hiển thị notifi
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), i, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setPriority(Notification.PRIORITY_MAX);
        manager.notify(i, builder.build());
    }

    private int countDay(int year, int month, int day) {
        if (month < 3) {
            year--;
            month += 12;
        }
        return 365 * year + year / 4 - year / 100 + year / 400 + (153 * month - 457) / 5 + day - 306;
    }
}
