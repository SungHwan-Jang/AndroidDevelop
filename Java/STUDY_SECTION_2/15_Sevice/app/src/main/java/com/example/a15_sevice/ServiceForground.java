package com.example.a15_sevice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

// <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/> 추가 - 사용자에게 허가 받지 않아도 됨
// 8.0 이상부터는 Activity 종료 시, Background도 종료 되지만,
// noti 달고 Forground 동작시에는 Activity가 종료 되어도 동작을 계속 수행한다.
public class ServiceForground extends Service {
    public ServiceForground() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 8.0 이상이면 notification message 띄우고 forground service로 운영한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 안드로이드 8.0 이상부터는 반드시 service를 가동시킨 activity가 종료가 되었을 때도 service가 돌도록 하겠다면,
            // 반드시!!!!!! 이렇게 알림 메시지를 등록한 뒤에 startForground 메서드에 알림 메시지를 등록해야한다.
            // 즉 사용자 몰래 background 동작을 막겠다는 의미이다!!!!
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("test1", "service", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            manager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test1");
            builder.setSmallIcon(android.R.drawable.ic_menu_search);
            builder.setContentTitle("run service");
            builder.setContentText("now running service...");
            builder.setAutoCancel(true);

            Notification notification = builder.build();

            // 현재 notification 메시지를 Forground 서비스의 메시지로 등록한다.
            startForeground(10, notification); // id는 noti 의 번호이다.
        }
        else{

        }
        // thread를 가동한다.
        ThreadClass thread = new ThreadClass();
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    class ThreadClass extends Thread{
        @Override
        public void run() {
            super.run();

            for(int i =0; i<10; i++){
                SystemClock.sleep(1000);
                long time = System.currentTimeMillis();
                Log.d("test", "Forground time : " + time);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)

            }

//            while(true){
//                SystemClock.sleep(1000);
//                long time = System.currentTimeMillis();
//                Log.d("test", "Forground time : " + time);
//            }

            // 작업이 완료되면 notification 메시지를 사라지게 한다.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                stopForeground(STOP_FOREGROUND_REMOVE); // 원래 id 10 넣어서 하는 동작인데... STOP_FOREGROUD_REMOVE를 넣음..
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(10);
            }
        }
    }
}