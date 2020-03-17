package hwan.test.a28_pending_intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Notification 메시지를 통해 애플리케이션의 Activity 를 실행할 수 있는데 이 때 사용하는 것이 Pending Intent 이다.
    // Pending Intent 를 통해 실행되는 Activity 로 데이터를 전달 할 수도 있다.
    // Action 은 알림 메시지에 버튼과 같은것을 배치하여 클릭시, 지정된 Activity를 실행할 수 있게 된다.
    // 안드4 까지는 메시지당 하나의 Activity 실행이 가능했지만 4.1 이후부터는 하나의 메시지를 통해 여러 Activity를 실행할 수 있도록 제공된다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1Method(View v) {
        NotificationCompat.Builder builder = getNotificationBuilder("pending", "pending intent");
        builder.setContentTitle("notification1");
        builder.setContentText("this is alarm1");
        builder.setSmallIcon(android.R.drawable.ic_menu_camera);

        // 메시지를 터치하면 실행될 Activity 정보를 관리할 객체를 생성한다.
        Intent intent1 = new Intent(this, Test1Activity.class);

        // 실행될 Activity에 전달할 데이터를 세팅한다.
        intent1.putExtra("data1", "String data1");
        intent1.putExtra("data2", 100);

        // 추가 액션을 설정한다. (별로 쓸일 없을것 같음 -0-)
        Intent intent2 = new Intent(this, Test2Activity.class);
        PendingIntent pending2 = PendingIntent.getActivity(this, 100, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        // 액션 생성을 위한 빌더 객체 생성
        NotificationCompat.Action.Builder builder2 = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_compass, "Action1", pending2);
        NotificationCompat.Action action2 = builder2.build();
        builder.addAction(action2);

        // Intent 객체를 관리할 PendingIntent 객체를 생성하여 세팅한다.
        PendingIntent pending1 = PendingIntent.getActivity(this, 10, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent.FLAG ... 중요도를 나타냄
        builder.setContentIntent(pending1);
        // Notification 클릭시 자동으로 없어지게 해야 한다.
        builder.setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(10, notification);
    }

    public void btn2Method(View v) {
        NotificationCompat.Builder builder = getNotificationBuilder("pending", "pending intent");
        builder.setContentTitle("notification2");
        builder.setContentText("this is alarm2");
        builder.setSmallIcon(android.R.drawable.ic_menu_camera);

        // 메시지를 터치하면 실행될 Activity 정보를 관리할 객체를 생성한다.
        Intent intent1 = new Intent(this, Test2Activity.class);
        // Intent 객체를 관리할 PendingIntent 객체를 생성하여 세팅한다.
        PendingIntent pending1 = PendingIntent.getActivity(this, 10, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent.FLAG ... 중요도를 나타냄
        builder.setContentIntent(pending1);
        // Notification 클릭시 자동으로 없어지게 해야 한다.
        builder.setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(20, notification);
    }

    public NotificationCompat.Builder getNotificationBuilder(String id, String name) {
        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(this, id);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        return builder;
    }
}