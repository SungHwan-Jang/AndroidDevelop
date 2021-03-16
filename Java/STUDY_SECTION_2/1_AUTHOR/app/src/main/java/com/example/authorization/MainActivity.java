package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text_view;

    String[] permission_list = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_view = (TextView) findViewById(R.id.textView);
        checkPermission();
    }

    public void checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            // 권한 허용 여부를 확인한다.
            int check = checkCallingOrSelfPermission(permission);

            if(check == PackageManager.PERMISSION_DENIED){
                // 사용자에게 권한 허용여부를 확인하는 창을 띄운다.
                requestPermissions(permission_list, 0);
            }
        }
    }

    // requestPermissions(permission_list, 0); 의 requestCode 가 아래 메서드의 requestCode로 들어간다.
    // requestCode별 상황처리문 삽입을 해야한다.
    // 권한 확인 여부가 완료되면 호출되는 메서드이다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        text_view.setText("");

        // 사용자가 권한 허용 여부를 확인한다.
        for(int i=0; i<grantResults.length; i++){
            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                text_view.append(permissions[i] + " 허용됨\n");
            }
            else{
                text_view.append(permissions[i] + " 거부됨\n");
            }
        }
    }
}