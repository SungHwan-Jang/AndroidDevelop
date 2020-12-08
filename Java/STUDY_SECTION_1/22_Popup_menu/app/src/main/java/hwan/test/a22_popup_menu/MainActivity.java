package hwan.test.a22_popup_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.PointerIcon;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.text1);
    }

    public void btnMethod(View v){
        // 팝업메뉴 객체를 생성한다
        PopupMenu pop = new PopupMenu(this, text1); // 2번쨰 파라미터 뷰에 팝업이 생성된다.
        // 팝업 메뉴의 메뉴를 관리하는 객체를 추출한다.
        Menu menu = pop.getMenu();
        // 메뉴를 구성한다.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);
        // 팝업 메뉴의 리스너를 설정한다.
        PopupListener popupListener = new PopupListener();
        pop.setOnMenuItemClickListener(popupListener);
        // 메뉴를 나타나게 한다.
        pop.show();
    }

    class PopupListener implements PopupMenu.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            //사용자가 선택한 메뉴의 id값을 추출
            int id = item.getItemId();
            switch (id){
                case R.id.item1:
                    text1.setText("selected menu 1");
                    break;
                case R.id.item2:
                    text1.setText("selected menu 2");
                    break;
                case R.id.item3:
                    text1.setText("selected menu 3");
                    break;
            }
            return false;
        }
    }
}