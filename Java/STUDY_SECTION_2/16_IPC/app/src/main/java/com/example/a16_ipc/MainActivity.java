package com.example.a16_ipc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import java.util.List;

// IPC - Activity에서 실행중인 서비스 데이터를 사용하고자 할때
// 현재 실행중인 서비스에 접속하고 서비스가 가지고 있는 메서드를 호출하여 값을 가져와 사용할 수 있다.
public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    // 접속한 서비스 객체
    ServiceClass ipcService = null;
    // 서비스 접속을 관리하는 객체를 생성 하거나 아래처럼...클래스로 만들어 진행을 해도 된다.
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // ServiceClass의 LocalBinder 객체가 IBinder service parameter로 들어오게 된다.
            ServiceClass.LocalBinder binder = (ServiceClass.LocalBinder)service;
            ipcService = binder.get_service();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ipcService = null;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        //현재 서비스가 수행중인지 확인한다.
        boolean check = is_service_running("com.example.a16_ipc.ServiceClass");
        Intent intent = new Intent(this, ServiceClass.class);

        if(check == false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
        // 서비스에 바인딩
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 서비스 바인딩 해제
        unbindService(connection);
    }

    public boolean is_service_running(String name){
        // 현재 실행중인 서비스의 이름을 가져온다.
        ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos =  manager.getRunningServices(Integer.MAX_VALUE);

        for(ActivityManager.RunningServiceInfo info : runningServiceInfos){
            if(info.service.getClassName().equals(name)){
                return true;
            }
        }

        return false;
    }

    public void btn_method(View view){
        // 서비스 객체의 메서드를 호출해 값을 반환받는다.
        int val = ipcService.get_value();
        textView.setText("val : " + val);
    }

    // 서비스 접속 관리를 하는 클래스
//    class ConnectionClass implements ServiceConnection{
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    }
}