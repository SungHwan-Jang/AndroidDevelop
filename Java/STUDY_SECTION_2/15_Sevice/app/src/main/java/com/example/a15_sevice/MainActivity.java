package com.example.a15_sevice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
// Service - 화면을 가지고 있지 않은 실행단위 / back ground 프로세싱 처리를 위해 사용된다.
// 안드로이드 4대 구성요소 중 백그라운드 처리를 위해 제공되는 요소이다.
// Activity는 화면을 가지고 있어 화면이 보이는 동안만 동작하지만, Service 는 화면을 가지고 있지 않아서
// 보이지 않는 동안에도 동작하는 것을 의미한다.

//Forground service
// - 서비스는 기본적으로 백그라운드에서 운영되는 실행요소이며 메모리가 부족해지거나 하면 os에 의해 제거된다.
// - 이를 방지하려면 Forground service로 만들어서 사용한다.

public class MainActivity extends AppCompatActivity {

    Intent serviceIntent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // *** 안드로이드 8.0 미만 버전에서는 service 실행 중 activity가 종료되면
    // 정지된 service가 한번 더 실행이 된다.
    // 8.0 이상인 경우에는 activity 종료 시, 서비스가 다시 가동되지 않는다.

    // *** 안드로이드 8.0 미만&이상 버전에서 intent service 실행 중 activity가 종료되면
    // 서비스 및 thread 모두 같이 종료 된다.

    public void btn_method_1(View view){
        serviceIntent = new Intent(this, Service1.class);
        startService(serviceIntent);
    }

    public void btn_method_2(View view){
        stopService(serviceIntent);
    }

    public void btn_method_3(View view) {
        serviceIntent = new Intent(this, Service2.class);
        startService(serviceIntent);
    }

    public void btn_method_4(View view) {
        serviceIntent = new Intent(this, ServiceForground.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(serviceIntent);
        }
        else {
            startService(serviceIntent);
        }

        finish();
    }
}