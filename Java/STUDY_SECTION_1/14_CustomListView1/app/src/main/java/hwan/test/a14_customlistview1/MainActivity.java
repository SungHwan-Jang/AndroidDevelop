package hwan.test.a14_customlistview1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    String[] data1 = {
            "test1", "test2", "test3", "test4",
            "test5", "test6", "test7", "test8"
    };

    ListView list1;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list1 = (ListView) findViewById(R.id.list1);
        text1 = (TextView) findViewById(R.id.text1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.listText, data1);
        list1.setAdapter(adapter);
        ListCustomListner listnerList = new ListCustomListner();
        list1.setOnItemClickListener(listnerList);
    }

    class ListCustomListner implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            text1.setText("select is " + data1[position]);
        }
    }
}

//  text1.setText("select is " + data1[position]);