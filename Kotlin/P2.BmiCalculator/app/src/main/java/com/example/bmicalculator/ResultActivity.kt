package com.example.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var height = intent.getStringExtra("height").toInt()
        var weight = intent.getStringExtra("weight").toInt()

        if(height == 0 || weight == 0){
            super.onDestroy()
            this.releaseInstance()
        }

        var bmi = weight/ Math.pow(height/100.0, 2.0)

        when{
            bmi >= 35 -> resultTextView.text = "고도 비만"
            bmi >= 30 -> resultTextView.text = "2 비만"
            bmi >= 25 -> resultTextView.text = "1 비만"
            bmi >= 23 -> resultTextView.text = "과 체중"
            bmi >= 18.5 -> resultTextView.text = "정상"
            else -> resultTextView.text = "저체중"
        }

        when {
            bmi >= 23 ->
                imageView.setImageResource(
                    R.drawable.ic_thumb_down_black_24dp
                )
            bmi >= 18.5 ->
                imageView.setImageResource(
                    R.drawable.ic_thumb_up_black_24dp
                )
            else ->
                imageView.setImageResource(
                    R.drawable.ic_sentiment_very_dissatisfied_black_24dp
                )
        }

        toast("BMI is ${Math.round(bmi)}")
    }
}
