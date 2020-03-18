package hwan.test.a29_stylenotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1Method(View v) {
        NotificationCompat.Builder builder = getNotificationBuilder("style", "style Notification");
        builder.setContentTitle("Big Picture");
        builder.setContentText("Big Picture Notification");
        builder.setSmallIcon(android.R.drawable.ic_menu_agenda);

        //Big picture Notification 객체를 생성한다.
        NotificationCompat.BigPictureStyle big = new NotificationCompat.BigPictureStyle(builder);
        //보여줄 이미지를 설정한다.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        big.bigPicture(bitmap);
        big.setBigContentTitle("Big Content Title");
        big.setSummaryText("Summary Test");

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(10, notification);
    }

    public void btn2Method(View v) {
        NotificationCompat.Builder builder = getNotificationBuilder("style", "style Notification");
        builder.setContentTitle("Big Picture");
        builder.setContentText("Big Picture Notification");
        builder.setSmallIcon(android.R.drawable.ic_menu_call);

        //Big text Style Notification 객체를 생성한다.
        NotificationCompat.BigTextStyle big = new NotificationCompat.BigTextStyle(builder);
        big.setBigContentTitle("Big Content Title");
        big.setSummaryText("Summary Test");
        big.bigText("This is Big Text Style Notification.\n it usefully assign long type message.");

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(20, notification);
    }

    public void btn3Method(View v) {
        NotificationCompat.Builder builder = getNotificationBuilder("style", "style Notification");
        builder.setContentTitle("Title");
        builder.setContentText("Content");
        builder.setSmallIcon(android.R.drawable.ic_menu_day);

        //Big text Style Notification 객체를 생성한다.
        NotificationCompat.InboxStyle inbox = new NotificationCompat.InboxStyle(builder);
        inbox.setSummaryText("Summary Test");

        inbox.addLine("aaaaaa");
        inbox.addLine("bbbbb");
        inbox.addLine("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(30, notification);
    }


    public NotificationCompat.Builder getNotificationBuilder(String id, String name) {
        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(this, id);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        return builder;
    }
}