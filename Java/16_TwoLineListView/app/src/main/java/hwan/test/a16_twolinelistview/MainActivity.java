package hwan.test.a16_twolinelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //기존 안드로이드에서 제공하는 2라인 리스트뷰를 활용해 본다.

    String[] data1 = {
            "문자열1", "문자열2", "문자열3"
    };

    String[] data2 = {
            "stirng1", "string2", "string3"
    };

    ListView list1;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView)findViewById(R.id.result);

        list1 = (ListView)findViewById(R.id.list1);
        ArrayList<HashMap<String, String>> data_list = new ArrayList<HashMap<String, String>>();


        for(int i =0; i< data1.length; i++){
            HashMap<String, String> tmp = new HashMap<String, String>();
            tmp.put("str1", data1[i]);
            tmp.put("str2", data2[i]);

            data_list.add(tmp);
        }

        String[] keys = { "str1", "str2"};
        int[] ids = {android.R.id.text1, android.R.id.text2}; //사용할 제공 레이아웃 android.R.layout.simple_list_item_2에서 아이디 확인.
        // 어뎁터를 리스트뷰에 셋팅한다.

        SimpleAdapter adapter = new SimpleAdapter(this, data_list, android.R.layout.simple_list_item_2, keys, ids);
        list1.setAdapter(adapter);
        ListViewListner listner = new ListViewListner();
        list1.setOnItemClickListener(listner);
    }

    class ListViewListner implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            result.setText(data1[position]);
        }
    }
}