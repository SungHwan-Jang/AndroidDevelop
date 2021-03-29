package com.example.a20_dialogfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// DialogFragment
// AlertDialog를 Fragment로 만들어 사용할 수 있도록 제공되는 Fragment이다. (AlertDialog와 큰 차이가 없다.)
// 현업에서 app 개발하다 보면 AlertDialog보다 DialogFragment를 사용하는것이 편할때가 종종 있다.
// AlertDialog는 os에서 제공하는 것만 사용 가능하지만, DialogFragment는 내부 코드를 수정 가능하다. (자유롭게 만들기 좋다.)
public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view_1);
    }

    public void btn_method(View view){
        //Dialog로 띄울 fragment의 객체를 생성한다.
        SubFragment subFragment = new SubFragment();
        FragmentManager manager = getSupportFragmentManager();
        // fragment dialog를 띄운다.
        subFragment.show(manager, "tag");
    }
}