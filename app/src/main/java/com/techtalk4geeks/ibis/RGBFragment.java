package com.techtalk4geeks.ibis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RGBFragment extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.fragment_rgb, container, false);
    }
}