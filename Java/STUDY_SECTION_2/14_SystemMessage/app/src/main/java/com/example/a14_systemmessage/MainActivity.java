package com.example.a14_systemmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

// 안드로이드에서는 단말기에서 사건이 발생했을 경우 각 사건마다 정해놓은 이름으로 시스템 메시지를 발생시킨다.
// 이 메시지와 동일한 이름으로 설정되어 있는 BroadCastReceiver를 찾아 개발자가 만든 코드를 동작시킬 수 있다.
// 안드로이드 8.0 부터는 사용할 수 있는 시스템 메시지의 수가 줄어들었다.

public class MainActivity extends AppCompatActivity {

    // 확인 받을 권한 목록ㄹ
    String [] permissonList = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_permisson();
    }

    public void check_permisson(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }

        for(String permission : permissonList){
            int check = checkCallingOrSelfPermission(permission);
            if(check == PackageManager.PERMISSION_DENIED){
                requestPermissions(permissonList, 0);
            }
        }
    }
}