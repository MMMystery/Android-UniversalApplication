package com.example.administrator.myuniversaltoolapplication.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myuniversaltoolapplication.R;


public class TwoFragment extends Fragment implements View.OnClickListener{
    private Button topBar_btn_left, topBar_btn_right;
    private TextView topBar_tv_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        initView(view);

        return view;

    }

    private void initView(View view) {
        topBar_btn_left = (Button) view.findViewById(R.id.topbar_btn_left);
        topBar_btn_right = (Button) view.findViewById(R.id.topbar_btn_right);
        topBar_tv_title = (TextView) view.findViewById(R.id.topbar_tv_title);
        topBar_btn_left.setOnClickListener(this);
        topBar_btn_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.topbar_btn_left:
                Toast.makeText(getActivity(),"lefttwo",Toast.LENGTH_LONG).show();
                break;
            case R.id.topbar_btn_right:
                Toast.makeText(getActivity(),"rigthtwo",Toast.LENGTH_LONG).show();
                break;
        }

    }
}
