package hwan.test.a8_radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    RadioButton radio1_3, radio2_3;
    RadioGroup group1, group2;
    TextView text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radio1_3 = (RadioButton) findViewById(R.id.radio1_3);
        radio2_3 = (RadioButton) findViewById(R.id.radio2_3);
        group1 = (RadioGroup) findViewById(R.id.group1);
        group2 = (RadioGroup) findViewById(R.id.group2);

        text1 = (TextView) findViewById(R.id.textView1);
        text2 = (TextView) findViewById(R.id.textView2);

        //라디오 그룹에 리스너 설정.
        RadioListner listner = new RadioListner();
        group1.setOnCheckedChangeListener(listner);
        group2.setOnCheckedChangeListener(listner);
    }

    public void btn1Method(View v) {
        radio1_3.setChecked(true);
        radio2_3.setChecked(true);
    }

    public void btn2Method(View v) {
        int id1 = group1.getCheckedRadioButtonId();
        int id2 = group2.getCheckedRadioButtonId();

        switch (id1) {
            case R.id.radio1_1:
                text1.setText("select radio 1-1");
                break;
            case R.id.radio1_2:
                text1.setText("select radio 1-2");
                break;
            case R.id.radio1_3:
                text1.setText("select radio 1-3");
                break;
            default:
                break;
        }

        switch (id2) {
            case R.id.radio2_1:
                text2.setText("select radio 2-1");
                break;
            case R.id.radio2_2:
                text2.setText("select radio 2-2");
                break;
            case R.id.radio2_3:
                text2.setText("select radio 2-3");
                break;
            default:
                break;
        }
    }

    class RadioListner implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // check 상태가 변경된 라디오 그룹의 아이디를 추출한다.
            int id = group.getId();

            switch (id) {
                case R.id.group1:
                    switch (checkedId) {
                        case R.id.radio1_1:
                            text1.setText("radio event1");
                            break;
                        case R.id.radio1_2:
                            text1.setText("radio event2");
                            break;
                        case R.id.radio1_3:
                            text1.setText("radio event3");
                            break;
                        default:
                            break;
                    }
                    break;
                case R.id.group2:
                    switch (checkedId) {
                        case R.id.radio2_1:
                            text2.setText("radio event1");
                            break;
                        case R.id.radio2_2:
                            text2.setText("radio event2");
                            break;
                        case R.id.radio2_3:
                            text2.setText("radio event3");
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}