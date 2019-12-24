package com.example.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { // 센서의 정밀도가 변경될 때 호출
        //
    }

    override fun onSensorChanged(event: SensorEvent?) { // 센서의 값이 변경될 때 호출
        event?.let{
            Log.d("MainActivity", "onSensorChanged => x:" +
            "${it.values[0]}, y: ${it.values[1]}, z: ${it.values[2]}")
            //tag : 필터링 용, msg : 출력 메시지
            //Log.e() .w() .i() .v()와 같은 로그 메서드가 있다. (에러용, 경고용, 정보성 로그, 모든 로그)
            tiltView.onSensorEvent(event)
        }
    }

    private val sensorManager by lazy{ //지연 초기화로 sensorManager 변수를 처음 사용할 때 객체를 얻게 됨.
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //센서를 사용하려면 안드로이드가 제공하는 SensorManager 서비스 객체가 필요함.
        //1. 센서 매니저에 대한 참조를 얻어야함 (인스턴스화)
        //2. getSystemService()메서드에 센서 서비스 상수 전달하여 SensorManager 클래스의 인스턴스를 만듬.
    }

    private lateinit var tiltView : TiltView //늦은 초기화가 아니면 에러 발생 -> 미리 생성이 불가한가...why ??

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) //화면이 꺼지지 않게 함.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // 가로 모드로 동작하게 함.
        super.onCreate(savedInstanceState)

        tiltView = TiltView(this) //해당 클래스는 context를 인자로 받기 때문에 this를 지정해준다. 따라서 TiltView 클래스 초기화
        //setContentView(R.layout.activity_main)
        setContentView(tiltView) // TiltView를 등록
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME)
        //registerListener()메서드로 사용할 센서를 등록한다.
        //1. 센서값을 받을 SensorEventListener this를 지정하여 액티비티에서 센서값을 받도록함.
        //2. sensorManager는 SensorManager 객체의 인스턴스이며 getDefaultSensor()메서드로 사용할 센서 종류 지정
        //3. 센서값을 얼마나 자주 받을지 enum 전달
    }

    override fun onPause() { //화면 전환 직전 및 꺼짐 직전에 호출
        super.onPause()
        sensorManager.unregisterListener(this)
        //unregisterListener() 메서드로 센서 사용을 해제할 수 있다.
        //인자로 SensorEventListener 객체를 지정해야 하며 MainActivity 클래스에서 이 객체를 구현중이므로 this를 지정한다.
    }
}
