package hwan.test.a28_pending_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Test1Activity extends AppCompatActivity {

    // 뷰의 주소값을 담을 참조변수
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        text1 = (TextView)findViewById(R.id.test1View);
        // 액티비티를 실행할 때, 사용한 인텐트 객체를 추출한다.
        Intent intent = getIntent();
        // 데이터 추출
        String data1 = intent.getStringExtra("data1");
        int data2 = intent.getIntExtra("data2", 0);

        text1.setText("data1 : " + data1 + "\n");
        text1.append("data2 : " + data2);
    }
}