package com.example.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {

    private val greenPaint : Paint = Paint() // Canvas 그리기 메서드에 paint 객체가 필요하다. 따라서 생성.
    private val blackPaint : Paint = Paint()
    private var cX : Float = 0.0f;
    private var cY : Float = 0.0f;
    private var xCoord : Float = 0.0f
    private var yCoord : Float = 0.0f

    init{
        greenPaint.color = Color.GREEN
        blackPaint.style = Paint.Style.STROKE
        //스타일 속성
        // FILL - 색을 채우고 획 관련 설정 무시.
        // FILL_AND_STROKE - 획과 관련된 설정 유지하며 색을 채움.
        // STROKE - 획 관련 설정을 유지하여 외곽선만 그림.
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) { // View의 크기가 변경될 때 호출.
        //super.onSizeChanged(w, h, oldw, oldh)
        cX = w / 2f
        cY = h / 2f
    }

    //onDraw 메서드는 인자로 넘어오는 Canvas 객체에 뷰의 모습을 그린다.
    override fun onDraw(canvas: Canvas?) { //그래픽을 다루기 위해 다음과 같은 클래스가 필요하다.
        // Canvas : 도화지( 뷰의 표면)
        // Paint : 붓 (색, 굵기, 스타일 정의)
        // Canvas Class의 그리기 메서드는 다음과 같다.
        // drawCircle(cx, cy, radius, paint) 원 그리기
        // drawLine(startX, startY, stopX, stopY, paint) 라인 그리기

        canvas?.drawCircle(cX,cY,100f,blackPaint)
        canvas?.drawCircle(cX + xCoord,cY + yCoord,100f,greenPaint)

        canvas?.drawLine(cX+(-20f), cY+0f, cX+20f , cY+0f, blackPaint)
        canvas?.drawLine(cX+0f, cY+(-20f), cX+0f , cY+20f, blackPaint)
        //좌표계는 왼쪽 상단이 (0,0) 오른쪽 하단이 (mX, mY) 중점을 (cX, cY)라고 할때 View의 크기를 알아야함.
        //onSizeChanged() 메서드로 뷰의 크기를 얻어냄.
    }

    fun onSensorEvent(event: SensorEvent){ //WHY!? SensorEvent에 ? 붙이면 동작이상..
        //화면이 가로축이라서 x와 y를 바꿔줌.
        yCoord = event.values[0] * 40 // 가속도계 값이 너무 작아서 20배 크게 해서 크게크게 움직이도록 함.
        xCoord = event.values[1] * 40

        invalidate() // 해당 메서드는 뷰의 onDraw 메서드를 다시 호출하는 메서드이다. 즉, 뷰를 다시 그리게 된다.
    }
}