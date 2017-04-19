package com.xieyaxin.space.forme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by John on 2017/3/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initOperation();
    }

    public void setupToolbar(boolean home,boolean showTitle,String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(home);//设置toolbar返回可用true
        getSupportActionBar().setDisplayShowTitleEnabled(showTitle);//false
    }

    public void setupToolbar(boolean home,boolean showTitle) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(home);//设置toolbar返回可用true
        getSupportActionBar().setDisplayShowTitleEnabled(showTitle);//false
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    //每當用戶從操作欄中選擇在應用程序的活動層次結構中向上導航時，將調用此方法，你可以看成返回键吧
    @Override
    public boolean onSupportNavigateUp() {//如果在此活動的清單中指定了父級或其活動別名，則會自動處理默認向上導航。
        onBackPressed();
        return true;
    }

    public abstract void initView();
    public abstract void initOperation();

}
