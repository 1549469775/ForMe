package com.xieyaxin.space.forme.UI;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xieyaxin.space.forme.Interface.OnClickListener;
import com.xieyaxin.space.forme.R;
import com.xieyaxin.space.forme.Util.ShowUtil;

/**
 * 使用方法：在布局中加如一个id为toolbar的Toolbar
 * Created by John on 2017/4/18.
 */

public class ToolBarCreate {

    private Toolbar toolbar = null;
    private OnClickListener onClickListener;

    public ToolBarCreate(AppCompatActivity activity){
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }

    public ToolBarCreate(AppCompatActivity activity,String title){
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
    }

    public ToolBarCreate(AppCompatActivity activity,String title,boolean showArrow){
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(showArrow);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ToolBarCreate(AppCompatActivity activity,@LayoutRes int resId) {
        activity.getSupportActionBar().setCustomView(resId);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
            }
        });
    }

}
