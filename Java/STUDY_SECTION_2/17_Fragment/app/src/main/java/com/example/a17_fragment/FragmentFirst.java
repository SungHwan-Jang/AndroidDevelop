package com.example.a17_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFirst#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFirst extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentFirst() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFirst.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFirst newInstance(String param1, String param2) {
        FragmentFirst fragment = new FragmentFirst();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    // Fragment 생성 후 화면에 배치할때, onCreateView 메서드가 자동 호출된다.
    // 반환하는 View 객체를 지정된 레이아웃에 배치를 하게된다. 배치된 View들은 Fragment에서 관리를 하게된다.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // container - 지정된 레이아웃의 객체 id ( 버튼 등 어디에 붙어있는지 )
        // 즉 R.layout.fragment_first로 뷰를 만들어서 container에 배치를 하고 inflater 반환하는 뷰를 처리한다.
        // (해당 뷰를 Main에서 붙일 부분을 지정한다.)
        return inflater.inflate(R.layout.fragment_first, container, false);
    }
}