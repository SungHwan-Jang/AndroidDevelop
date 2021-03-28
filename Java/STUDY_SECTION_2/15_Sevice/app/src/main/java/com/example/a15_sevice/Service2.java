package com.example.a15_sevice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.JobIntentService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */

// intent service는 어떠한 처리를 새로 Thread를 생성하여 처리할 수 있도록 기본 정의된 구조이다.

public class Service2 extends IntentService {

    public Service2() {
        super("Service2");
    }

    // onHandleIntent는 별도의 스레드가 발생되어 처리될 메서드이다.
    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i =0 ; i<10; i++){
            SystemClock.sleep(1000);
            long time = System.currentTimeMillis();
            Log.d("test", "intent service time : " + time);
        }
    }
}