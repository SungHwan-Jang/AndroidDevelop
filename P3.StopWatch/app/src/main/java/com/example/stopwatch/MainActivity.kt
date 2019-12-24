package com.example.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    @Volatile
    private var time = 0;

    private var timerTask : Timer? = null;
    private var isRunningTimerFlag : Boolean = false;
    private var lap = 1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FAB_BUTTON.setOnClickListener {
            isRunningTimerFlag = !isRunningTimerFlag;

            if(isRunningTimerFlag){
                timerCheckStart()
            }
            else{
                timerPause()
            }
        }

        RESET_FAB_BUTTON.setOnClickListener {
            resetFAB();

        }

        LAB_TIME_BUTTON.setOnClickListener {
            recordLabTime();
        }
    }

    private fun timerCheckStart() { //UI THREAD는 메인 스레드, WORKER THREAD 는 타이머..및 이외의 스레드들..WORKER THREAD는 UI 조작불가

        FAB_BUTTON.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10){
            time++;

            val sec = time /100;
            val milli = time % 100;

            runOnUiThread(fun() { // 해당 BeginInvoke 기능은 timer기능을 정지 시켜도 한 두번 더 동작이 되는 또다른 스레드가 된다. isRunningTimeFlag로 제어를 하였다...
                if(isRunningTimerFlag){
                    SEC_TEXT_VIEW.text = "$sec";
                    MILLI_TEXT_VIEW.text = "$milli";
                }
            })
        }
    }

    private fun timerPause(){
        FAB_BUTTON.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun recordLabTime(){
        val lapTime = this.time;
        val textView = TextView(this)
        textView.text = "${lap} LAP : ${lapTime/100}.${lapTime%100}"
        lapLayout.addView(textView, 0)
        lap++
    }

    private fun resetFAB() : Unit{ // 부족한점. UI 변경에 mutex_lock 필요

        isRunningTimerFlag = false;

        timerTask?.cancel();
        time = 0;
        lap = 1;

        FAB_BUTTON.setImageResource(R.drawable.ic_play_arrow_black_24dp)

        SEC_TEXT_VIEW.text = "0";
        MILLI_TEXT_VIEW.text = "00";

        lapLayout.removeAllViews()

    }
}


