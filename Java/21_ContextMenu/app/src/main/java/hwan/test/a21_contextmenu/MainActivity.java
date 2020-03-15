package hwan.test.a21_contextmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 보통 직관적이지 않아서 리스트 뷰에 할당하여 사용하는 경우가 많다.

    // registerForContext
    // 컨텍스트 메뉴를 등록하는 메서드, 메서드의 매개 변수로 넣어준 뷰 객체에 메뉴가 설정된다.

    TextView text1;
    ListView list1;

    String[] data1 = {
            "data1", "data2", "data3", "data4", "data5"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.text1);
        // 뷰의 컨텍스트 메뉴를 설정한다.
        registerForContextMenu(text1);

        list1 = (ListView) findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        list1.setAdapter(adapter);
        ListListener listListener = new ListListener();
        list1.setOnItemClickListener(listListener);

        registerForContextMenu(list1);
    }

    // 컨텍스트 메뉴가 설정되어 있는 뷰를 길게 누르면 컨텍스트 메뉴 구성을 위해서 호출하는 메서드 (안드 os가 호출함)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // menu - 컨텍스트 메뉴를 구성하기위한 객체,
        // v의 주소값을 토대로 어떤 객체를 길게 눌렀는지 판단한다. 즉 v는 길게누른 객체
        // menuInfo 는 길게 누른 객체에 대한 정보!
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        // 사용자가 길게 누른 뷰의 주소값을 얻어온다.

        int view_id = v.getId();
        switch (view_id) {
            case R.id.text1:
                menu.setHeaderIcon(R.mipmap.ic_launcher); // android version 따라서 보이기도 안보이기도 한다.
                menu.setHeaderTitle("Context menu in TextView");
                inflater.inflate(R.menu.textview_menu, menu);
                break;
            case R.id.list1: // 리스트의 경우에는 어떤 항목을 길게 눌렀는지가 가장 중요하다. (menuInfo 이용!)
                //사용자가 길게 누른 항목의 인덱스를 가져온다.
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
                int position = info.position;

                menu.setHeaderTitle("ListView Context" + position);
                inflater.inflate(R.menu.listview_menu, menu);
                break;
        }
    }

    //컨텍스트 메뉴의 항목을 선택하면 호출되는 메서드
    // 어느 뷰에서 발생한 컨텍스트 메뉴인지 확인이 불가능하다. -> 따라서 menu xml의 id를 모두 다르게 설정해야한다!!!!
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // 사용자가 선택한 메뉴 항목의 id를 추출한다.
        int id = item.getItemId();

        // 사용자가 길게 누른 리스트뷰의 항목 인덱스를 가지고 있는 객체를 추출한다.
        ContextMenu.ContextMenuInfo info = item.getMenuInfo();
        int position = 0;

        if(info != null && info instanceof AdapterView.AdapterContextMenuInfo){
            AdapterView.AdapterContextMenuInfo info2 = (AdapterView.AdapterContextMenuInfo)info;
            position = info2.position;
        }

        switch (id) {
            case R.id.text_item1:
                text1.setText("텍스트 뷰의 메뉴 1를 선택");
                break;
            case R.id.text_item2:
                text1.setText("텍스트 뷰의 메뉴 2를 선택");
                break;
            case R.id.listview_item1:
                text1.setText("listView select 1, " + position);
                break;
            case R.id.listview_item2:
                text1.setText("listView select 2, " + position);
        }

        return super.onContextItemSelected(item);
    }

    //listView의 리스너
    class ListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            text1.setText("selected : " + data1[i]);
        }
    }

}