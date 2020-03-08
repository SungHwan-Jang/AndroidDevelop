package hwan.test.a10_seekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SeekBar seek1, seek2;
    TextView text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seek1 = (SeekBar) findViewById(R.id.seekBar);
        seek2 = (SeekBar) findViewById(R.id.seekBar2);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);

        SeekBarListner seekListner = new SeekBarListner();
        seek1.setOnSeekBarChangeListener(seekListner);
        seek2.setOnSeekBarChangeListener(seekListner);
    }

    public void btn1Method(View v) {
        seek1.incrementProgressBy(10);
        seek2.incrementProgressBy(10);
    }

    public void btn2Method(View v) {
        seek1.incrementProgressBy(-10);
        seek2.incrementProgressBy(-10);
    }

    public void btn3Method(View v) {
        seek1.setProgress(50);
        seek2.setProgress(50);
    }

    public void btn4Method(View v) {
        int value1 = seek1.getProgress();
        int value2 = seek2.getProgress();

        text1.setText("seek1 :" + value1);
        text2.setText("seek2 :" + value2);
    }

    class SeekBarListner implements SeekBar.OnSeekBarChangeListener {

        //현재 값을 변경 시켰을 때 호출되는 리스너
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int id = seekBar.getId();
            switch (id){
                case R.id.seekBar:
                    text1.setText("1 Seekbar :" + progress);
                    break;
                case R.id.seekBar2:
                    text1.setText("2 Seekbar :" + progress);
                    break;
            }

            if(fromUser == true){
                text2.setText("user changed this value");
            }
            else{
                text2.setText("code change this value");
            }
        }

        //값을 변경하기위해 터치하면 호출되는 메서드
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            int id = seekBar.getId();

            switch (id) {
                case R.id.seekBar:
                    text2.setText("Seekbar1 를 터치함");
                    break;
                case R.id.seekBar2:
                    text2.setText("Seekbar2 를 터치함");
                    break;
            }

        }

        // 값을 변경하고 터치를 때면 호출되는 메서드
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int id = seekBar.getId();
            switch (id){
                case R.id.seekBar:
                    text2.setText("Seekbar1 를 땜");
                    break;
                case R.id.seekBar2:
                    text2.setText("Seekbar2를 땜");
                    break;
            }
        }
    }
}