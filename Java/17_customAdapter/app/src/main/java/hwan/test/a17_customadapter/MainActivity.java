package hwan.test.a17_customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // list array,
    String[] data = {
            "data1", "data2", "data3", "data4", "data5", "data6"
    };

    TextView text1;
    ListView list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.text1);
        list1 = (ListView) findViewById(R.id.list1);

        ListAdapter adapter = new ListAdapter();
        list1.setAdapter(adapter);


    }

    class ListAdapter extends BaseAdapter {

        BtnListner listner = new BtnListner();

        //안드로이드는 getCount() 호출하여 전체 항목 수를 얻고 getView를 항목 수 만큼(보이는 공간만큼) 호출하여 보여준다.
        @Override
        public int getCount() { // 리스트뷰 내의 전체 항목의 개수를 반환한다.
            //return 0;
            return data.length;
        }

        @Override
        public Object getItem(int position) { // 항목의 index가 position으로 들어오니 반환하고 싶은값 반환 (자유롭게)
            return null;
        }

        @Override
        public long getItemId(int position) { // index 10번에 해당하는 뷰의 id를 넘겨준다던가 하면 된다. (자유롭게)
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // 리스트 뷰 항목 하나를 구성하여 반환한다.
            // 안보이던 항목을 getView를 (보여야 하는만큼) 호출하여 보여준다.
            // 스크롤시 움직여서 원래 보이던 항목이 안보이게 되면, 필요없는 그 뷰는 버리지 않고 convertView로 들어온다. (즉 돌려막기)

            // 재사용 가능한 뷰가 없다면 뷰를 만들고
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.custom_adapter, null);
            }

            // 뷰를 구성
            TextView sub_text = (TextView) convertView.findViewById(R.id.custom_text);
            sub_text.setText(data[position]);

            Button sub_btn1 = (Button) convertView.findViewById(R.id.button1);
            Button sub_btn2 = (Button) convertView.findViewById(R.id.button2);

            //버튼에 인덱스 값을 저장하여 몇번째 리스트인지 알수 있게한다.
            sub_btn1.setTag(position);
            sub_btn2.setTag(position);

            sub_btn1.setOnClickListener(listner);
            sub_btn2.setOnClickListener(listner);

            // 뷰를 반환
            return convertView;
        }
    }

    class BtnListner implements View.OnClickListener {

        @Override
        public void onClick(View v) { // 버튼객체 v가 넘어온다.
            int id = v.getId();

            //list의 인덱스 값을 추출한다.
            int position = (Integer) v.getTag();
            // 리스너에서 몇번째 리스트에서 버튼이 클릭 되었는지 알 수 없다.
            // 따라서 어댑터에서 미리 Tag를 설정하여 그 값을 불러와 몇 번째 리스트에서 불러왔는지 확인하는 작업이다.

            switch (id) {
                case R.id.button1:
                    text1.setText("push 1 button " + (position+1) +" list");
                    break;
                case R.id.button2:
                    text1.setText("push 2 button " + (position+1) +" list");
                    break;
            }
        }
    }
}