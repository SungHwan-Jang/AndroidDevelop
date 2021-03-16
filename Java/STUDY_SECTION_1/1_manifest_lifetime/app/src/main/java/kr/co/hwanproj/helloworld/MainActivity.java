package kr.co.hwanproj.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

// 안드로이드의 4대 구성요소
// Activity - UI Form 현재 보이는 화면을 관리하는 실행 단위
// Service - Background worker
// Broadcast Receiver - event
// Content Provider - data region

// 실행 - onCreate() -> onStart() -> onResume()
// 뒤로가기(정지) - onPause() -> onStop() -> onDestroy()
// 백그라운드 불러오기 - onRestart() -> onStart() -> onResume()
// 백그라운드화 - onPause() -> onStop()



public class MainActivity extends AppCompatActivity {

    @Override
    // Activity가 생성될 때 자동으로 호출된다.
    // 화면 회전이 발생했을 때 자동으로 호출된다.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //R 은 res, layout은 layout 폴더의 activity_main.xml을 열게 된다.
        System.out.println("Call onCreate()");
        Log.d("test","Call onCreate()");
    }

    @Override
    // onCreate() 메서드 호출 이후에 자동으로 호출
    // Activity가 정지 상태가 되었다가 활동상태로 돌아올때 호출 (바탕 나갔다가 [완전 정지] 다시 활성화 한 경우)
    protected void onStart() {
        super.onStart();
        Log.d("test","Call onStart()");
    }

    @Override
    // onStart() 메서드가 호출된 이후에 자동으로 호출된다.
    // Activity가 일시 정지 되었다가 다시 돌아 올때 호출 (팝업창이 뜬 경우)
    protected void onResume() {
        super.onResume();
        Log.d("test","Call onResume()");
    }

    @Override
    // Activity가 정지 상태가 되었다가 활동 상태로 돌아갈 때 onStart() 메서드 전에 호출된다.
    protected void onRestart() {
        super.onRestart();
        Log.d("test","Call onRestart()");
    }

    @Override
    // Activity가 일시 정지 상태가 될때 호출된다.
    // 화면상에서 완전히 사라지거나 현재 화면 위에 작은 팝업창 같은것이 나타날때 호출
    protected void onPause() {
        super.onPause();
        Log.d("test","Call onPause()");
    }

    @Override
    //Activity가 화면상에서 사라질 때 호출된다.
    protected void onStop() {
        super.onStop();
        Log.d("test","Call onStop()");
    }

    @Override
     // 현재 액티비티의 수행이 완전히 종료되어 메모리 상에서 제거될 때 호줄
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Call onDestroy()");
    }
}