package com.example.a19_listfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

// ListFragment
// Fragment 내에 ListView를 사용할 경우 보다 편리하게 구성할 수 있도록 제공되는 Fragment이다. (fragment내에 listview가 한개라는 전제)
// ListView의 id 가 @android:id/list로 설정되어 있을 경우 자동으로 ListView를 찾아 관리하게 된다.

public class MainActivity extends AppCompatActivity {

    SubFragment subFragment = new SubFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainContainer, subFragment);
        transaction.commit();
    }
}