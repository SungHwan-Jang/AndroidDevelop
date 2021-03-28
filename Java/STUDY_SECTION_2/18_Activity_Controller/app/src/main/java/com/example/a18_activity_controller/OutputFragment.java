package com.example.a18_activity_controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutputFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textView1, textView2;

    public OutputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OutputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OutputFragment newInstance(String param1, String param2) {
        OutputFragment fragment = new OutputFragment();
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
        View view = inflater.inflate(R.layout.fragment_output, container, false);
        Button btn = view.findViewById(R.id.button_2);
        ButtonListener listener = new ButtonListener();
        btn.setOnClickListener(listener);

        MainActivity activity = (MainActivity)getActivity();
        textView1 = view.findViewById(R.id.text_view_1);
        textView2 = view.findViewById(R.id.text_view_2);
        textView1.setText(activity.value1);
        textView2.setText(activity.value2);

        return view;
    }

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // 현재 fragment를 관리하는 activity 객체를 추출한다.
            MainActivity activity = (MainActivity)getActivity();
            // fragment manaer를 호출한다.
            FragmentManager manager = activity.getSupportFragmentManager();
            // 이전 fragment로 이동한다.
            manager.popBackStack();
        }
    }
}