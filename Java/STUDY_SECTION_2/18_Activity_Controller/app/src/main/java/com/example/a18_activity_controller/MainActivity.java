package com.example.a18_activity_controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
// mvc pattern : model - view - controller 형태
// 화면들을 관리하는 것을 controller 라고 한다.
// 눈에 보이는 실제 화면을 관리하는것을 view
// data를 관리하는것을 model이라고 한다.
// 예를들어 Fragment로 모든 화면을 구성한다면, activity가 controller, fragment 가 view가 된다.

// ** Activity의 역할 - 각 Fragment를 교환하고 관리하는 역할 / Fragment들이 사용할 데이터를 관리하는 역할 **

enum FRAGMENT_T {
    INPUT_FRAGMENT,
    OUTPUT_FRAGMENT,
};

public class MainActivity extends AppCompatActivity {
    // 관리할 fragment 객체
    InputFragment inputFragment = new InputFragment();
    OutputFragment outputFragment = new OutputFragment();

    // fragment들이 공통적으로 사용할 값을 저장할 변수
    String value1;
    String value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        set_fragment(FRAGMENT_T.INPUT_FRAGMENT);
    }

    //보여줄 fragment를 관리하는 메서드
    public void set_fragment(FRAGMENT_T name){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // fragmet 이름으로 분기한다.

        switch (name){
            case INPUT_FRAGMENT:
                transaction.replace(R.id.main_container, inputFragment);
                break;
            case OUTPUT_FRAGMENT:
                transaction.replace(R.id.main_container, outputFragment);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }
}