package com.xieyaxin.space.forme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by John on 2017/4/18.
 */

public abstract class BaseFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=setUpLayout(inflater,container,savedInstanceState);
        initView();
        initOperation();
        return view;
    }

    public abstract View setUpLayout(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState);
    public abstract void initView();
    public abstract void initOperation();

}
