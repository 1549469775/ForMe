package com.xieyaxin.space.forme.Preference;

import android.content.Context;
import android.os.Handler;

import com.xieyaxin.space.forme.Bean.MeiZhi;
import com.xieyaxin.space.forme.Interface.JudgeListener;
import com.xieyaxin.space.forme.Interface.ResultListener;
import com.xieyaxin.space.forme.MainActivity;
import com.xieyaxin.space.forme.Util.RelpaceUtil;
import com.xieyaxin.space.forme.Util.ShowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/4/22.
 */

public class MainDataAction {

    private static MainDataAction action = null;
    private Context context;

    private MainDataAction(Context context){
        this.context=context;
    }

    public static MainDataAction getInstance(Context context){
        if (action==null){
            synchronized (MainDataAction.class){
                if (action==null){
                    action=new MainDataAction(context);
                }
            }
        }
        return action;
    }

}
