package com.example.a10_obj_transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.second_text_view1);
        Intent intent = getIntent();

        // 클래스의 CREATOR.createFromParcel() 메서드를 호출하여 반환값을 받는다.
        // 즉 객체 자체를 받아내는건 아니고 객체의 멤버변수를 받아서 새로운 객체를 만든다.
        TestClass t1 = intent.getParcelableExtra("test1");
        textView.setText("t1 data10 : " + t1.data10 + "t2 data20 : " + t1.data20);
    }

    public void btn_method(View view){
        Intent intent = new Intent();

        TestClass t2 = new TestClass();
        t2.data10 = 200;
        t2.data20 = "return gogo";

        //intent.putExtra("test1", t2); // test1 쓰고 있더라도 상관없는듯..
        intent.putExtra("test2", t2); // test1 쓰고 있더라도 상관없는듯..
        setResult(RESULT_OK, intent);

        finish();
    }
}