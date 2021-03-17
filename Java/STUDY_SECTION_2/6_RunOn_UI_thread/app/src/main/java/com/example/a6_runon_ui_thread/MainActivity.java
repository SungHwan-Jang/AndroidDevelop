package com.example.a6_runon_ui_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;
// RunOnUiThread 메서드는 개발자가 발생시킨 일반 스레드에서 코드 일부를 main thread가 처리하도록 하는 메서드

public class MainActivity extends AppCompatActivity {
    TextView textView1, textView2;
    // 스레드 중지 처리를 위한 변수
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
    }

    public void btn_method(View view){
        long now = System.currentTimeMillis();
        textView1.setText(MessageFormat.format("now : {0}", now));
    }

    class ThreadClass extends Thread{
        @Override
        public void run() {
            super.run();
            SystemClock.sleep(100);

            long time = System.currentTimeMillis();
            Log.d("test", "time : " + time);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}