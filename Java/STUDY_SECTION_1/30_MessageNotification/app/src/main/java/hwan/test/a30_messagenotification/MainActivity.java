package hwan.test.a30_messagenotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Person;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;

// 안드로이드 9.0 이상부터 사용 가능하다. min sdk 버전 28 으로 맞추어 Notification 분기도 생략함.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1Method(View v) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("message", "Message style", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.GREEN);

        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(this, "message");
        builder.setContentTitle("Message Style");
        builder.setContentText("Message Style Notification");
        builder.setSmallIcon(android.R.drawable.ic_menu_agenda);

        // 다자간의 대화 내용을 노티로 나타내는것을 메시지 스타일이다.
        // 사람 한명 (대화 사람 정보)를 객체로 만든다.
        Person.Builder tmp1 = new Person.Builder();
        Icon icon1 = Icon.createWithResource(this, android.R.drawable.ic_menu_call);
        tmp1.setIcon(icon1);
        tmp1.setName("sung-hwan");
        Person user1 = tmp1.build();

        Person.Builder tmp2 = new Person.Builder();
        Icon icon2 = Icon.createWithResource(this, android.R.drawable.ic_menu_camera);
        tmp2.setIcon(icon2);
        tmp2.setName("ji-hwan");
        Person user2 = tmp2.build();

        // 메시지 스타일 객체 만들기
        Notification.MessagingStyle style = new Notification.MessagingStyle(user1);

        style.addMessage("1 Message", System.currentTimeMillis(), user1);
        style.addMessage("2 Message", System.currentTimeMillis(), user2);
        style.addMessage("3 Message", System.currentTimeMillis(), user1);
        style.addMessage("4 Message", System.currentTimeMillis(), user2);
        style.addMessage("5 Message", System.currentTimeMillis(), user1);
        style.addMessage("6 Message", System.currentTimeMillis(), user2);

        builder.setStyle(style);

        Notification notification = builder.build();
        manager.notify(10, notification);
    }
}