package com.example.a17_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
// Fragment
// 여러 화면을 가지고 있는 app은 여러 Activity를 가지고 있는 app을 의미한다.
// activity는 독립된 실행 단위로, 메모리를 많이 소모한다.
// 독립된 실행 단위가 아닌 화면만 필요한 경우 Activity 보다 Fragment를 사용하는 것이 효율적이다.
// Fragment는 Activity내의 작은 화면 조각으로 Activity의 화면을 여러 영역으로 나누어 관리 하고자 하는 목적으로 사용한다.

// Fragment는 안드로이드 5.0 에서 추가 되었지만 하위 버전에서도 사용할 수 있다.
// add : Fragment를 지정된 레이아웃에 추가한다.
// replace : 지정된 레이아웃에 설정되어 있는 Fragment를 제거하고 새로운 Fragment를 추가한다.

// AddToBackStack
// 안드로이드에서 back button은 현재 액티비티를 종료한다.
// Fragment는 Activity가 가니므로 back button으로 제거할 수 없는데, AddToBackStack 메서드를 통해 BackStack에 포함할 경우
// back button으로 제거할 수 있다. 이를 통해 마치 이전 화면으로 돌아가는 듯한 효과를 줄 수 있다.
public class MainActivity extends AppCompatActivity {

    // Fragment 생성
    FragmentFirst fragmentFirst = new FragmentFirst();
    FragmentSecond fragmentSecond = new FragmentSecond();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void disp_fragment_first(View view){
        // fragment 관리하는 obj 추출
        FragmentManager manager = getSupportFragmentManager();
        // fragment 변경을 관리하는 객체를 추출 - 새로운 fragment 추가 및 교체 등을 담당하는 역할
        // transaction 객체를 이용하여 배치를 한다.
        FragmentTransaction tran = manager.beginTransaction();
        // fragment를 추가한다. - 배치할 레이아웃의 id값을 넣는다.
        //tran.add(R.id.mainContainer, fragmentFirst); // 한번 배치한 Fragment를 또다시 배치하면 app crash 발생한다.
        tran.replace(R.id.mainContainer, fragmentFirst); // 없으면 배치 있으면 제거하고 다시 배치를 수행한다.
        // fragment 변경사항을 백 스택에 포함시킨다.
        // 지금까지 fragment 변경사항이 backstack 저장되어 계속 이전 fragment 상황으로 돌아간다.
        tran.addToBackStack(null);
        // 적용한다.
        tran.commit();
        // 참고로 Layout은 FrameLayout을 사용한다 ( Linear는 계속 밑으로 생성, Frame은 덮어서 생성 )

    }

    public void disp_fragment_second(View view){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        //tran.add(R.id.mainContainer, fragmentSecond);
        tran.replace(R.id.mainContainer, fragmentSecond);
        tran.commit();
    }
}