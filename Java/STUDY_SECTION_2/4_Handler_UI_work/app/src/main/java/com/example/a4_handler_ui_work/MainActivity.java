package com.example.a4_handler_ui_work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;
// 안드로이드에서 네트워크 처리 (오래 걸리지 않더라도 오류 발생 상황이 높으므로 )나 5초이상의 작업은 new thread에서 처리해야함
// new thread에서 화면 관련 처리는 handler를 이용해야 한다.
// 오레오(8.0) 이상에서는 new thread 에서 화면 처리를 해도 된다.

// thread에서 코드 처리 중, 화면에 관련된 작업이 필요하다면 handler를 상속받은 클래스를 만들어 필요시 handler를 요청하면 된다.
// handler를 상속 받은 클래스에 만든 메서드는 main thread에서 처리한다.
public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;

    //thread의 무한 루프 종료키 위한 변수
    boolean isRunning = false;
    // 일반 스레드에서 요청한 화면 처리용 핸들러
    DisplayHandler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);

        //임시로 오래 걸리는 작업 생성
        // main thread 에서 계속 해당 작업 수행하여 화면에 아무것도 띄우지 못함
//        while (true){
//            SystemClock.sleep(100);
//            long now = System.currentTimeMillis();
//            textView2.setText(MessageFormat.format("current Time : {0}", now));
//        }
        handler = new DisplayHandler();
        isRunning = true;
        ThreadClass thread = new ThreadClass();
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    public void btn_method(View view){
        long now = System.currentTimeMillis();
        textView1.setText(MessageFormat.format("current Time : {0}", now));
    }

    class ThreadClass extends Thread{

        int a = 10;
        int b = 20;

        @Override
        public void run() {
            super.run();
            // 오래 걸리는 작업 가정
            while (isRunning) {
                SystemClock.sleep(100);
                long now = System.currentTimeMillis();
                Log.d("test", MessageFormat.format("current Time : {0}", now));
                SystemClock.sleep(500);
                handler.sendEmptyMessage(0);
                SystemClock.sleep(500);
                handler.sendEmptyMessage(1);

                //핸들러 요청을 할때 화면 처리를 위한 데이터를 전달한다. (정수 + 객체 까지 가능)

                SystemClock.sleep(500);
                Message msg = new Message();
                msg.what = 2;
                msg.arg1 = a++; //integer
                msg.arg2 = b++; //integer
                msg.obj = now; // object 전달 가능

                handler.sendMessage(msg);
            }
        }
    }

    class DisplayHandler extends Handler{
        // 개발자가 발생기킨 스레드에서 화면에 관련된 처리를 하기 위해 작업을 요청하면 자동으로 호출되는 메서드 (main method)
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 0:
                    long time = System.currentTimeMillis();
                    textView2.setText(MessageFormat.format("current Time : {0}", time)); // 안드로이드 버전에 따라 오류 발생 이떄 상속받은 핸들러 클래스를 사용한다.
                    break;
                case 1:
                    long time2 = System.currentTimeMillis();
                    textView1.setText(MessageFormat.format("current Time : {0}", time2)); // 안드로이드 버전에 따라 오류 발생 이떄 상속받은 핸들러 클래스를 사용한다.
                    break;
                case 2:
                    textView1.setText(MessageFormat.format("msg send : {0}", msg.arg1)); // 안드로이드 버전에 따라 오류 발생 이떄 상속받은 핸들러 클래스를 사용한다.
                    textView2.setText(MessageFormat.format("msg send : {0}", (long)msg.obj)); // 안드로이드 버전에 따라 오류 발생 이떄 상속받은 핸들러 클래스를 사용한다.
                    break;
                default:
                    break;
            }

        }
    }
}