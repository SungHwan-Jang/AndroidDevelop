package com.example.a7_excute_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// 안드로이드 4대 구성요소
// Activity : 화면을 관리하는 실행단위 - UI 처리에만 실행되고 나머지의 경우에는 종료된다.
// Service : 백그라운드에서 실행되는 실행단위 - 백그라운드에서 계속 실행된다.
// Broad Cast Receiver : Os가 전달하는 메시지를 전달 받아 실행되는 실행 단위
// - os 에서 발생되는 사건에 대해 처리 (평소에 동작X), 예를들어 배터리가 low 상태라면 모든 app 에 low 상태라고 알려준다.
// - 만일 해당 low batt broad cast receiver 가 코드상으로 존재한다면 해당 처리를 수행하게 된다. ( 그 사건이 언제 발생할지 모르므로 지속적 감시가 아닌 콜백 형태? )
// Content Provioder : 저장된 데이터를 제공하기 위한 실행단위 -
// - 예를 들어 안드로이드에서 저장된 sqlite 를 다른 앱에서 사용하고자 할때 사용되는 실행단위이다.

// Intent
// 다른 액티비티 창을 활성화 하기위한 동작 구조이다. 직접 호출이 아니라 os Intent 를 통한 호출이 가능하다.
// Activity A -> startActivity() -> Intent -> Android System (os) -> Intent -> onCreate() -> Activity B
// 구성요소를 실행하기 위한 메서드를 사용하여 사전에 Indent 객체를 만들어 새롭게 실행하고자 하는 구성요소 정보들을 Indent 에 전달해야 한다.
// Os가 Indent 를 분석하여 새로운 구성요소를 호출한다. ( 즉 Indent 는 실행 요청을 위한 정보를 담는 객체이다. )
// startActivity() : 지정된 Intent 에 담긴 정보를 토대로 Activity 를 실행한다.
// finish() : 현재 실행되어 있는 Activity 를 종료한다.

// Back Stack
// Activity 에서 다른 Activity 를 실행하면 이전 Activity 는 Back Stack 에 담겨 정지 상태가 되고 새로 실행된 Activity 가 활동 하게 된다.
// 새로 실행된 Activity 가 제거되면, Back Stack 에 있던 Activity 가 다시 활동하게 된다.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_method(View view){
        // intent 객체를 만들어서 실행한 액티비티의 정보를 셋팅한다.
        // ***** this 의 의미는 MainActivity 클래스와 같은 어플리케이션에 있는 activity 를 실행하겠다는 의미이다.
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}