package com.example.a3_handler_repeated_work;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//Handler는 개발자가 안드로이드 is에게 작업 수행을 요청하는 역할을 한다
//개발자가 작업을 요청하면 안드로이드 os는 작업을 하지 않을때, 개발자가 요청한 작업을 수행하게 된다
//이 처리는 main thread에서 수행한다
//5초 이상 걸리는 작업은 불가능하다 / 화면처리는 가능

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}