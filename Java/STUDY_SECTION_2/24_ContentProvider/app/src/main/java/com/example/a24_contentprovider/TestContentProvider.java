package com.example.a24_contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

// URI authorities : test.dbprovider
public class TestContentProvider extends ContentProvider {
    public TestContentProvider() {
    }

    // 삭제될 low 갯수가 반환
    // delete 호출 시 자동 호출
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        DBHelper helper =new DBHelper(getContext(), null, null, 0);
        SQLiteDatabase db = helper.getWritableDatabase();

        int cnt = db.delete("TestTable", selection, selectionArgs);
        return cnt;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // 다른 app 에서 insert 작업을 요청하면 호출되는 메서드
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //throw new UnsupportedOperationException("Not yet implemented");

        //activity는 자체가 context 상속받아서 this로 context 넘길수 있지만,
        // provider에서는 context를 추출해야만 사용 가능하다.
        DBHelper helper =new DBHelper(getContext(), null, null, 0);
        SQLiteDatabase db = helper.getWritableDatabase();

        // 저장 처리
        db.insert("TestTable", null, values);
        Log.d("test", "insert content provider");
        return uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    // 다른 app에서 select 작업 요청시 호출되는 메서드
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");

        // projection - 가져올 컬럼 이름 목록
        // selection - 조건절
        // selectionArgs - 조건절 ?에 들어가는 값
        // sortOrder - 정렬기준

        DBHelper helper = new DBHelper(getContext(), null, null, 0);
        SQLiteDatabase db = helper.getWritableDatabase();

        // null 이 전달될 경우 모두 다 선택된다.
        Cursor c = db.query("TestTable", projection, selection, selectionArgs, null, null, sortOrder);

        return c;
    }

    // update
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");

        DBHelper helper = new DBHelper(getContext(), null, null, 0);
        SQLiteDatabase db = helper.getWritableDatabase();

        int cnt = db.update("TestTable", values, selection, selectionArgs);

        return cnt;
    }
}