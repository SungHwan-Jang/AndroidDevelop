package com.example.a25_resource_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// 안드로이드는 app에서 사용하는 다양한 값을 코드에 직접 작성하지 않고 xml을 통해 관리할 수 있도록 제공된다.
// xml 통해 사용할 수 있기 때문에 단말기의 상태(크기, 해상도, 언어 등)에 따라 다양하게 대응할 수 있고
// 유지 보수를 용이하게 할 수 있다.

// res/values/*.xml 파일을 string xml로 사용 가능하다. ( 새로 만들어도 됨)
public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view1);
    }

    public void btn_method_1(View view){
        Resources res = getResources();
        String str1 = res.getString(R.string.text1_str2);
        textView.setText(str1);

        // textView는 자주 사용하기 때문에 아래와 같이 단순히 사용도 가능하다.
        textView.setText(R.string.button1_str);
    }

    public void btn_method_2(View view){
        Resources res = getResources();
        String [] stringArray = res.getStringArray(R.array.string_array_1);

        textView.setText("");
        for(String str1 : stringArray){
            textView.append(str1);
        }
    }

    public void btn_method_3(View view){
        // 직접 정의된 컬러 값을 가져오거나,
        //textView.setTextColor(Color.YELLOW);

        // rbg로 컬러를 가져온다.
//        int color = Color.rgb(26,106,129);
//        textView.setTextColor(color);
        // argb로 투명도 까지 추가 가능하다.
//        int color = Color.argb(50, 26,106,129);
//        textView.setTextColor(color);

        // resource xml로 설정
        Resources res = getResources();
        // 이전 버전에는 이런식으로 동작했으나, 버전업에 따라 모든 단말기에서 같은 팔렛트 색상이
        // 보이도록 내부 수정
        // int color = res.getColor(this, R.color.custom_color);
        // 따라서 다음과 같이 처리한다.
        int color = ContextCompat.getColor(this, R.color.custom_color);
        textView.setTextColor(color);
    }

    // 크기관리
    // 크기를 xml에 등록해서 사용할 수 있다.
    // 모든 디스플레이 장비는 px라는 단위로 크기등을 결정하게 된다.
    // 안드로이드는 다양한 단말기 때문에 px을 사용하면 크기가 다르게 나타날 수 있다.
    // 안드로이드는 가변형 단위들을 제공한다.

    // px : 실제 사용할 픽셀의 개수
    // dp: 160ppi 액정에서 1dp = 1px ( 10dp 는 160ppi 에서 10px 그림 / 320ppi 에서는 20px 그림 , 따라서 모든 액정에 같은 길이 보장)
    // sp : 단말기에 설정되어 있는 글자 크기에 따라 가변  **** 글자 이북 관련!!
    //      기본 크기에서 160ppi 액정에서 1sp = 1px
    // pt : 1pt = 1/72인치

    public void btn_method_4(View view){
        Resources res = getResources();

        float px = res.getDimension(R.dimen.px);
        float dp = res.getDimension(R.dimen.dp);
        float sp= res.getDimension(R.dimen.sp);
        float inch = res.getDimension(R.dimen.inch);
        float mm = res.getDimension(R.dimen.mm);
        float pt = res.getDimension(R.dimen.pt);

        //;
        //text view에 각각 출력해 봅시다.
    }

}