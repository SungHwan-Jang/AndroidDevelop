package hwan.test.a11_edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText edit1;
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = (EditText)findViewById(R.id.editTextTextPersonName);
        text1 = (TextView)findViewById(R.id.textView);

        EnterListner listner = new EnterListner();
        edit1.setOnEditorActionListener(listner);

        WatcherClass watcher = new WatcherClass();
        edit1.addTextChangedListener(watcher);
    }

    public void btn1Method(View v){
        edit1.setText("new string");
    }

    public void btn2Method(View v){
        String str = edit1.getText().toString(); // getText()는 editable 객체반환 -> string 으로 변환한다
        text1.setText("Input :" + str);
    }

    class EnterListner implements TextView.OnEditorActionListener{

        // 키보드 활성화 후 엔터키 누르면 발생되는 이벤트를 나타낸다.
        @Override// v - enter 누를때 edit 객체, id - edit id, event - 사건 객체
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String str = v.getText().toString();
            text1.setText("input Str :" + str);
            return false; //true일 경우 엔터키 누르면 키보드가 안 사라진다.
        }
    }

    class WatcherClass implements TextWatcher{
        // 문자열 값이 변경 되었을때
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            text1.setText("setting str..:" + s);

        }

        //문자열 값이 변경 되기 전
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        //문자열 값이 변경된 이후
        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}