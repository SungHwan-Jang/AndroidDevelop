package hwan.test.a26_listdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // 기본적인 리스트 다이얼로그 구성 위한 문자열 배열
    String[] data1 = {
            "option1", "option2", "option3", "option4", "option5", "option6",
    };
    TextView text1;

    // 커스텀 리스트 다이얼로그 구성을 위한 문자열 배열
    String[] data2 = {
            "Office", "Restricted", "Steam"
    };

    int[] imgData = {
            R.drawable.office_folder_file_10350, R.drawable.restricted_folder_file_10341, R.drawable.steamfolder_file_vapo_10558
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.textView);
    }

    public void btn1Method(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("list dialog");
        builder.setNegativeButton("Cancel", null);

        // 리스트 다이얼로그 구성을 위한 문자열 배열을 설정한다. (setItems 메소드를 사용한다는 것 빼곤 똑같다).
        ListDialogListener listDialogListener = new ListDialogListener();

        builder.setItems(data1, listDialogListener);
        builder.show();
    }

    class ListDialogListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //기본 다이얼로그는 which 가 어떤버튼인지 구분을 했는데, which를 리스트 다이어로그에 세팅하면 선택한 요소가 들어오게 된다.
            text1.setText("Basic List Dialog : " + data1[which]);
        }
    }

    public void btn2Method(View v){
        // 리스트 뷰 구성을 위해 필요한 데이터를 ArrayList에 담는다.
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        for(int i =0; i< data2.length; i++){
            HashMap<String, Object> tmp = new HashMap<String, Object>();
            tmp.put("data2", data2[i]);
            tmp.put("imgData", imgData[i]);

            list.add(tmp);
        }

        String[] keys = {"data2", "imgData"};
        int[] ids = {R.id.rowTextView, R.id.row_imageView};

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.row, keys, ids);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("custom list dialog");

        CustomListDialogListener customListDialogListener = new CustomListDialogListener();
        builder.setAdapter(adapter, customListDialogListener);
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    class CustomListDialogListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            text1.setText("custom list :" + data2[which]);
        }
    }
}