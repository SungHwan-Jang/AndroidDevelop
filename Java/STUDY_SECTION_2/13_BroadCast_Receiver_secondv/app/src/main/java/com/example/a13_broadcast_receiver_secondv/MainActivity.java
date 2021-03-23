package com.example.a13_broadcast_receiver_secondv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_method(View view){
        // 명시적 인텐트 방식
        Intent intent = new Intent();
        // 어떤 어플에 있는 (package - manifest 설정에 app 이름 확인)
        // 어떤 리시버(class Name - broadcast package name - app1의 broadcast 의 최상단 확인 및 클래스 네임 복사)를 동작 시킨다
        intent.setClassName("com.example.a13_broadcast_receiver", "com.example.a13_broadcast_receiver.TestReceiver");
        sendBroadcast(intent);
    }

    public void btn_method_2(View view){
        // 암시적 인텐트 방식 (모든 리시버 동작)
        // app1의 manifest에서 intent filter 설정 후 name 등록 한뒤에 가져온다. ( 해당 코드는 8 이상 버전에서 동작하지 않는다 )
        // 안드로이드 8.0 이후 제약사항이다.
        // 안드로이드는 백그라운드 실행이 자유롭다는 점에서 개발의 자율성이 높지만 높은 하드웨어 사양을 요구한다는 단점을 지니고 있다.
        // 따라서 8.0 이후 버전에서는 일부를 제외한 모든 Broad Cast Receiver는 암시적 인텐트로 실행할 경우 반드시 코드를 통하여
        // 리시버를 등록하는 작업을 해야만 한다. 이는 Broad Cast Reciver를 소유한 app이 실행 중에만 사용 할 수 있도록 제한한 것이다. ( batt감지, 문자수신 감지 등 제외 )
        Intent intent = new Intent("android.intent.action.testBR");
        sendBroadcast(intent);
    }
}