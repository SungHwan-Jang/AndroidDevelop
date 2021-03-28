package com.example.a15_sevice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

// 백그라운드 처리를 위해 사용한다. Service
public class Service1 extends Service {
    public Service1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // service 가 가동될때 호출되는 메서드

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test", "service start");
        // thread run
        // 아래와 같이 사용해도 되지만, 실제 thread로 service 구현할때는 intent service로 생성한다.
        // thread가 구현되어있는 형태이다.
        ThreadClass thread = new ThreadClass();
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    // service가 중지되면 호출되는 메서드

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test", "service destroy");
        // Destroy() 되더라도 thread는 동작을 마무리 하게 된다.
    }

    class ThreadClass extends Thread{
        @Override
        public void run() {
            super.run();
            for(int i =0; i<10; i++){
                SystemClock.sleep(1000);
                long time = System.currentTimeMillis();
                Log.d("test", "Service time : "+ time);
            }
        }
    }
}