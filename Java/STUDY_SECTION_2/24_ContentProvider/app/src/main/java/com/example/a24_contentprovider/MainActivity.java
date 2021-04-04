package com.example.a24_contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// 안드로이드 4대 구성요소 중 하나로 app이 저장한 데이터를 다른 app이 사용할 수 있도록 제공하는 개념이다.
public class MainActivity extends AppCompatActivity {

    TextView scrTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrTextView = findViewById(R.id.scroll_text_view_1);

    }

    public void btn_method_select(View view){
        DBHelper helper = new DBHelper(this, null, null, 0);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 안정성을 위해 TestTable이 존재하는지 확인 code를 추가해야 한다.
        String sql = "select * from TestTable";

        Cursor c = db.rawQuery(sql, null);

        scrTextView.setText("");

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

            scrTextView.append("idx : " + idx + "\n");
            scrTextView.append("textData : " + textData + "\n");
            scrTextView.append("intData : " + intData + "\n");
            scrTextView.append("floatData : " + floatData + "\n");
            scrTextView.append("dateData : " + dateData + "\n\n");
        }

        db.close();
    }
}