package com.example.a22_sqlite_1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
// 안드로이드에서 사용하는 내장 DB로 표준 SQL 문을 사용하는 관계형 DB이다.
// MySQL과 유사한 문법을 사용하고 있으며 일반적인 관계형 데이터 베이스가 가지고 있는 기능을 가지고 있다.

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view_1);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase(); // onCreate 호출 - 열려있지 않은 경우에만
    }

    public void btn_insert_method(View view){
        // database open
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)";

        // 데이터 준비
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(new Date());

        // ? 에 바인딩 될 값 배열
        String [] arg1 = {"test_string_data_1", "100", "11.11", date};
        String [] arg2 = {"test_string_data_2", "200", "22.22", date};

        // 저장한다.
        db.execSQL(sql, arg1);
        db.execSQL(sql, arg2);

        db.close();

        textView.setText("save complete");
    }

    public void btn_get_method(View view) {
        // database open
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "select * from TestTable";
        String [] arg1 = {}; // 조건절
        // 쿼리 실행
        //Cursor c = db.rawQuery(sql, arg1);
        Cursor c = db.rawQuery(sql, null); // 조건절이 없다면 null을 넣으면 된다.

        textView.setText("");

        // 선택된 raw를 차례로 가져와서 데이터를 전부 표시
        while(c.moveToNext()){
            // 가져올 컬럼의 인덱스 번호를 추출한다.
            int idx_pos = c.getColumnIndex("idx");
            int textData_pos = c.getColumnIndex("textData");
            int intData_pos = c.getColumnIndex("intData");
            int floatData_pos = c.getColumnIndex("floatData");
            int dateData_pos = c.getColumnIndex("dateData");

            // 컬럼 인덱스 번호를 통해 데이터를 가져온다.
            int idx = c.getInt(idx_pos);
            String textData = c.getString(textData_pos);
            int intData = c.getInt(intData_pos);
            double floatData = c.getDouble(floatData_pos);
            String dateData = c.getString(dateData_pos);

            textView.append("idx : " + idx + "\n");
            textView.append("text data : " + textData + "\n");
            textView.append("int data : " + intData + "\n");
            textView.append("float data : " + floatData + "\n");
            textView.append("date data : " + dateData + "\n");
        }

        db.close();
    }

    public void btn_update_method(View view) {
        // database open
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "update TestTable set textData = ? where idx = ?";
        String [] args = {"update check", "1"};

        db.execSQL(sql, args);
        db.close();

        textView.setText("update complete");

    }

    public void btn_delete_method(View view){
        // database open
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "delete from TestTable where idx=?";
        String [] args = {"1"};

        db.execSQL(sql, args);
        db.close();

        textView.setText("delete complete");
    }
}