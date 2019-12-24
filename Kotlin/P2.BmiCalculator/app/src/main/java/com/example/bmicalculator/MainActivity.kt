package com.example.bmicalculator

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData();

        resultButton.setOnClickListener {
            // 결과 버튼 클릭시 할 일을 작성하는 부분

            // 코틀린 기반 intent
//            val intent = Intent(this, ResultActivity::class.java)
//
//            intent.putExtra("weight", weightEditText.text.toString()) // key 와 value의 쌍으로 데이터 저장
//            intent.putExtra("height", heightEditText.text.toString())
//
//            startActivity(intent)

            //Anko 기반 intetn

            if(weightEditText.text.toString() == "" || heightEditText.text.toString() == ""){ // For Error Handling
                return@setOnClickListener;
            }
            else{
                saveData(heightEditText.text.toString().toInt(), weightEditText.text.toString().toInt())
                startActivity<ResultActivity>(
                    "weight" to weightEditText.text.toString(),
                    "height" to heightEditText.text.toString()
                )
            }
        }
    }

    private fun saveData(height:Int, weight:Int){
        val sharedPreference = getSharedPreferences("dataSet", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()

        editor.putInt("height", height)
        editor.putInt("weight", weight)

        editor.commit();
    }

    private fun loadData() : Unit{
        val sharedPreference = getSharedPreferences("dataSet", Context.MODE_PRIVATE)
        var height : Int = sharedPreference.getInt("height", 0);
        var weight : Int = sharedPreference.getInt("weight", 0);

        if(height != 0 && weight != 0){
            heightEditText.setText(height.toString());
            weightEditText.setText(weight.toString());
        }
    }
}
