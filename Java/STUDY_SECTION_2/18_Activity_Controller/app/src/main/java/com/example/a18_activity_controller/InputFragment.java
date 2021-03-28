package com.example.a18_activity_controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText edit1, edit2;

    public InputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputFragment newInstance(String param1, String param2) {
        InputFragment fragment = new InputFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        // 생성한 뷰 내부에 있는 뷰 객체의 주소값을 추출한다.
        Button btn = view.findViewById(R.id.button_1);
        ButtonListener listener = new ButtonListener();
        btn.setOnClickListener(listener);

        edit1 = view.findViewById(R.id.edit_text_1);
        edit2 = view.findViewById(R.id.edit_text_2);

        return view;
    }

    // 버튼을 누르면 반응할 메서드
    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // 현재 fragment를 관리하는 activity 객체를 추출한다.
            MainActivity activity = (MainActivity)getActivity();

            activity.value1 = edit1.getText().toString();
            activity.value2 = edit2.getText().toString();
            // result fragment가 나타나도록 메서드를 호출한다.
            activity.set_fragment(FRAGMENT_T.OUTPUT_FRAGMENT);
        }
    }
}