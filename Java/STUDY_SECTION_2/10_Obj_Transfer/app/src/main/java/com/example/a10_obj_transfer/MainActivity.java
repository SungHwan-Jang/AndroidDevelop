package com.example.a10_obj_transfer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
// *** Parcleable
// Intent를 통해 객체를 전달 할 때는 Parcleable 인터페이스를 구현한 객체만 가능하다.
// Parcelable 인터페이스는 전달 받는 쪽에서 객체를 복원할 때 필요한 정보를 가진 부분을 의미한다.

public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view1);
    }

    public void btn_method(View view){
        Intent intent = new Intent(this, SecondActivity.class);

        TestClass t1 = new TestClass();
        t1.data10 = 100;
        t1.data20 = "string1";

        // TestClass가 가지고 있는 writeToParcel() 메서드 호출출
       intent.putExtra("test1", t1);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            TestClass tRet = data.getParcelableExtra("test2");
            textView.setText("tRet 10: " + tRet.data10 + "tRet 10: " + tRet.data20);
        }
    }
}