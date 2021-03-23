package com.example.a13_broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

// Broad cast receiver ( 안드로이드 8 이후로 방식이 많이 바뀌었다. 8 이전과 이후를 모두 생각해야 한다. )
// 안드로이드가 1 이라는 방송을 하면 1을 수신하면 반응하는 ( 평소에는 동작하지 않다가 수신시 동작하게 등록해놔야 함 ) 리시버가 동작하는 구조
// 예를 들면, 문자 메시지가 수신되는 순간 처리 방법에 적용 ( 언제 발생할지 모르는 상황 )

// 명시적 인텐트
// - 안드로이드의 4대 구성요소 중 하나를 동작시키기 위해 해당 클래스명을 직접 기술하여 동작시키는 것을 의미한다.
// - 한번에 하나만 실행이 가능하다.

// 암시적 인텐트
// - 안드로이드의 4대 구성요소 중 원하는 구성요소를 실행하기 위해 Intent Filter를 통해 설정한 이름을 이용한 것을 의미한다.
// - 동일한 이름이 여러개 있을 경우에는 Activity의 경우에는 선택 창이 뜨며, 그 외의 경우에는 모두 실행된다.

public class MainActivity extends AppCompatActivity {
    TestReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 안드로이드드 8.0 이상이면 암시적 인텐트를 코드로 등록해야 한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            br = new TestReceiver();
            IntentFilter filter = new IntentFilter("android.intent.action.testBR"); // register broad cast manifest action name
            registerReceiver(br, filter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(br != null){
                unregisterReceiver(br);
                br = null;
            }
        }
    }
}