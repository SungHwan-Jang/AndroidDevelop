package com.example.a13_broadcast_receiver; // 최상단 클래스 네임 확인

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        String str = "broad cast receiver action!";
        Toast t1 = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        t1.show();
    }
}