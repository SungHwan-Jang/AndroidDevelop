package hwan.test.a27_notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1Method(View v){
        NotificationCompat.Builder builder = getNotificationBuilder("channel1", "1th_channel");
        //Ticker 메시지 설정 - 6.0 이상에서는 Ticker 메시지 기능 삭제
        builder.setTicker("Ticker message");
        // 작은 아이콘 설정
        builder.setSmallIcon(android.R.drawable.ic_menu_search);
        // 큰 아이콘 설정 - 큰아이콘은 Bitmap 객체로 할당해야 한다.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);
        // 숫자 설정
        builder.setNumber(100);
        // 타이틀 설정
        builder.setContentTitle("Content Title");
        // 내용 설정
        builder.setContentText("Content Text");

        // 메시지 객체를 생성
        Notification notification = builder.build();

        //알림 메시지 관리 객체를 추출한다.
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //알림 메시지를 출력한다. (id 설정 - 똑같은 값으로 설정시 확인 안한 메시지 덮어씀, 새롭게 설정하면 계속생김)
        manager.notify(10, notification);
    }

    public void btn2Method(View v){
        NotificationCompat.Builder builder = getNotificationBuilder("channel1", "1th_channel");
        //Ticker 메시지 설정 - 6.0 이상에서는 Ticker 메시지 기능 삭제
        builder.setTicker("Ticker message");
        // 작은 아이콘 설정
        builder.setSmallIcon(android.R.drawable.ic_menu_search);
        // 큰 아이콘 설정 - 큰아이콘은 Bitmap 객체로 할당해야 한다.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);
        // 숫자 설정
        builder.setNumber(100);
        // 타이틀 설정
        builder.setContentTitle("Content Title 2");
        // 내용 설정
        builder.setContentText("Content Text 2");

        // 메시지 객체를 생성
        Notification notification = builder.build();

        //알림 메시지 관리 객체를 추출한다.
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //알림 메시지를 출력한다. (id 설정 - 똑같은 값으로 설정시 확인 안한 메시지 덮어씀, 새롭게 설정하면 계속생김)
        manager.notify(20, notification); // id 다르게 해야 메시지가 겹치치 않는다.
    }

    public void btn3Method(View v){
        NotificationCompat.Builder builder = getNotificationBuilder("channel2", "2th_channel");
        //Ticker 메시지 설정 - 6.0 이상에서는 Ticker 메시지 기능 삭제
        builder.setTicker("Ticker message");
        // 작은 아이콘 설정
        builder.setSmallIcon(android.R.drawable.ic_menu_search);
        // 큰 아이콘 설정 - 큰아이콘은 Bitmap 객체로 할당해야 한다.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);
        // 숫자 설정
        builder.setNumber(100);
        // 타이틀 설정
        builder.setContentTitle("Content Title 3");
        // 내용 설정
        builder.setContentText("Content Text 3");

        // 메시지 객체를 생성
        Notification notification = builder.build();

        //알림 메시지 관리 객체를 추출한다.
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //알림 메시지를 출력한다. (id 설정 - 똑같은 값으로 설정시 확인 안한 메시지 덮어씀, 새롭게 설정하면 계속생김)
        manager.notify(30, notification); // id 다르게 해야 메시지가 겹치치 않는다.
    }

    public void btn4Method(View v){
        NotificationCompat.Builder builder = getNotificationBuilder("channel2", "2th_channel");
        //Ticker 메시지 설정 - 6.0 이상에서는 Ticker 메시지 기능 삭제
        builder.setTicker("Ticker message");
        // 작은 아이콘 설정
        builder.setSmallIcon(android.R.drawable.ic_menu_search);
        // 큰 아이콘 설정 - 큰아이콘은 Bitmap 객체로 할당해야 한다.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);
        // 숫자 설정
        builder.setNumber(100);
        // 타이틀 설정
        builder.setContentTitle("Content Title 4");
        // 내용 설정
        builder.setContentText("Content Text 4");

        // 메시지 객체를 생성
        Notification notification = builder.build();

        //알림 메시지 관리 객체를 추출한다.
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //알림 메시지를 출력한다. (id 설정 - 똑같은 값으로 설정시 확인 안한 메시지 덮어씀, 새롭게 설정하면 계속생김)
        manager.notify(40, notification); // id 다르게 해야 메시지가 겹치치 않는다.
    }

    // 안드로이드 8.0 이상을 대응하기 위한 Notification.Builder 생성 메서드
    // 버전별로 분기해야 한다.

    public NotificationCompat.Builder getNotificationBuilder(String id, String name){
        NotificationCompat.Builder builder = null;
        // OS 버전별로 분기
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // 안드로이드 8.0 오레오 버전 이상
            // 알림 메시지를 관리하는 객체를 추출한다.
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            // 채널 객체를 생성한다.
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            // 메시지 출력시 단말기 LED를 사용할 것인지... (option)
            channel.enableLights(true);
            // LED 색상을 설정
            channel.setLightColor(Color.RED);
            // 진동 설정
            channel.enableVibration(true);
            // 알림 메시지를 관리하는 객체에 채널을 등록한다.
            manager.createNotificationChannel(channel);
            // 메시지 생성을 위한 객체를 생성한다.
            builder = new NotificationCompat.Builder(this, id);
        }
        else{
            builder = new NotificationCompat.Builder(this);
        }

        return builder;
    }
}