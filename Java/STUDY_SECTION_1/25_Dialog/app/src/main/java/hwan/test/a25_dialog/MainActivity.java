package hwan.test.a25_dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView text1;
    ProgressDialog pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.text1);
    }

    public void btn1Method(View v) {
        //다이얼 로그에 관한 정보를 관리하는 개체를 생성한다
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 제목
        builder.setTitle("Basic dialog");
        builder.setMessage("Basic dialog Main");
        builder.setIcon(R.mipmap.ic_launcher);

        Dialog1Listener listener = new Dialog1Listener();

        //다이얼로그의 버튼을 셋팅한다.
        builder.setPositiveButton("Positive", listener);
        builder.setNeutralButton("Neutral", listener);
        builder.setNegativeButton("Negative", listener);

        //다이얼로그를 띄운다.
        builder.show();
    }

    //다이얼로그의 리스너
    class Dialog1Listener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // 버튼 종류 값으로 분기한다. i 는 버튼종류가 들어오게 된다.
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE:
                    text1.setText("positive");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    text1.setText("negative");
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    text1.setText("neutral");
                    break;
            }
        }
    }

    public void btn2Method(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("custom dialog");
        builder.setIcon(R.mipmap.ic_launcher);

        //다이얼로그를 통해 보여줄 뷰를 생성한다.
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dialog, null);
        // 생성한 뷰를 다이어로그에 세팅한다.
        builder.setView(v1);

        Dialog2Listener listener = new Dialog2Listener();

        //다이얼로그의 버튼을 셋팅한다.
        builder.setPositiveButton("Confirm", listener);
        builder.setNegativeButton("Cancel", null);

        //다이얼로그를 띄운다.
        builder.show();
    }

    class Dialog2Listener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:

                    //AleartDialog가 가지고 있는 뷰를 가져온다.
                    AlertDialog alert = (AlertDialog) dialog;
                    //AlertDialog가 가지고 있는 뷰를 추출한다.
                    EditText edit1 = (EditText) alert.findViewById(R.id.editTextTextPersonName);
                    EditText edit2 = (EditText) alert.findViewById(R.id.editTextTextPersonName2);

                    //사용자가 입력한 문자열을 가져온다.
                    String str1 = edit1.getText().toString();
                    String str2 = edit2.getText().toString();

                    text1.setText("edit1 : " + str1 + "\n");
                    text1.append("edit2 : " + str2);
                    break;
            }
        }
    }

    //DatePicker 다이얼로그 사용
    public void btn3Method(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Dialog3Listener listener = new Dialog3Listener();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, listener, year, month, day);
        pickerDialog.show();
    }

    class Dialog3Listener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            text1.setText(year + " Y" + (month + 1) + " M" + dayOfMonth + " D");
        }
    }

    // TimePicker 다이얼로그 사용

    public void btn4Method(View v) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        Dialog4Listener listener = new Dialog4Listener();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listener, hour, minute, false);
        timePickerDialog.show();
    }

    class Dialog4Listener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            text1.setText(hourOfDay + " H" + minute + " M");
        }
    }

    // Progress Dialog 사용 - 8.0이상 비추천

    public void btn5Method(View v) {

        //Progress Diarog를 생성한다.
        pro = ProgressDialog.show(this, "title", "main message");
        // 5초 이후에 Progress Dialog를 제거하기 위해 쓰레드 스타트
        Handler handler = new Handler();
        ThreadClass thread = new ThreadClass();
        handler.postDelayed(thread, 5000); // 5초 이후에 thread 동작시켜라
    }

    class ThreadClass extends Thread {
        @Override
        public void run() {
            //현재 나타나있는 ProgressDialog를 제거한다.
            pro.cancel(); // 다른 스레드에서 캔슬하기 위하여 전역으로 ProgressDialog를 생성하였다.
        }
    }
}