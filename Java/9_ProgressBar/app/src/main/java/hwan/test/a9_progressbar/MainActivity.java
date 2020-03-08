package hwan.test.a9_progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar)findViewById(R.id.progressBar);
    }

    public void btn1Method(View v){
        bar.incrementProgressBy(5);
    }

    public void btn2Method(View v){
        bar.incrementProgressBy(-5);
    }

    public void btn3Method(View v){
        bar.setProgress(80);
    }
}