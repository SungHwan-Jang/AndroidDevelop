package com.example.a12_activity_action;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
// 안드로이드에서 제공되는 기본 애플리케이션 중 다른 애플리케이션이 사용할 수 있도록
// Activity가 제공되는 것들이 있다.
// 이들은 모두 공개되어 있는 이름이 제공되며 이를 이용해 실행할 수 있다.

public class MainActivity extends AppCompatActivity {

    // 승인받을 권한 목록
    final String [] permissionList = {
            "android.permission.CALL_PHONE",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_permission();
    }

     public void btn_method_1(View view){
        // 구글 지도를 통해 보여줄 위도와 경도를 셋팅한다.
         Uri uri = Uri.parse("geo:37.234343,131.861601");
         //Intent.ACTION_VIEW의 경우 뭔가를 보여주는 경우에 실행되는 Activity이다.
         Intent intent = new Intent(Intent.ACTION_VIEW, uri); //uri 스키마가 geo 이므로 구글맵 실행
         startActivity(intent);
     }

     public void btn_method_2(View view){
        // browser를 통해 보여줄 페이지의 주소를 세팅한다.
         Uri uri = Uri.parse("http://developer.android.com");
         Intent intent = new Intent(Intent.ACTION_VIEW, uri);
         startActivity(intent);
     }

     public void btn_method_3(View view){
        //전화번호를 셋팅한다.
         Uri uri = Uri.parse("tel:01090765675");
         Intent intent = new Intent(Intent.ACTION_DIAL, uri);
         startActivity(intent);
     }

     public void btn_method_4(View view){
        // 전화를 걸어 버린다. - permission 필요
         Uri uri = Uri.parse("tel:01090765675");
         Intent intent = new Intent(Intent.ACTION_CALL, uri);
         startActivity(intent);
     }

     public void check_permission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }

        for(String permission : permissionList){
            int check = checkCallingOrSelfPermission(permission);
            if(check == PackageManager.PERMISSION_DENIED){
                requestPermissions(permissionList, 0);
                break;
            }
        }
     }
}