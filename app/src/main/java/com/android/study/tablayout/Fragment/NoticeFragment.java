package com.android.study.tablayout.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.study.tablayout.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {
    public static NoticeFragment newInstance() {
        NoticeFragment fragment = new NoticeFragment();
        Bundle args = new Bundle(); // intent처럼 데이터 전달할 때
        fragment.setArguments(args);
        return fragment;
    } // Fragment


    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        return view;
    }

}
