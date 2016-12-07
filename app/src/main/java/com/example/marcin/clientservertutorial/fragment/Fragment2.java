package com.example.marcin.clientservertutorial.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcin.clientservertutorial.R;

/**
 * Created by Marcin on 2016-12-06.
 */
public class Fragment2 extends Fragment {
    public Fragment2(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);
        return rootView;
    }
}