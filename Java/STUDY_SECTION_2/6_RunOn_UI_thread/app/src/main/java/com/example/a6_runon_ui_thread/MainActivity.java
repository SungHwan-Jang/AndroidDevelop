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

        //thread 가동
        isRunning = true;
        ThreadClass thread = new ThreadClass();
        thread.start();
    }

    public void btn_method(View view){
        long now = System.currentTimeMillis();
        textView1.setText(MessageFormat.format("now : {0}", now));
    }

    class ThreadClass extends Thread {
        @Override
        public void run() {
            super.run();
            while (isRunning) {
                SystemClock.sleep(100);
                // 한 번 선언한 뒤 변하지 않는 Immutable 형식
                // Java 에서는 상수를 선언할 때 final을 이용하여 Read-Only 로 설정
                final long time = System.currentTimeMillis();
                Log.d("test", "time : " + time);

                // 기존에는 handler를 사용하여 화면처리를 구현하였다.
                // !!!!!! handler 를 사용하는경우 : 이곳 저곳에서 핸들러에 등록된 처리를 사용할 경우
                // !!!!!! runOnUithread를 사용 경우 : 한 군데에서만 사용하는 경우
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 실제로는 time 변수는 개발자 생성 스레드에서 만들어 진 변수이며
                        // runOnUiThread는 main thread에서 처리한다.
                        // 안정성을 위하여 해당 time 변수는 final로 선언해야 한다.
                        textView2.setText(MessageFormat.format("thread : {0}", time));
                    }
                }); // runnable 인터페이스를 구현한 객체를 전달,  thread와 관련됨 즉, 익명 중첩 클래스를 만들어 전달하면 된다.
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}