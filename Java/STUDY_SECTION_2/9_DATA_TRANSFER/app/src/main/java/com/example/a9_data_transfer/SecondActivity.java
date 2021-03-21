package com.example.a9_data_transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;

public class SecondActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.second_text_view1);
        // 현재 액티비티를 실행화기 위해 사용된 Intent 객체를 추출한다.
        Intent intent = getIntent();
        // 저장한 데이터를 가져온다. ( name에 해당하는 값이 없다면 defaultValue가 기본값이 된다. )
        int data1 = intent.getIntExtra("data1", 0);
        double data2 = intent.getDoubleExtra("data2", 0.0);
        boolean data3 = intent.getBooleanExtra("data3", false);
        String data4 = intent.getStringExtra("data4");

        textView.setText(MessageFormat.format("data1 : {0}\n", data1));
        textView.append(MessageFormat.format("data1 : {0}\n", data2));
        textView.append(MessageFormat.format("data1 : {0}\n", data3));
        textView.append(MessageFormat.format("data1 : {0}\n", data4));
    }

    public void btn_method(View view){
        // 돌아갈 때 전달할 데이터를 세팅
        Intent intent = new Intent();
        intent.putExtra("val1", 10);
        intent.putExtra("val2", true);
        intent.putExtra("val3", "finish activity2");

        setResult(RESULT_OK, intent);
        finish();
    }
}