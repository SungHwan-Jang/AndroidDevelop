package com.example.a5_async_task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;

// AsyncTask는 비 동기 처리를 위해 제공되는 클래스이다.
// 개발자가 발생시키는 쓰레드와 핸들러의 조합으로 쓰레드 운영중 화면 처리가 가능했던 구조를 클래스로 제공한다.
//
// onPreExecute : doInBackground 메서드가 호출되기 전에 호출되는 메서드. main thread가 처리한다.
// doInBackground : 일반 스레드에서 처리한다.
// onProgressUpdate : doInBackground 메서드에서 publishProgress 메서드를 호출하면 main thread가 처리하는 메서드, doInBackground 메서드 내에서 화면 처리가 필요할 때 사용한다.
// onPostExecute : doInBackground 메서드 수행 완료 후 호춣, main thread가 처리한다.
//

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);

        AsyncTask asyncTask = new AsyncTaskClass();
        // AsyncTask를 가동한다. 매개 변수의 값은 doInBackground 메서드로 전달된다.
        asyncTask.execute(10, 20);
    }

    public void btn_method(View view){
        long now = System.currentTimeMillis();
        textView1.setText(MessageFormat.format("time :{0}", now));
    }

    //실제 AsyncTask는 사용하면 안됨 deprecated 됨. !!!! rxJava, coroutine를 사용하여 구현함.
    // 1번 제네릭 : excute 메서드의 매개 변수., doInBackground의 매개변수타입
    // 2번 제네릭 : onProgressUpdate 의 매개변수타입
    // 3번 제네릭 : doInBackground 메서드의 반환타입, onPostExecute 메서드의 매개변수타입
    class AsyncTaskClass extends AsyncTask<Integer, Long, String>{ // generic ???

        // onPreExecute : doInBackground 메서드가 호출되기 전에 호출되는 메서드. main thread가 처리한다.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView2.setText("async text start");
        }
        // doInBackground : 일반 스레드에서 처리한다. ( 오래걸리는 작업 처리 ex network 작업, 화면 처리는 불가능 , 일반 스레드 생성하여 처리하는것과 동일함 )
        @Override
        protected String doInBackground(Integer... integers) {
            int val1 = integers[0];
            int val2 = integers[1];

            for(int i=0; i<10; i++){
                SystemClock.sleep(1000);
                val1++;
                val2++;
                Log.d("test", i + " : " + val1 + " : " + val2);

                //화면 처리
                long now = System.currentTimeMillis();
                publishProgress(now); // 해당 메서드에서 onProgressUpdate 가 실행된다. 두번째 제네릭은 long type 전달.
            }
            return "termination";
        }
        // onProgressUpdate : doInBackground 메서드에서 publishProgress 메서드를 호출하면 main thread가 처리하는 메서드,
        // doInBackground 메서드 내에서 화면 처리가 필요할 때 사용한다.
        // 오래 걸리는 작업을 하면 안된다.
        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            textView2.setText(MessageFormat.format("async : {0}", values[0]));
        }
        // onPostExecute : doInBackground 메서드 수행 완료 후 main thread가 호춣, main thread가 처리한다.
        // doInBackground method 의 반환타입을 파라미터로 받는다.
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView2.setText(s);
        }
    }
}