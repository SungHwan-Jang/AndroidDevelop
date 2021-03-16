package com.example.a3_handler_repeated_work;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;

//Handler는 개발자가 안드로이드 os에게 작업 수행을 요청하는 역할을 한다
//개발자가 작업을 요청하면 안드로이드 os는 작업을 하지 않을때, 개발자가 요청한 작업을 수행하게 된다
//이 처리는 main thread에서 수행한다 - 요청받은 작업을 순서대로 반복 처리
//5초 이상 걸리는 작업은 불가능하다 / 화면처리는 가능

public class MainActivity extends AppCompatActivity {
    TextView textView1, textView2;
    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView)findViewById(R.id.text_view1);
        textView2 = (TextView)findViewById(R.id.text_view2);

        handler = new Handler();
        // handler를 통해 요청할 작업
        ThreadClass thread = new ThreadClass();
        // thread.start(); // 여기서 화면 작업을 하면 안됨 ( 이전 버전 마이그레이션 때문에 )
        handler.post(thread);

    }

    public void btn_method(View view){
        long now = System.currentTimeMillis();
        textView1.setText(MessageFormat.format("current time : {0}", now));
    }

    class ThreadClass extends Thread{
        @Override
        public void run() {
            super.run();
            long now = System.currentTimeMillis();
            textView2.setText(MessageFormat.format("current time : {0}", now));

            // 현재 작업을 다시 요청한다.
            // onCreate시 핸들러 한번 호출 (100 ms 마다 호출시, handler.post 대신에 handler.postDelayed()를 호출한다.
            handler.postDelayed(this, 1000);
        }
    }
}