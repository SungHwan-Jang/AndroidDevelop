package com.example.a14_systemmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        // 안드로이드 os가 리시버를 찾기 위해 사용했던 이름을 추출한다.
        String action = intent.getAction();
        // 리시버의 이름으로 분기한다.
        switch (action){
            case "android.intent.action.BOOT_COMPLETED": // manifest receiver intent filter 이름을 넣는다.
                Toast t1 = Toast.makeText(context, "boot complete", Toast.LENGTH_SHORT);
                t1.show();
                break;
            case "android.provider.Telephony.SMS_RECEIVED":
                String str = "";
                // 문자 메시지 정보를 가지고 있는 객체를 추출한다.
                // Bundle 이라는 객체는 intent 안에 세팅되어 있는 객체를 한꺼번에 전부 다 꺼내온다.
                Bundle bundle = intent.getExtras();
                if(bundle != null){
                    // 번들에서 문자 메시지 객체를 추출한다.
                    Object[] obj = (Object [])bundle.get("pdus");
                    // 문자 메시지 정보 객체를 이용해 SMS message 객체를 생성한다.
                    SmsMessage [] msg = new SmsMessage[obj.length];

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = bundle.getString("format");

                        for(int i=0; i<obj.length; i++){
                            msg[i] = SmsMessage.createFromPdu((byte[])obj[i], format);
                        }
                    }
                    else{
                        for(int i=0; i<obj.length; i++){
                            msg[i] = SmsMessage.createFromPdu((byte[])obj[i]);
                        }
                    }

                    for(SmsMessage a1 : msg){
                        String phoneNumber = a1.getOriginatingAddress();
                        String msgBody = a1.getMessageBody();

                        String totalMsg = phoneNumber + " : " + msgBody;

                        Toast t2 = Toast.makeText(context, totalMsg, Toast.LENGTH_LONG);
                        t2.show();
                    }
                }
                break;
            default:
                break;
        }
    }
}