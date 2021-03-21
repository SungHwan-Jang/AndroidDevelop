package com.example.a9_data_transfer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;
// activity 를 실행하기 위해 사용하는 intent 객체에 putExtra 메서드를 이용하여 데이터를 세팅하면
// 실행되는 activity 에서 데이터를 전달받을 수 있다.

// putExtra 메서드는 자료형 별로 메서드가 제공되기 때문에 타입을 가리지 않는다.
public class MainActivity extends AppCompatActivity {
    // 뷰의 주소값을 담을 참조 변수
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view1);
    }

    public void btn_method(View view){
        // intent 생성

        Intent intent = new Intent(this, SecondActivity.class);
        // second Activity로 전달할 데이터를 세팅

        intent.putExtra("data1", 100);
        intent.putExtra("data2", 11.11);
        intent.putExtra("data3", true);
        intent.putExtra("data4", "string data");
        // run activity
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // back 버튼 말고 act2의 버튼을 클릭한 경우에만 처리하려 한다 (RESULT_OK 수신)

        if(resultCode == RESULT_OK){
            int val1 = data.getIntExtra("val1", 0);
            boolean val2 = data.getBooleanExtra("val2", false);
            String val3 = data.getStringExtra("val3");

            textView.setText(MessageFormat.format("data1 : {0}\n", val1));
            textView.append(MessageFormat.format("data2 : {0}\n", val2));
            textView.append(MessageFormat.format("data3 : {0}\n", val3));
        }
    }
}