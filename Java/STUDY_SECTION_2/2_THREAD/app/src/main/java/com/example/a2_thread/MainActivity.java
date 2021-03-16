package com.example.a2_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.MessageFormat;
// 안드로이드는 비 동기적 처리 외에 네트워크에 관련된 코드는 전부 스레드로 운영해야한다.
// AND는 액티비티의 코드를 처리하기 위해 쓰레드를 발생한다.
// 여기서 발생되는 쓰레드를 main thread or ui thread 라고 부르기도 한다.

// main thread가 현재 작업을 하지 않을 때만 화면 작업이 가능하여 main thread가 바쁠때,
// 화면 작업이나 터치가 발생하면 ANR (Application not Respond )이 발생한다.
public class MainActivity extends AppCompatActivity {

    TextView text1, text2;
    // 쓰레드 동작 여부 컨트롤 할 변수
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView)findViewById(R.id.text_view1);
        text2 = (TextView)findViewById(R.id.text_view2);

        // 100ms 마다 현재 시간을 출력하려고 함. ( 이러한 경우 main thread가 해당 작업처리하느라 아무것도 못함 ANR 발생)
//        while(true){
//            SystemClock.sleep(100);
//            long now = System.currentTimeMillis();
//            text2.setText(MessageFormat.format("버튼 클릭 : {0}", now));
//        }
        isRunning = true;
        ThreadClass thread = new ThreadClass();
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // thread 종료를 위하여 false 값을 넣어주도록 하자.
        isRunning = false;
    }

    public void btnMethod(View view){
        long now = System.currentTimeMillis();
        text1.setText(MessageFormat.format("버튼 클릭 : {0}", now));
    }

    // UI thread 에 developer 생성 thread로 화면 처리 시, 에러가 발생한다.
    // 그러나 현재 안드로이드 오레오(8.0) 이상 부터는 developer 가 발생시킨 스레드에서 화면 처리가 가능하다.
    class ThreadClass extends Thread{
        @Override
        public void run() {
            super.run();
            while (isRunning){
                SystemClock.sleep(100);
                long now = System.currentTimeMillis();
                Log.d("test", MessageFormat.format("버튼 클릭 : {0}", now));
                text2.setText(MessageFormat.format("버튼 클릭 : {0}", now));
            }
        }
    }
}

