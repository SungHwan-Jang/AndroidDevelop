package com.example.a11_execute_other_app_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
// Intent Filter
// 안드로이드의 4대 구성요소는 모두 AndroidManifest.xml 파일에 기록되어야 한다.
// 이때 다른 애플리케이션이 실행할 수 있도록 하고자한다면, Intent Filter를 이용해 이름을 설정해주면 된다.

public class MainActivity extends AppCompatActivity {

    // 뷰의 주소값을 담을 참조변수
    TextView textView = null;
    final int SECOND_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
    }

    public void btn_method(View view){
        // 다른 애플리케이션의 이름을 셋팅한 intent 객체를 세팅한다.
        Intent intent = new Intent("com.test.testActivity"); // 만일 app3이 존재하며 action 이름이 동일할시에는 어떤 app 을 실행할지 고르는 창이 뜬다.
        intent.putExtra("data1", 100);
        intent.putExtra("data2", true);

        startActivityForResult(intent, SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SECOND_ACTIVITY){
            if(resultCode == RESULT_OK){
                int val1 = data.getIntExtra("data1", 0);
                boolean val2 = data.getBooleanExtra("data2", false);

                textView.setText("d1 : " + val1 + "d2 : " + val2);
            }
        }
    }
}