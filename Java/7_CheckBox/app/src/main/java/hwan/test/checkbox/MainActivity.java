package hwan.test.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView_result;
    CheckBox check1, check2, check3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_result = (TextView) findViewById(R.id.textView_result);
        check1 = (CheckBox) findViewById(R.id.checkbox_1);
        check2 = (CheckBox) findViewById(R.id.checkbox_2);
        check3 = (CheckBox) findViewById(R.id.checkbox_3);

        //checkBox listener 설정
        CheckBoxListener listener = new CheckBoxListener();
        check1.setOnCheckedChangeListener(listener);
        check2.setOnCheckedChangeListener(listener);
        check3.setOnCheckedChangeListener(listener);
    }

    //check 확인 - 첫번째 버튼과 연결된 메서드
    public void btn1Method(View v) {
        textView_result.setText("");

        boolean b1 = check1.isChecked();
        boolean b2 = check2.isChecked();
        boolean b3 = check3.isChecked();

        if (b1 == true) {
            textView_result.append("checked1 ");
        }

        if (b2 == true) {
            textView_result.append("checked2 ");
        }

        if (b3 == true) {
            textView_result.append("checked3 ");
        }
    }

    //check init - 두번째 버튼과 연결된 메서드
    public void btnMethod2(View v) {
        check1.setChecked(true);
        check2.setChecked(true);
        check3.setChecked(true);
    }

    //check init - 두번째 버튼과 연결된 메서드
    public void btnMethod3(View v) {
        check1.setChecked(false);
        check2.setChecked(false);
        check3.setChecked(false);
    }

    public void btnMethod4(View v) {
        check1.toggle();
        check2.toggle();
        check3.toggle();
    }

    // check 박스의 체크 상태가 변경되면 반응하는 리스너
    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // check상태가 변경된 체크박스의 id를 가져옴
            int id = buttonView.getId();

            switch (id) {
                case R.id.checkbox_1:
                    if (check1.isChecked() == true) {
                        textView_result.setText("checked 1!");
                    } else {
                        textView_result.setText("unchecked 1!");
                    }
                    break;
                case R.id.checkbox_2:
                    if (isChecked == true) {
                        textView_result.setText("checked 2!");
                    } else {
                        textView_result.setText("unchecked 2!");
                    }
                    break;
                case R.id.checkbox_3:
                    if (isChecked == true) {
                        textView_result.setText("checked 3!");
                    } else {
                        textView_result.setText("unchecked 3!");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}