package hwan.test.button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //view의 주소값을 담을 참조변수
    TextView textView_result;
    Button button_first;
    Button button_second;
    Button button_third;
    Button button_forth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View의 주소값을 얻어온다.
        textView_result = (TextView) findViewById(R.id.textView_result);
        button_first = (Button) findViewById(R.id.button_first);
        button_second = (Button) findViewById(R.id.button_second);
        //리스너 객체를 생성
        BtnListnerFirst btnListner1 = new BtnListnerFirst();
        BtnListnerSecond btnListner2 = new BtnListnerSecond();
        //리스너를 버튼 객체에 설정한다.
        button_first.setOnClickListener(btnListner1);
        button_second.setOnClickListener(btnListner2);

        button_third = (Button) findViewById(R.id.button_third);
        button_forth = (Button) findViewById(R.id.button_forth);

        BtnListnerThF btnListner34 = new BtnListnerThF();
        button_third.setOnClickListener(btnListner34);
        button_forth.setOnClickListener(btnListner34);
    }

    // 첫 번째 버튼과 연결된 리스너
    class BtnListnerFirst implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            textView_result.setText("button1 clicked!");
        }
    }

    class BtnListnerSecond implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            textView_result.setText("button2 clicked!");
        }
    }

    class BtnListnerThF implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 이벤트가 발생된 뷰의 id값을 추출한다.
            int id = v.getId();
            // id로 분기한다.

            switch (id) {
                case R.id.button_third:
                    textView_result.setText("clicked3 button!");
                    break;
                case R.id.button_forth:
                    textView_result.setText("clicked4 button!");
                    break;
                default:
                    break;
            }
        }
    }

    //아래의 기법은 클릭 이벤트만 동작한다.
    // 위와 같이 복잡하게 안해도 되는 장점이 있다.
    // 5 번째 버튼을 누르면 호출되는 메서드
    // xml onClick 속성에 해당 메서드를 등록하는 방식이다.
    public void btn5Method(View v) {
        textView_result.setText("click 5");
    }

    // 6 번째 버튼을 누르면 호출되는 메서드
    public void btn6Method(View v) {
        textView_result.setText("click 6");
    }

    // 7 ,8 버튼 호출되는 메서드
    public void btn78Method(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button7:
                textView_result.setText("click 7");
                break;
            case R.id.button8:
                textView_result.setText("click 8");
                break;
            default:
                break;
        }
    }
}