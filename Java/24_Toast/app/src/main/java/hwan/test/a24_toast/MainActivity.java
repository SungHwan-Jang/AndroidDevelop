package hwan.test.a24_toast;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1Method(View v){
        // 토스트 객체를 생성한다.
        Toast t1 = Toast.makeText(this, "this is toast message", Toast.LENGTH_SHORT);
        t1.show();
    }

    public void btn2Method(View v){
        //토스트를 통해 보여줄 뷰 객체를 생성한다.
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.custom_toast, null);

        //뷰가 가지고 있는 뷰를 추출한다.
        ImageView img1 = (ImageView)v1.findViewById(R.id.imageView2);
        TextView text = (TextView)v1.findViewById(R.id.toastMsgText);

        // 뷰 커스텀 세팅
        img1.setImageResource(R.drawable.ic_launcher_foreground);
        text.setText("Customed Toast");
        text.setTextColor(Color.BLUE);

        // 뷰의 배경을 토스트 전용 배경 이미지로 설정한다.
        v1.setBackgroundResource(android.R.drawable.toast_frame);




        // 생성한 뷰를 토스트 객체에 설정한다.
        Toast t2 = new Toast(this);
        // 토스트 객체에 보여줄 뷰를 설정한다.
        t2.setView(v1);

        // 토스트 메시지의 위치를 설정한다.
        t2.setGravity(Gravity.CENTER, 0 ,0);
        //토스트가 표시될 시간
        t2.setDuration(Toast.LENGTH_SHORT);

        t2.show();
    }
}