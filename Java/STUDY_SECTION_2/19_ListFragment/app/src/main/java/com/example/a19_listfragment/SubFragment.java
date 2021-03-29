package com.example.a19_listfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// ListFragment를 상속한다.
public class SubFragment extends ListFragment {

    TextView textView;

    // 리스트뷰를 구성하기 위한 데이터
    String [] stringList = {"option1", "option2", "option3", "option4", "option5"};

    public SubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub, container, false);
        // 뷰의 주소값을 얻어온다 ( 리스트 뷰의 주소값은 얻지 않는다 - 자동으로 찾아준다 ListFragment 상속 받았기 때문에,
        // android:id="@android:id/list")를 자동으로 받아온다.
        textView = view.findViewById(R.id.text_view_1);
        MainActivity activity = (MainActivity)getActivity();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, stringList);
        // 어뎁터를 리스트 뷰에 적용한다.
        setListAdapter(adapter);

        return view;
    }
    
    // 리스트뷰의 항목을 터치하면 호출되는 메서드를 자동 제공 ( 이전에는 리스너를 만들어서 적용 했었다. )
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        textView.setText("selected item : " + stringList[position]);
    }
}