package com.example.a20_dialogfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubFragment extends DialogFragment {

    public SubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub, container, false);
    }

    // Dialog를 만들어서 띄울때 해당 메서드가 실행된다.
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity)getActivity();

        // alert dialog 만들어서 반환
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        DialogListner listner = new DialogListner();
        builder.setTitle("title");
        builder.setMessage("message");
        builder.setPositiveButton("positive", listner);
        builder.setNeutralButton("neutral", listner);
        builder.setNegativeButton("negative", listner);

        AlertDialog alert = builder.create();
        return alert;
        //return super.onCreateDialog(savedInstanceState);
    }

    class DialogListner implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            MainActivity activity = (MainActivity)getActivity();

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    activity.textView.setText("positive");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    activity.textView.setText("negative");
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    activity.textView.setText("neutral");
                    break;
                default:
                    break;
            }
        }
    }
}