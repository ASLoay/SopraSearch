package com.app.ashmawy.soprasearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lenovo on 04-Dec-15.
 */
public class ManageProfile extends android.support.v4.app.Fragment implements GUI_Output {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manageprofilelayout, container, false);
        return rootView;
    }

    @Override
    public void ShowSearchScreen() {
    }

    @Override
    public void LocalisationSaved() {

    }
}
