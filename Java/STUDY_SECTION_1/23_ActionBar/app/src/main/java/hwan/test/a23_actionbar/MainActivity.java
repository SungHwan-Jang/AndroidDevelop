package hwan.test.a23_actionbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text1, text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView)findViewById(R.id.mainText1);
        text2 = (TextView)findViewById(R.id.mainText2);
    }

    //옵션 메뉴 구성을 위해 호출하는 메서드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        //검색 뷰가 설정되어 있는 메뉴 항목 객체를 추출한다.
        MenuItem search_item = menu.findItem(R.id.item5);
        // 액션뷰로 설정된 뷰를 추출한다.
        SearchView searchView = (SearchView)search_item.getActionView();
        //안내문구 설정
        searchView.setQueryHint("input search command.");

        // 서치뷰에 리스너를 설정한다.
        SearchViewListener searchViewListener = new SearchViewListener();
        searchView.setOnQueryTextListener(searchViewListener);

        return true;
    }
    // 서치뷰의 리스너를 설정한다.
    class SearchViewListener implements SearchView.OnQueryTextListener{

        // 확인 키를 누르면 호출되는 메서드
        @Override
        public boolean onQueryTextSubmit(String s) {
            text2.setText(s);
            return false;
        }

        // 문자열이 변경될 때 마다 호출되는 메서드
        @Override
        public boolean onQueryTextChange(String s) {
            text1.setText(s);
            return false;
        }
    }

    // 옵션 메뉴의 항목을 터치라면 호출되는 메서드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //사용자가 선택한 메뉴 항목의 아이디를 추출한다.
        int id = item.getItemId();
        //분기한다
        switch (id){
            case R.id.item1:
                text1.setText("selected 1");
                break;
            case R.id.item2:
                text1.setText("selected 2");
                break;
            case R.id.item3:
                text1.setText("selected 3");
                break;
            case R.id.item4:
                text1.setText("selected 4");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}