package hwan.test.textview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView_header;
    TextView textView_middle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // setContentView 메소드 호출 이후에 xml 객체가 생성완료된다. 따라서 이후에 컨트롤한다.

        // id 속성이 textView_header를 가져온다.
        textView_header = (TextView) findViewById(R.id.textView_header);
        // text의 메시지 내용을 바꾼다.
        textView_header.setText("new string message");

        textView_middle = (TextView) findViewById(R.id.textView_middle);
        textView_middle.setText("second modified message");
    }
}