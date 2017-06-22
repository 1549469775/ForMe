package com.xieyaxin.space.forme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/4/22.
 */

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.switch1)
    Switch switch1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.switch1)
    public void onViewClicked() {

    }
}
