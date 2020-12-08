package hwan.test.a13_adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // view address
    ListView list1;
    TextView text1;
    // for listView, make string array
    String[] data1 ={
            "list1", "list2", "list3","list4", "list5", "list6",
            "list7", "list8", "list9","list10", "list11", "list12",
            "list13", "list14", "list15","list16", "list17", "list18",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView)findViewById(R.id.textView2);

        list1 = (ListView)findViewById(R.id.list1);

        // 몇개의 항목을 보여줄 것인가. => 배열이나 리스트의 항목 갯수로 결정
        // 각 항목에 어떤 데이터를 보여줄 것인가. => 배열이나 리스트에 들어있는 값
        // 각 항목은 어떤 모양으로 보여줄 것인가? => 레이아웃을 지정
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        // 프로젝트 리소스는 R로 접근, Android 제공 리소스는 android.R로 접근한다. 해당 simple_list ctrl+B 접근시 xml 확인 가능
        list1.setAdapter(adapter);
        //리스트뷰에 어뎁터를 설정한다.
        ListListener listener = new ListListener();
        list1.setOnItemClickListener(listener);
    }

    class ListListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //position - 사용자가 터치한 인덱스 번호
            text1.setText(data1[position]);
        }
    }
}