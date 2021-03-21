package com.example.a8_onresultactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

// startActivityForResult()
// 액티비티에서 다른 액티비티를 실행하고 다시 돌아왔을 때, 어떤 처리가 필요하다면 액티비티 실행시 startActivity가 아닌 startActivityForResult 메서드를 사용한다.
// 즉, startActivityForResult() 로 액티비티 호출 뒤에 돌아오면 자동으로 onActivityResult 메서드가 호출된다. 여기에서 필요한 작업을 처리하면 된다.

public class MainActivity extends AppCompatActivity {

    TextView textView = null;

    final int SECOND_ACTIVITY = 2;
    final int THIRD_ACTIVITY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view1);
    }

    public void btn_method(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, SECOND_ACTIVITY);
    }

    public void btn_method_2(View view){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivityForResult(intent, THIRD_ACTIVITY);
    }

    // requestCode : activity 구분
    // resultCode : activity 의 반환값 ( ex: 사용 설정 :1, 미사용 :0 등으로 _)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 다른 액티비티 실행 후 돌아오는 경우 자동 실행된다.
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SECOND_ACTIVITY:
                textView.setText("return act 2");
                switch (resultCode){
                    // RESULT 값 설정을 하지 않으면 기본적으로 CANCELED가 반환된다.
                    case RESULT_OK:
                        textView.append(" - OK");
                        break;
                    case RESULT_CANCELED:
                        textView.append(" - CANCEL");
                        break;
                    case RESULT_FIRST_USER:
                        textView.append(" - FIRST_USER");
                        break;
                    default:
                        break;
                }
                break;
            case THIRD_ACTIVITY:
                textView.setText("return act 3");
                break;
            default:
                break;

        }
    }
}