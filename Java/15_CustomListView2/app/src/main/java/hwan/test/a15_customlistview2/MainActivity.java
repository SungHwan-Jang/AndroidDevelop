package hwan.test.a15_customlistview2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    int[] images = {
            R.drawable.office_folder_file_10350,
            R.drawable.restricted_folder_file_10341,
            R.drawable.steamfolder_file_vapo_10558
    };

    String[] data1 = {
            "오피스", "금지", "스팀"
    };

    String[] data2 = {
            "Office", "Restrict", "Steam"
    };

    ListView listView;
    TextView resText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        resText = (TextView)findViewById(R.id.text1);

        ArrayList<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < images.length; i++) {
            HashMap<String, Object> tmp = new HashMap<String, Object>();
            tmp.put("flag", images[i]);
            tmp.put("data1", data1[i]);
            tmp.put("data2", data2[i]);

            data_list.add(tmp);
        }

        // 해시맵 객체에 데이터를 저장할 때 사용한 이름 배열
        String[] keys = {"flag", "data1", "data2"};
        // 데이터를 셋팅할 뷰의 id
        int[] ids = {R.id.imageView1, R.id.customText1, R.id.customText2};
        // 어탭터 생성
        SimpleAdapter adapter = new SimpleAdapter(this, data_list, R.layout.custom_listview, keys, ids);
        listView.setAdapter(adapter);

        ListListner listner = new ListListner();
        listView.setOnItemClickListener(listner);
    }

    class ListListner implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            resText.setText(data1[position]);
        }
    }
}