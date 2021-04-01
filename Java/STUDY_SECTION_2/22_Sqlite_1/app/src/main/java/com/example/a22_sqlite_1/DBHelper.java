package com.example.a22_sqlite_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

//    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        // param[0] activity가 context를 상속받고 있기 때문에 activity (context)를 넘겨 주어야 한다.
//        // param[1] = 사용할 db 이름
//        // param[2] = DB 오픈할때 사용하는 factory class를 지정 null로 해도 됨
//        // param[3] = version 은 이후에 확인
////        super(context, name, factory, version);
//        super(context, "test.db", null, 1);
//
//    }


    // 최초 app 실행시 생성자 실행되며 부모 클래스에서 name 전달시 해당 name에 해당하는 파일을 만든다. - 이후에는 db가 있기 떄문에 새롭게 만들수가 없다.
    public DBHelper(@Nullable Context context) {
        // param[0] activity가 context를 상속받고 있기 때문에 activity (context)를 넘겨 주어야 한다.
        // param[1] = 사용할 db 이름
        // param[2] = DB 오픈할때 사용하는 factory class를 지정 null로 해도 됨
        // param[3] = version 은 db의 버전을 의미한다. test.db가 생성시 버전이 1로 생성된다. table의 구조가 변경이 되어야 하는 경우 version이 변경되어야 한다.
        //            db가 생성된 경우 onCreate는 다시는 호출되지 않는다. 따라서 onUpgrade 메서드가 필요하다.
//        super(context, name, factory, version);
        super(context, "test.db", null, 1);

    }

    // onCreate의 경우
    // db있는지 확인 후에 있으면 아무 일도 없고
    // 없으면 db파일을 만들고 onCreate 메소드를 호출한다. ( 없을 경우에만 단 한번 호출이 된다. )
    // table 생성, 기타 필요한 작업 (초기 데이터 삽입 등 )을 수행하면 된다.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("test", "created data base");
        // 테이블 구조를 만든다.
        String sql = "create table TestTable("
                + "idx integer primary key autoincrement, "
                + "textData text not null, "
                + "intData integer not null, "
                + "floatData real not null, "
                + "dateData not null"
                + ")";

        db.execSQL(sql);

    }

    // table 구조가 바뀌는 database version 변경에 따라 필요한 메서드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("test", "old version : "+ oldVersion);
        Log.d("test", "new version : "+ newVersion);

        switch (oldVersion){
            case 1:
                // 1에서 2구조로 테이블 구조를 변경시키는 작업을 한다.
            case 2:
                // 2에서 3구조로 테이블 구조를 변경시키는 작업을 한다.
            case 3:
                // 한번 table 이름 넣기 실패하면 app 지우고 다시 ... (table search 안되는 경우.. )
//                String sql = "create table TestTable("
//                        + "idx integer primary key autoincrement, "
//                        + "textData text not null, "
//                        + "intData integer not null, "
//                        + "floatData real not null, "
//                        + "dateData not null"
//                        + ")";
//
//                db.execSQL(sql);
                // ....
        }
    }
}
