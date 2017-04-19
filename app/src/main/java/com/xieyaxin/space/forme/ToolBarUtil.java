package com.xieyaxin.space.forme;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by John on 2017/4/18.
 */

public class ToolBarUtil {

    private Toolbar toolbar;

    public static ToolBarUtil newInstance() {
        ToolBarUtil toolBarUtil = new ToolBarUtil();
        return toolBarUtil;
    }

    public void setupToolbar(AppCompatActivity activity, boolean home, boolean showTitle, String title) {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(home);//设置toolbar返回可用true
        activity.getSupportActionBar().setDisplayShowTitleEnabled(showTitle);//false
    }

    public void setupToolbar(AppCompatActivity activity,boolean home,boolean showTitle) {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(home);//设置toolbar返回可用true
        activity.getSupportActionBar().setDisplayShowTitleEnabled(showTitle);//false
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ActionBar getActionBar(AppCompatActivity appCompatActivity) {
        return appCompatActivity.getSupportActionBar();
    }

}
