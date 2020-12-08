package hwan.test.a12_imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //뷰의 주소값을 담을 참조변수
    ImageView img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ImageView 설정
        img3 = (ImageView) findViewById(R.id.imageView3);
        //해당 ImageView에 리소스 설정
        img3.setImageResource(R.drawable.antopen);
    }
}