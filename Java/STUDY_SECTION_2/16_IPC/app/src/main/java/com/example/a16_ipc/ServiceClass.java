package com.example.a16_ipc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ServiceClass extends Service {
    int value = 0;

    // service 접속 처리를 하기 위한 바인더 객체 ( 접속하는 쪽에서 이 객체를 이용하여 서비스 객체를 받을 수 있다.)
    IBinder binder = new LocalBinder();

    public ServiceClass() {
    }

    // 서비스에 접속하면 호출되는 메서드
    // 여기서 반환하는 바인더 객체를 접속하는 객체쪽에 반환한다.
    // 반환하는 바인더를 이용해 현재 서비스 객체를 반환받을 수 있다.
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("test1", "Service", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test1");

            builder.setSmallIcon(android.R.drawable.ic_menu_search);
            builder.setContentTitle("run service");
            builder.setContentText("running service now...");
            builder.setAutoCancel(true);
            Notification notification = builder.build();

            startForeground(10, notification);
        }

        ThreadClass threadClass = new ThreadClass();
        threadClass.start();
        return super.onStartCommand(intent, flags, startId);
    }

    class ThreadClass extends Thread{
        @Override
        public void run() {
            super.run();
            while(true) {
                SystemClock.sleep(1000);
                Log.d("test", "now : " + value);
                value++;
            }
        }
    }

    // 접속하는 액티비티에서 서비스를 추출하기 위해 사용되는 클래스
    class LocalBinder extends Binder{
        public ServiceClass get_service(){
            return ServiceClass.this;
        }
    }

    //변수의 값을 반환하는 메서드
    public int get_value(){
        return value;
    }
}