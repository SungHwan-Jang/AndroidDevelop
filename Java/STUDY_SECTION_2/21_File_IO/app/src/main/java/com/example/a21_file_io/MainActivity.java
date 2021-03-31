package com.example.a21_file_io;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// 데이터를 영구적으로 보존할 필요가 있을경우,
// 안드로이드는 app이 데이터를 저장할 수 있는 저장소를 두 가지로 제공하고 있다.
// 내부 저장소 : app을 통해서만 접근이 가능하다.
// 외부 저장소 : 단말기 내부의 공유 영역으로 모든 app이 접근 가능하다. 단말기를 컴퓨터에 연결하면 탐색기를 통해 접근할 수 있는 영역을 의미한다.

// 내부 저장소 : openFileOutput, openFileInput
// 외부 저장소 : FileInputStream, FileOutputStream
// 외부 저장소 저장시, "android/data/"package name"" 폴더에 저장하면 app 삭제시 같이 삭제가 되며 그외에 폴더는 유지된다.
// <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
//    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/> 외부저장소 이용시 필요하다.

public class MainActivity extends AppCompatActivity {

    TextView textView;

    String [] permissonList = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view_1);

        check_permisson();

        // 저장할 외부 저장소의 경로를 구한다.
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            Toast toast =  Toast.makeText(this,"ok", Toast.LENGTH_LONG);
            toast.show();
        }
        // 아래와 같이 외부 저장소 이용하자.
//        public void writeFile() {
//            String fileTitle = "title.txt";
//            File file = new File(Environment.getExternalStorageDirectory(), fileTitle);
//
//            try {
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                FileWriter writer = new FileWriter(file, false);
//                String str = "      저장할 내용      ";
//                writer.write(str);
//                writer.close();
//            } catch (IOException e) {
//
//            }
//        }
//        File file = Environment.getExternalStorageDirectory();  // Deprecated
        File file = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "test");
        String absoluteDirPath = file.getAbsolutePath();
        //String packageName = getPackageName();
        path = absoluteDirPath;

        //path = absoluteDirPath + "/android/data/" + packageName + "/";
    }

    public void btn_method_1(View view){
        try{
            // 내부 저장소와 연결되어 있는 쓰기 스트림 추출
            // MODE_PRIVATE : 이전 내역 덮어쓰기 , MODE_APPEND : 추가하기
            FileOutputStream fos = openFileOutput("myFile.dat", MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);
            // data를 쓴다
            dos.writeInt(100);
            dos.writeDouble(11.11);
            dos.writeBoolean(true);
            dos.writeUTF("string_1");

            dos.flush();
            dos.close();

            textView.setText("finish writing inner storage");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void btn_method_2(View view){
        try{
            FileInputStream fis = openFileInput("myFile.dat");
            DataInputStream dis = new DataInputStream(fis);

            int data1 = dis.readInt();
            double data2 = dis.readDouble();
            boolean data3 = dis.readBoolean();
            String data4 = dis.readUTF();

            dis.close();

            textView.setText("data1 : " + data1);
            textView.append("data2 : " + data2);
            textView.append("data3 : " + data3);
            textView.append("data4 : " + data4);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btn_method_3(View view){
        try {
            check_externel_dir();

            FileOutputStream fos = new FileOutputStream(path + "/sd_file.dat");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF("test external src");
            dos.flush();
            dos.close();

            textView.setText("finish write external file");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btn_method_4(View view){
        try {
            check_externel_dir();

            FileInputStream fis = new FileInputStream(path + "/sd_file.dat");
            DataInputStream dis = new DataInputStream(fis);

            String data = dis.readUTF();
            dis.close();

            textView.setText("read external data : " + data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void check_permisson(){
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            return;
        }

        for(String permisson : permissonList){
            int check = checkCallingOrSelfPermission(permisson);
            if(check == PackageManager.PERMISSION_DENIED){
                requestPermissions(permissonList, 0);
                break;
            }
        }
    }

    // 저장공간에 대한 access 는 denied 된것같다.. 이후 한번 더 확인 필
    // **************************************************************
    // android 버전업으로 인한 영향인건지... 기존 getExternal.. 은 Deprecated 되었다.
    private void check_externel_dir(){
        // 외부 저장소 경로가 있는지 확인하고 없으면 생성한다.
        File file = new File(path);

        if(!file.exists()){
            if(!file.mkdir()){
                Log.d("test", "dir not created");
            }
        }

    }
}