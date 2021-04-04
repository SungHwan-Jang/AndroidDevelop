package com.example.a23_sqlite_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// SQlite 사용시, 표준 SQL 쿼리문 사용대신 사용할 수 있는 클래스를 제공하고 있다.
// class 나 SQL 문 사용방법 중 편한 방법을 사용한다.
public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view1);
    }

    public void btn_method_insert(View view){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(new Date());

//        insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)
        ContentValues cv1 = new ContentValues();
        cv1.put("textData", "string_1");
        cv1.put("intData", "100");
        cv1.put("floatData", "11.11");
        cv1.put("dateData", date);

        //nullColumnHack : 해당 컬럼에 값을 안넣었을 때, 기본값을 의미한다.
        db.insert("TestTable", null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put("textData", "string_1");
        cv2.put("intData", "100");
        cv2.put("floatData", "11.11");
        cv2.put("dateData", date);

        db.insert("TestTable", null, cv2);

        db.close();
        textView.setText("save complete");
    }

    public void btn_method_select(View view){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

//        "Select 컬럼명들 from 테이블명 where 조건절 group by 기준컬럼 having 조건절 order by 컬럼명";
        // 1: table 명,
        // 2: 컬럼 이름이 담긴 문자열 배열 (null 은 모든 컬럼)
        // 3: 조건 절
        // 4: 조건 절 ? 에 바인딩 될 값의 배열
        // 5: group by 절 기준 컬럼
        // 6: having 절에 들어갈 조건절
        // 7: 정렬기준
        Cursor c = db.query("TestTable", null, null, null, null, null, null);

        textView.setText("");

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

    public void btn_method_update(View view){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

//        "update table명 set column = 값, column = 값 where 조건절"

        ContentValues cv = new ContentValues();
        cv.put("textData", "string_changed");

        String where = "idx=?";
        String[] args = {"1"};

        db.update("TestTable", cv, where, args);
        db.close();

        textView.setText("modified complete");
    }

    public void btn_method_delete(View view){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String where = "idx=?";
        String[] args = {"1"};

        db.delete("TestTable", where, args);
        db.close();

        textView.setText("delete complete");
    }
}