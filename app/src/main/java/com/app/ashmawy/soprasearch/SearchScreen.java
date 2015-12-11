package com.app.ashmawy.soprasearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public class SearchScreen extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.searchscreenlayout, container, false);
        return rootView;
    }

}
