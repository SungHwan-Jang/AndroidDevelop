package com.example.a9_data_transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
// activity 를 실행하기 위해 사용하는 intent 객체에 putExtra 메서드를 이용하여 데이터를 세팅하면
// 실행되는 activity 에서 데이터를 전달받을 수 있다.

// putExtra 메서드는 자료형 별로 메서드가 제공되기 때문에 타입을 가리지 않는다.
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}