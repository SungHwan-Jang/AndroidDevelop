package com.example.a11_excute_other_app_activity_sub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.text_view1);

        // intent를 추출한다.
        Intent intent = getIntent();

        int data1 = intent.getIntExtra("data1", 0);
        boolean data2 = intent.getBooleanExtra("data2", false);

        textView.setText("d1 : " + data1 + "d2 : " + data2);
    }

    public void btn_method(View view){
        Intent intent = new Intent();
        intent.putExtra("data1", 200);
        intent.putExtra("data2", true);

        setResult(RESULT_OK, intent);
        finish();
    }
}