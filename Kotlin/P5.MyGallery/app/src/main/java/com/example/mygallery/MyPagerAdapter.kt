package com.example.mygallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

//Main.xml의 ViewPager는 프래그먼트들을 좌우로 슬라이드 하는 뷰이다.
// 어댑터 패턴을 구현하여 뷰 페이저를 사용해야한다.
// 데이터: 프래그먼트(화면),,, 어댑터 : 프래그먼트를 어느 화면에 표시할 것인지 관리하는 객체,,, 뷰 : 뷰페이저

// FragmentPagerAdapter : 페이지 내용이 영구적일 때 상속 한 번 로딩한 페이지는 메모리에 보관하여 빠르나 메모리를 많이 사용한다.
// FragmentStatePagerAdapter : 많은 수의 페이지가 있을 때 적합. 보이지 않는 페이지를 메모리에서 제거할 수 있고 상대적으로 적은 메모리를 차지.

class MyPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val items = ArrayList<Fragment>() // 뷰 페이저가 표시할 프래그먼트 목록
    // 어댑터가 프래그먼트의 목록을 리스트로 가지도록 함. 이 목록은 updateFragments() 메서드로 외부에서 추가가 가능

    override fun getItem(p0: Int): Fragment { //position 위치의 프래그먼트
        return items[p0]
    }

    override fun getCount(): Int { // 아이템 갯수
        return items.size
    }

    fun updateFragments(items:List<Fragment>){ // 아이템 갱신
        this.items.addAll(items)
    }


}