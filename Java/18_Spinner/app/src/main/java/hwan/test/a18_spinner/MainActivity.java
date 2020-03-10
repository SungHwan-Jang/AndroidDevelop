package hwan.test.a18_spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 콤보박스 같은 느낌이다. 리스트뷰는 화면을 많이 차지해서 화면을 절약? 하는 방법이다.

    String[] data1 = {
            "spinner1", "spinner2", "spinner3"
    };

    Spinner spinner1;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner)findViewById(R.id.spinner);
        text1 = (TextView)findViewById(R.id.textView);

        // 기본 스피너
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data1);
        // 드롭다운으로 나타나는 항목 리스트의 뷰
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 어댑터에 스피너를 적용한다.
        spinner1.setAdapter(adapter1);

        SpinnerListner listner = new SpinnerListner();
        spinner1.setOnItemSelectedListener(listner);
    }

    public void btnMethod(View v){
        // 현재 스피너에 선택되어있는 항목의 인덱스 값을 추출한다.
        int index = spinner1.getSelectedItemPosition();
        text1.setText("now, currently selected item is <" + data1[index]+">");
    }

    class SpinnerListner implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // 사용자가 항목을 선택했을 때 호출되는 메서드
            text1.setText(data1[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // 아무것도 선택되지 않았을 때, 선택되는 메서드. 거의 사용되지 않는다.
        }
    }
}