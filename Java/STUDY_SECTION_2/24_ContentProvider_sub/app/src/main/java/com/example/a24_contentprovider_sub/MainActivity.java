package com.example.a24_contentprovider_sub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.scr_textView);
    }

    public void btn_method_save(View view){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("textData", "string_1");
        contentValues1.put("intData", "100");
        contentValues1.put("floatData", "11.11");
        contentValues1.put("dateData", date);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("textData", "string_2");
        contentValues2.put("intData", "200");
        contentValues2.put("floatData", "22.22");
        contentValues2.put("dateData", date);

        //test.dbprovider

        // 접속할 Content provider 의 이름이 담긴 객체를 생성한다.
        Uri uri = Uri.parse("content://test.dbprovider");
        // Content provider에 작업을 요청하기 위한 객체를 가져온다.
        ContentResolver resolver = getContentResolver();
        // 데이터 insert 작업을 요청한다.
        resolver.insert(uri, contentValues1);
        resolver.insert(uri, contentValues2);

        textView.setText("save complete");
    }

    public void btn_method_select(View view){
        Uri uri = Uri.parse("content://test.dbprovider");
        ContentResolver resolver = getContentResolver();
        //데이터를 받아온다.
        Cursor c = resolver.query(uri, null, null, null, null);

        textView.setText("");

        while(c.moveToNext()){
            int idxPos = c.getColumnIndex("idx");
            int textDataPos = c.getColumnIndex("textData");
            int intDataPos = c.getColumnIndex("intData");
            int floatDataPos = c.getColumnIndex("floatData");
            int dateDataPos = c.getColumnIndex("dateData");

            int idx = c.getInt(idxPos);
            String textData = c.getString(textDataPos);
            int intData = c.getInt(intDataPos);
            double floatData = c.getDouble(floatDataPos);
            String dateData = c.getString(dateDataPos);

            textView.append("idx : " + idx + "\n");
            textView.append("textData : " + textData + "\n");
            textView.append("intData : " + intData + "\n");
            textView.append("floatData : " + floatData + "\n");
            textView.append("dateData : " + dateData + "\n\n");
        }
    }

    public void btn_method_update(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("textData", "string_modified");
        String where = "idx=?";
        String [] args = {"1"};

        Uri uri = Uri.parse("content://test.dbprovider");
        ContentResolver resolver = getContentResolver();

        resolver.update(uri, contentValues, where, args);

        textView.setText("complete modifying action");
    }

    public void btn_method_delete(View view){
        String where = "idx=?";
        String [] args = {"1"};

        Uri uri = Uri.parse("content://test.dbprovider");
        ContentResolver resolver = getContentResolver();
        resolver.delete(uri, where, args);
        textView.setText("complete delete action");
    }
}