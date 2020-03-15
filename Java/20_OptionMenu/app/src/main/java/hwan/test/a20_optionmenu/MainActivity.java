package hwan.test.a20_optionmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.text1);
    }

    //res- menu - 에서 옵션 xml 생성
    //onCreateOptionsMenu - 액티비티 객체 만들어질떄 자동으로 호출되는 메서드, 여기서 메뉴를 생성
    // 메서드가 true를 반환하면 메뉴가 나타난다. (xml 생성과 코드 생성 방식 2가지가 있다.)


    //액티비티가 화면에 나타날 때 메뉴 구성을 위해서 호출하는 메서드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //xml 활용한 메뉴구성 방법
        /*
        // xml을 통해 메뉴를 구성할 수 있는 객체를 추출
        MenuInflater inflater = getMenuInflater();
        //xml을 이용해 메뉴를 구성한다.
        inflater.inflate(R.menu.option_menu, menu);
        */
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "codeMenu1");
        //menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "codeMenu2");
        Menu sub = menu.addSubMenu("codeMenu2");
        sub.add(Menu.NONE, Menu.FIRST + 10, Menu.NONE, "subMenu1");
        sub.add(Menu.NONE, Menu.FIRST + 20, Menu.NONE, "subMenu2");
        menu.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "codeMenu3");

        return true; // false인 경우 옵션메뉴 생성이 안됨
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //터치한 항목의 객체가 item으로 들어오게 된다.
        // 사용자가 터치한 항목 객체의 id를 추출한다.
        int id = item.getItemId();
        // xml 분기한다.
        /*
        switch (id) {
            case R.id.item1:
                text1.setText("clicked " + "item1");
                break;
            case R.id.item2:
                text1.setText("clicked " + "item2");
                break;
            case R.id.item3:
                text1.setText("clicked " + "item3");
                break;
            case R.id.item2_1:
                text1.setText("clicked " + "item2_1");
                break;
            case R.id.item2_2:
                text1.setText("clicked " + "item2_2");
                break;
        }*/

        switch (id) {
            case Menu.FIRST:
                text1.setText("clicked " + "item1");
                break;
            case Menu.FIRST + 10:
                text1.setText("clicked " + "item2");
                break;
            case Menu.FIRST + 20:
                text1.setText("clicked " + "item3");
                break;
            case Menu.FIRST + 2:
                text1.setText("clicked " + "item2_1");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}