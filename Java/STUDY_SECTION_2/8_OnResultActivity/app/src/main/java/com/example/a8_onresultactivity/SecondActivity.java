package com.example.a8_onresultactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void btn_method(View view){
        // 실행 결과를 세팅한다.
        setResult(RESULT_OK);
        // terminate activity
        finish();
    }

    public void btn_method2(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void btn_method3(View view){
        // setResult(RESULT_FIRST_USER + 1); 등의 방법으로 더 많은 결과 반환이 가능하다.
        setResult(RESULT_FIRST_USER);
        finish();
    }
}